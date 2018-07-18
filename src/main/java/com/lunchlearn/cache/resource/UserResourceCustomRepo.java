package com.lunchlearn.cache.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunchlearn.cache.domain.User;
import com.lunchlearn.cache.pubsub.RedisMessagePublisher;
import com.lunchlearn.cache.repository.custom.UserCustomRepository;

@RestController
@RequestMapping("/rest/custom/user")
public class UserResourceCustomRepo {

	@Autowired
	RedisMessagePublisher redisMessagePublisher;
	
	@Autowired
	private UserCustomRepository userRepository;
	
	@Autowired
	CacheManager cacheManager;	
	
/*	public UserResource(@Qualifier("userCrudRepository") UserRepository userRepository) {
		this.userRepository = userRepository;
	} */

	@GetMapping("/add/{id}/{name}/{age}")
	public User add(@PathVariable("id") final String id,
		@PathVariable("name") final String name,
		@PathVariable("age") final int age)
	{
		userRepository.save(new User(id, name, age));
		return userRepository.findById(id);
	}
	
	@GetMapping("/update/{id}/{name}/{age}")
	public User update(@PathVariable("id") final String id,
			@PathVariable("name") final String name,
			@PathVariable("age") final int age
			)
	{
		userRepository.update(new User(id, name, age));
		redisMessagePublisher.publish(id);
		//cacheManager.getCache("userCache").put(id, userRepository.findById(id));
		
		return userRepository.findById(id);
	}
	
	@GetMapping("/all")
	public Map<String, User> all()
	{
		return (Map<String, User>) userRepository.findAll();
	}
	
	@GetMapping("{id}")
	public User findById(@PathVariable("id") final String id)	{
		return userRepository.findById(id);
	}
}
