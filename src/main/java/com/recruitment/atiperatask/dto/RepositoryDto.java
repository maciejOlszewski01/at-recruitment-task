package com.recruitment.atiperatask.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryDto {
    private String name;
    private String ownerLogin;
    private List<BranchDto> branchesList;
}
