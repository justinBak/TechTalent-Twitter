package com.tts.techtalenttwitter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwitter.model.UserProfile;

// You create an interface rather than a class because we aren't actually going
//to create the repository... SpringBoot is. However, we need to specify to SpringBoot
//details about how we want our repository created and what query methods
//might be used on it...so we create an interface for Spring Boot to scan and analyze 
//in order to create the real implement UserRepository

@Repository
public interface UserRepository extends CrudRepository<UserProfile, Long> {
	
	UserProfile findByUsername(String username);
	
	@Override
	List<UserProfile> findAll();

}
