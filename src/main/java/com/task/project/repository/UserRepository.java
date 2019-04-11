package com.task.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.project.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	List<User> findAll();
	List<User> findByName(String name);

	User findByLogonIDAndPassword(String logonID, String password);
	User findByLogonID(String logonID);
	
	boolean existsByLogonIDAndPassword(String logonID, String password);
	boolean existsByLogonID(String logonID);
	
}
