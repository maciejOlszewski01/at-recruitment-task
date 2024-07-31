package com.recruitment.atiperatask.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Repository {

    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branchesList;

    public boolean isNotFork() {
        return !fork;
    }
}
