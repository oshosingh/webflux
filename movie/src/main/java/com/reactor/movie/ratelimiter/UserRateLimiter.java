package com.reactor.movie.ratelimiter;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserRateLimiter {

    Map<Integer, TokenBucket> map;

    public UserRateLimiter(int id) {
        map = new HashMap<>();
        map.put(id, new TokenBucket(10, 10));
    }

    /**
     * Checks if user can be granted access
     * @return
     */
    public void isAllowed(Integer userId) {
        if(map.get(userId).grantAccess()) {
            log.info("{} able to access the api", Thread.currentThread().getName());
        }
        else {
            log.error("{} unable to access the api", Thread.currentThread().getName());
        }
    }


}
