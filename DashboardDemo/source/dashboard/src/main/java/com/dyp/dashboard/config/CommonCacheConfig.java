package com.dyp.dashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonCacheConfig {
    @Value("${spring.cache.cache-enable}")
    private boolean cacheEnable;

    @Value("${spring.cache.time-to-live}")
    private long timeToLive;

    @Value("${spring.cache.redis-shiro}")
    private boolean redisShiroEnable;


    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public boolean isRedisShiroEnable() {
        return redisShiroEnable;
    }

    public void setRedisShiroEnable(boolean redisShiroEnable) {
        this.redisShiroEnable = redisShiroEnable;
    }
}
