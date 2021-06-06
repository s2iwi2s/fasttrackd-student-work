package com.cooksys.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.backend.entities.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>{
    Optional<Company> findByCommonFieldsName(String companyName);

}
