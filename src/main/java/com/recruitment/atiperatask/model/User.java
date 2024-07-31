package com.recruitment.atiperatask.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class User {
    String username;
    List<Repository> repositoryList;

    public User(String username) {
        this.username = username;
        repositoryList = new ArrayList<>();
    }
}
