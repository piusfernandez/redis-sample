package com.lunchlearn.cache.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunchlearn.cache.domain.User;
import com.lunchlearn.cache.repository.crud.UserRepository;

@RestController
@RequestMapping("/rest/user")
public class UserResource {

	@Autowired
	private UserRepository userRepository;
	
	
/*	public UserResource(@Qualifier("userCrudRepository") UserRepository userRepository) {
		this.userRepository = userRepository;
	} */

	@GetMapping("/add/{id}/{name}/{age}")
	public User add(@PathVariable("id") final String id,
		@PathVariable("name") final String name,
		@PathVariable("age") final int age)
	{
		userRepository.save(new User(id, name, age));
		//return userRepository.findById(id);
		return userRepository.findOne(id);
	}
	
	@GetMapping("/update/{id}/{name}/{age}")
	public User update(@PathVariable("id") final String id,
			@PathVariable("name") final String name,
			@PathVariable("age") final int age
			)
	{
		userRepository.save(new User(id, name, age));
		//return userRepository.findById(id);
		return userRepository.findOne(id);
	}
	
	@GetMapping("/all")
	public List<User> all()
	{
		return (List<User>) userRepository.findAll();
	}
	

}
