package com.lunchlearn.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class EhCache {
	private static EhCacheManagerFactoryBean cacheManagerfactoryBean;

	@Bean
	public CacheManager getEhCacheManager() {
		return new EhCacheCacheManager(getEhCacheFactory().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean getEhCacheFactory() {
		cacheManagerfactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerfactoryBean.setShared(true);
		cacheManagerfactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return cacheManagerfactoryBean;
	}

}
