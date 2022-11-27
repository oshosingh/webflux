# webflux

## Movies
1. Get API to fetch movieinfo and moviereview using webclient.
2. Implemented rate limiters to restrict users from making many requests.
    -> TokenBucket Algo


## MovieInfo
1. GET api for getting movieinfo by movieId
2. POST,UPDATE,DELETE apis to add, update and delete movieinfo.
3. Server Sent Events is implemented to stream newly added movieinfo.
4. Added KafkaProducer and KafkaConsumer code to publish and consume messages from movieinfo topic.

## MovieReview
1. GET api for getting moviereviews by movieId
2. POST,UPDATE,DELETE apis to add, update and delete moviereviews by movieid.
