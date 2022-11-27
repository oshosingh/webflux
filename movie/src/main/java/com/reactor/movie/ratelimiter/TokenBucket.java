package com.reactor.movie.ratelimiter;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class TokenBucket implements RateLimiter{

    int bucketSize;
    int refillRate;

    Long refillTime = 2000l;

    AtomicInteger currentCapacity;

    AtomicLong lastUpdatedTime;

    public TokenBucket(int bucketSize, int refillRate) {
        this.bucketSize = bucketSize;
        this.refillRate = refillRate;
        currentCapacity = new AtomicInteger(refillRate);
        lastUpdatedTime = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public boolean grantAccess() {
        refillBucket();
        if(currentCapacity.get() > 0) {
            currentCapacity.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refillBucket() {
        Long currentTime = System.currentTimeMillis();

        int addTokens = (int) ((currentTime - lastUpdatedTime.get()) / 1000 * refillRate);
        currentCapacity.getAndSet(Math.min(bucketSize, currentCapacity.getAndAdd(addTokens)));
        lastUpdatedTime.getAndSet(System.currentTimeMillis());
    }
}
