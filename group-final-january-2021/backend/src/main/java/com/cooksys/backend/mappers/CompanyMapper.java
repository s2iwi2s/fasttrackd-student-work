package com.cooksys.backend.mappers;

import java.util.List;

import com.cooksys.backend.dtos.CompanyDto;
import com.cooksys.backend.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	@Mapping(target = "name", source = "commonFields.name")
	@Mapping(target = "text", source = "commonFields.text")
	public CompanyDto entityToDto(Company company);

	public List<CompanyDto> entitiesToDtos(List<Company> companies);

	@Mapping(source = "name", target = "commonFields.name")
	@Mapping(source = "text", target = "commonFields.text")
	public Company dtoToEntity(CompanyDto companyDto);
}
