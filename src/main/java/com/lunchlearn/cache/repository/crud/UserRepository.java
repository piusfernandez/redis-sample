package com.lunchlearn.cache.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lunchlearn.cache.domain.User;

/*
 * Hierarchy 
 * CrudRepository ( CRUD functions ) 
 *  PagingAndSortingRepository ( pagination and sorting records ) 
 *   JpaRepository 
 * */
@Repository
//public interface UserRepository extends CrudRepository<User, String> {
public interface UserRepository extends JpaRepository<User, String> {
}
