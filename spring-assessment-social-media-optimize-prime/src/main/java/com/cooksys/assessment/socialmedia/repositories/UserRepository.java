package com.cooksys.assessment.socialmedia.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.assessment.socialmedia.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Set<User> findByActiveTrue();

	Optional<User> findByCredentialsUsernameAndActive(String username, boolean active);

	Optional<User> findByCredentialsUsername(String username);
}
