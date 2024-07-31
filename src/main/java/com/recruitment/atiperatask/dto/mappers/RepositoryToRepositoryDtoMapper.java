package com.recruitment.atiperatask.dto.mappers;


import com.recruitment.atiperatask.dto.RepositoryDto;
import com.recruitment.atiperatask.model.Repository;

public class RepositoryToRepositoryDtoMapper {

    public static RepositoryDto map(Repository repository){
        RepositoryDto userDto = new RepositoryDto(
                repository.getName(),
                repository.getOwner().getLogin(),
                repository.getBranchesList().stream().map(BranchToBranchDtoMapper::map).toList()
        );
        return userDto;
    }
}
