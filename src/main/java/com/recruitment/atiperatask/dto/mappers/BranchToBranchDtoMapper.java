package com.recruitment.atiperatask.dto.mappers;


import com.recruitment.atiperatask.dto.BranchDto;
import com.recruitment.atiperatask.model.Branch;

public class BranchToBranchDtoMapper {
    public static BranchDto map(Branch branch){
        BranchDto branchDto = new BranchDto(
                branch.getName(),
                branch.getCommit().getSha()
        );
        return branchDto;
    }
}
