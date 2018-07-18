package com.lunchlearn.cache.repository.custom;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import com.lunchlearn.cache.domain.User;
import com.lunchlearn.cache.pubsub.RedisMessageSubscriber;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

	@Autowired
	RedisMessageSubscriber redisMessageSubscriber;
	
	@Autowired
	CacheManager cacheManager;	
	
	private RedisTemplate<String, User> redisTemplate;
	
	private RedisTemplate<String, String> redisTemplateForStringValue;
	
	private HashOperations<String, String, User> hashOperation;

	private ListOperations<String, User> listOperation;
	
	private SetOperations<String, User> setOperation;
	
	private ValueOperations<String, String> valueOperation;
	
	private ZSetOperations<String, User> zSetOperation;
	
	public UserCustomRepositoryImpl(RedisTemplate<String, User> redisTemplate,
			RedisTemplate<String, String> redisTemplateForStringValue)	{
		this.redisTemplate = redisTemplate;
		this.redisTemplateForStringValue = redisTemplateForStringValue;
		hashOperation = redisTemplate.opsForHash();
		listOperation = redisTemplate.opsForList();
		setOperation = redisTemplate.opsForSet();
		valueOperation = redisTemplateForStringValue.opsForValue();
		zSetOperation = redisTemplate.opsForZSet();
	}
	
	@Override
	public void save(User user) {
		hashOperation.put("USER", user.getId(), user);		
		listOperation.leftPush("list:users", user);
		setOperation.add("set:users" , user);
		zSetOperation.add("zset:users" , user, user.getAge());
		valueOperation.set(user.getId(), user.getName() + ":" + user.getAge());
	}

	@Override
	@CacheEvict(value="userCache", key="#user.id")
	public void update(User user) {
		save(user);
	}

	@Override
	@Cacheable(value="userCache")
	public User findById(String id) {
		System.out.println("calling findById() " + id);
/*		if(!RedisMessageSubscriber.messageList.isEmpty())	{ 
			System.out.println("subcriber: message received#" + RedisMessageSubscriber.messageList.get(0).toString());
			cacheManager.getCache("userCache").put(id, hashOperation.get("USER", id));
		}*/
		return hashOperation.get("USER", id);
	}

	@Override
	public Map<String, User> findAll() {
		return hashOperation.entries("USER");
	}

	@Override
	public void delete(String id) {
		hashOperation.delete("USER", id);
	}

}
