package com.lunchlearn.cache.repository.custom;

import java.util.Map;

import com.lunchlearn.cache.domain.User;

public interface UserCustomRepository {
	void save(User user);
	void update(User user);
	User findById(String id);
	Map<String, User> findAll();
	void delete(String id);
	
}
