package com.recruitment.atiperatask.contoller;

import com.recruitment.atiperatask.dto.UserDto;
import com.recruitment.atiperatask.service.GitHubService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class GitHubController {
    private final GitHubService githubService;

    @GetMapping("/{username}/repos")
    private ResponseEntity<UserDto> findRepos(@PathVariable String username){
        return ResponseEntity.ok(githubService.getUserRepositories(username));
    }

}
