package cn.tuyucheng.taketoday.caching.eviction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CachingService {
	@Autowired
	CacheManager cacheManager;

	public void putToCache(String cacheName, String key, String value) {
		cacheManager.getCache(cacheName).put(key, value);
	}

	public String getFromCache(String cacheName, String key) {
		String value = null;
		if (cacheManager.getCache(cacheName).get(key) != null)
			value = cacheManager.getCache(cacheName).get(key).get().toString();
		return value;
	}

	@CacheEvict(value = "first", allEntries = true)
	public void evictAllCacheValues() {
	}

	@CacheEvict(value = "first", key = "#cacheKey")
	public void evictSingleCacheValue(String cacheKey) {
	}

	public void evictSingleCacheValue(String cacheName, String cacheKey) {
		cacheManager.getCache(cacheName).evict(cacheKey);
	}

	public void evictAllCacheValues(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}

	@Scheduled(fixedRate = 6000)
	public void evictAllCachesAtIntervals() {
		evictAllCaches();
	}

	public void evictAllCaches() {
		cacheManager.getCacheNames()
				.parallelStream()
				.forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}
}