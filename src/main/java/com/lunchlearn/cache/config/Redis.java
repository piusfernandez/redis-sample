package com.lunchlearn.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunchlearn.cache.domain.User;

@Configuration
public class Redis {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory()	{
		// connect to local redis with out host / port 
		return new JedisConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, User> redisTemplate() {
		RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(customObjectMapper()));
		redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer(customObjectMapper()));
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(customObjectMapper()));
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplateForStringValue() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}
	
	private ObjectMapper customObjectMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.setVisibility(
	            mapper.getVisibilityChecker()
	                    .withFieldVisibility(Visibility.ANY)
	                    .withGetterVisibility(Visibility.NONE)
	                    .withSetterVisibility(Visibility.NONE)
	                    .withCreatorVisibility(Visibility.NONE));
	    mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class"); // enable default typing
	    return mapper;
	}
}
