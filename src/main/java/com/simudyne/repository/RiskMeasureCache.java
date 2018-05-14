package com.simudyne.repository;

import com.simudyne.rest.ResultCollector;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main entry point to EHCache to store and retrieve data
 */
@Repository
@Lazy
public class RiskMeasureCache {

    public static final Logger LOGGER = LoggerFactory.getLogger(RiskMeasureCache.class);

    private Cache<String, ResultCollector> cache;

    @Autowired
    private EhCacheManager ehCacheManager;

    private ReentrantLock reentrantLock = new ReentrantLock();
    /**
     * Add data to the cache
     * @param key
     * @param value
     */
    public void addTokenToCache(String key, ResultCollector value) {
       try {
           reentrantLock.lock();
           cache.put(key, value);
           LOGGER.info("Placed value in the cache key:{}, value:{}",key, value);
       } finally {
           reentrantLock.unlock();
       }
    }

    /**
     * Get Data from Cache
     * @param key
     * @return
     */
    public ResultCollector getTokenFromCache(String key) {
        try {
            reentrantLock.lock();
            return cache.get(key);
        }finally {
            reentrantLock.unlock();
        }
    }

    @PostConstruct
    public void post(){
        cache = ehCacheManager.getCache("AGENT_BASED_MODEL");
    }

    @PreDestroy
    public void onDestroy() throws Exception{
        ehCacheManager.destroy();
    }
}
