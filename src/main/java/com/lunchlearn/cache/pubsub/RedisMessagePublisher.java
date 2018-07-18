package com.lunchlearn.cache.pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.lunchlearn.cache.domain.User;

@Service
public class RedisMessagePublisher implements MessagePublisher {

	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@Autowired
	private ChannelTopic topic;

	public RedisMessagePublisher() {
	}

	public RedisMessagePublisher(RedisTemplate<String, User> redisTemplate, ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}