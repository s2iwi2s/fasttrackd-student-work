package com.cooksys.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDto {
    private String name;
    private String text;
    private CompanyDto company;
}
