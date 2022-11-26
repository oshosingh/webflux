package com.reactor.movies.service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactor.movies.mapper.MovieMapper;
import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.model.entity.MovieInfo;
import com.reactor.movies.repo.MovieInfoRepo;
import com.reactor.movies.util.MovieInfoUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
public class MovieInfoService {

	@Autowired
	private MovieInfoRepo movieInfoRepo;

	@Autowired
	private MovieMapper movieMapper;

	@Autowired
	private MovieInfoUtil movieInfoUtil;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${prop.spring.kafka.bootstrap-servers}")
	private String bootStrapServers;

	private Sinks.Many<MovieInfo> movieFluxSink = Sinks.many().replay().all();

	public Mono<MovieInfoDto> saveMovieInfo(MovieInfoDto movieInfoDto) {
		return movieMapper.getMovieInfoEntity(movieInfoDto).flatMap(movieInfoRepo::save).doOnNext(movie -> {
			movieFluxSink.tryEmitNext(movie);
			publishToKafka(movie, "movieinfo");
		}).map(movieMapper::getMovieInfoDtoFromEntity).log();
	}

	public Flux<MovieInfoDto> getMovieInfo() {
		return movieInfoRepo.findAll().map(movieMapper::getMovieInfoDtoFromEntity).log();
	}

	public Mono<MovieInfoDto> getMovieInfoById(String movieId) {
		return movieInfoRepo.findById(movieId).map(movieMapper::getMovieInfoDtoFromEntity);
	}

	public Mono<MovieInfoDto> updateMovieById(MovieInfoDto movieInfoDto, String movieId) {
		return movieInfoRepo.findById(movieId).flatMap(movieInfo -> {
			movieInfoUtil.copyProperties(movieInfoDto, movieInfo);
			return movieInfoRepo.save(movieInfo).map(movieMapper::getMovieInfoDtoFromEntity);
		});
	}

	public Mono<ResponseEntity<MovieInfoDto>> updateMovieByIdAlt(MovieInfoDto movieInfoDto, String movieId) {
		return movieInfoRepo.findById(movieId).flatMap(movieInfo -> {
			movieInfoUtil.copyProperties(movieInfoDto, movieInfo);
			return movieInfoRepo.save(movieInfo).map(movieMapper::getMovieInfoDtoFromEntity);
		}).map(updatedMovieInfo -> ResponseEntity.ok().body(updatedMovieInfo))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	public Mono<Void> deleteMovieInfoById(String movieId) {
		return movieInfoRepo.deleteById(movieId);
	}

	public Flux<String> movieStream() {
		Flux<String> movieInfoFlux = movieFluxSink.asFlux().map(MovieInfo::toString);
		return movieInfoFlux;
	}

	public void publishToKafka(MovieInfo movieInfo, String topicName) {
		try {
			String movieInfoJson = objectMapper.writeValueAsString(movieInfo);
			Properties producerProps = new Properties();

			producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
			producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

			SenderOptions<String, String> senderOptions = SenderOptions.<String, String>create(producerProps)
					.maxInFlight(1024);

			KafkaSender<String, String> sender = KafkaSender.create(senderOptions);

			Flux<SenderRecord<String, String, String>> senderRecord = Flux
					.just(SenderRecord.create(topicName, null, null, null, movieInfoJson, null));

			sender.send(senderRecord)
					.doOnError(e -> log.error("Error occured while publishing data to kafka topic : {} msg : {}",
							topicName, e.getMessage()))
					.doOnNext(r -> log.info("Published data to kafka topic : {} metdata : {}", topicName,
							r.correlationMetadata()))
					.subscribe();
			// subsribe to trigger the actual flow of records

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void consumeFromKafka(String topicName) {
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "movieinfogroup");

		ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create(consumerProps)
				.commitBatchSize(1)
				.commitInterval(Duration.ZERO)
				.subscription(Collections.singleton(topicName));
		
		Flux<ReceiverRecord<String, String>> dataFromKafka = KafkaReceiver.create(receiverOptions).receive();
		
		dataFromKafka.subscribe(data -> {
			log.info("Data from kafka : {}", data);
			data.receiverOffset().acknowledge();
		});
		
	}
}
