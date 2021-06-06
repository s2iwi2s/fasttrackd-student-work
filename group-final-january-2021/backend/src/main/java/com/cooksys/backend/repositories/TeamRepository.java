package com.cooksys.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.backend.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

    Optional<Team> findByCommonFieldsName(String teamName);

    Optional<Team> findByCommonFieldsNameAndCompanyCommonFieldsName(String teamName, String companyName);
}
