package com.recruitment.atiperatask.service;

import com.recruitment.atiperatask.dto.UserDto;
import com.recruitment.atiperatask.dto.mappers.RepositoryToRepositoryDtoMapper;
import com.recruitment.atiperatask.exception.UserNotFoundException;
import com.recruitment.atiperatask.model.*;
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

import java.util.List;

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

    @Test
    void testGetUserPositive() throws Exception {
        String username = "testuser";
        String reposUrl = "https://api.github.com/users/testuser/repos";
        String branchUrl = "https://api.github.com/repos/testuser/Repo1/branches";

        User mockUser = new User();
        mockUser.setUsername(username);

        Owner mockOwner = new Owner(username);

        Commit commit = new Commit("123");

        Branch mockBranch = new Branch("testbranch",commit);
        Branch[] mockBranches = new Branch[]{mockBranch};

        Repository mockRepo = new Repository("Repo1", mockOwner, false, List.of());
        Repository[] mockRepos = new Repository[]{mockRepo};

        UserDto mockUserDto = new UserDto(username, List.of(RepositoryToRepositoryDtoMapper.map(mockRepo)));

        when(restTemplate.getForObject(reposUrl, Repository[].class)).thenReturn(mockRepos);
        when(restTemplate.getForObject(branchUrl, Branch[].class)).thenReturn(mockBranches);

        UserDto userDto = gitHubService.getUserRepositories(username);

        assertNotNull(userDto);
        assertEquals(username, userDto.getUsername());
        assertEquals(1, userDto.getRepositoryDtoList().size());
        assertEquals("Repo1", userDto.getRepositoryDtoList().get(0).getName());
        assertEquals("testbranch", userDto.getRepositoryDtoList().get(0).getBranchesList().get(0).getName());
        assertEquals("123", userDto.getRepositoryDtoList().get(0).getBranchesList().get(0).getLastCommitSha());
    }

    @Test
    void testGetUserNegative() {
        String username = "nonexistentuser";
        String userUrl = "https://api.github.com/users/nonexistentuser/repos";

        when(restTemplate.getForObject(userUrl, Repository[].class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(UserNotFoundException.class, () -> gitHubService.getUserRepositories(username));
    }
}
