package com.cooksys.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByCredentialsUsername(String username);
	Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);
	Optional<User> findByCredentialsUsernameAndCredentialsPasswordAndDeletedFalse(String username, String password);
	List<User> findByProfileCompanyCommonFieldsNameAndDeletedFalse(String companyName);

}
