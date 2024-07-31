package com.recruitment.atiperatask.service;

import com.recruitment.atiperatask.exception.UserNotFoundException;
import com.recruitment.atiperatask.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GitHubServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Value("${github.api.url}")
    private String githubApiUrl;
    @InjectMocks
    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubService = new GitHubService(restTemplate,githubApiUrl);
    }

//    @Test
//    void testGetUser_Positive() throws Exception {
//        String username = "testuser";
//        String userUrl = "https://api.github.com/users/{username}";
//        String reposUrl = "https://api.github.com/users/{username}/repos";
//    }

    @Test
    void testGetUser_Negative() {
        String username = "nonexistentuser";
        String userUrl = "https://api.github.com/users/nonexistentuser/repos";

        when(restTemplate.getForObject(userUrl, Repository[].class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(UserNotFoundException.class, () -> gitHubService.getUserRepositories(username));
    }
}
