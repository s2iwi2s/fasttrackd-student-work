package com.cooksys.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.backend.entities.Project;
import com.cooksys.backend.entities.Team;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    Optional<Project> findByCommonFieldsNameAndTeamId(String projectName, Long teamId);

}
