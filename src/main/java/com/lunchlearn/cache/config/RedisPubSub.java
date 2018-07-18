package com.lunchlearn.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.lunchlearn.cache.domain.User;
import com.lunchlearn.cache.pubsub.MessagePublisher;
import com.lunchlearn.cache.pubsub.RedisMessagePublisher;
import com.lunchlearn.cache.pubsub.RedisMessageSubscriber;

@Configuration
public class RedisPubSub {

	@Autowired
	JedisConnectionFactory jedisConnectionFactory;
	
	@Autowired
	RedisTemplate<String, User> redisTemplate;
	
	
	@Bean
	MessageListenerAdapter messageListener() { 
	    return new MessageListenerAdapter(new RedisMessageSubscriber());
	}
	
	@Bean
	RedisMessageListenerContainer redisContainer() {
	    RedisMessageListenerContainer container 
	      = new RedisMessageListenerContainer(); 
	    container.setConnectionFactory(jedisConnectionFactory); 
	    container.addMessageListener(messageListener(), topic()); 
	    return container; 
	}
	
	@Bean
	MessagePublisher redisPublisher() { 
	    return new RedisMessagePublisher(redisTemplate, topic());
	}
	
	@Bean
	ChannelTopic topic() {
	    return new ChannelTopic("messageQueue");
	}
}
