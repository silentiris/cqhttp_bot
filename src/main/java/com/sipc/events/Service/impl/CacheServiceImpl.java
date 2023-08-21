package com.sipc.events.Service.impl;

import com.sipc.events.Service.CacheService;
import com.sipc.events.Util.spider.OjSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private OjSpider ojSpider;
    @Override
    @Cacheable(cacheNames = "sessionId",unless = "#result == null")
    public String getOjSessionId() {
        try {
            return ojSpider.getSessionId();
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    @CachePut(cacheNames = "sessionId",unless = "#result == null")
    public String putOjSessionId() {
        try {
            return ojSpider.getSessionId();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
