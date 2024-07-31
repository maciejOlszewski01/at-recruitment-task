package com.recruitment.atiperatask.service;

import com.recruitment.atiperatask.dto.UserDto;
import com.recruitment.atiperatask.dto.mappers.UserToUserDtoMapper;
import com.recruitment.atiperatask.exception.UserNotFoundException;
import com.recruitment.atiperatask.model.Branch;
import com.recruitment.atiperatask.model.Repository;
import com.recruitment.atiperatask.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {


    public GitHubService(RestTemplate restTemplate,
                         @Value("${github.api.url}") String githubApiUrl) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
    }
    private final RestTemplate restTemplate;
    private final String githubApiUrl;

    public UserDto getUserRepositories(String username) {
        User checkedUser = new User(username);

        try {
            List<Repository> nonForkRepos = fetchRepositories(username);
            nonForkRepos.forEach(repository -> repository.setBranchesList(fetchBranches(username, repository.getName())));
            checkedUser.setRepositoryList(nonForkRepos);
            return UserToUserDtoMapper.map(checkedUser);
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
            throw e;
        }
    }

    private List<Repository> fetchRepositories(String username) {
        String url = githubApiUrl + "/users/" + username + "/repos";
        Repository[] repositories = restTemplate.getForObject(url, Repository[].class);

        if (repositories == null) {
            return List.of();
        }

        return Arrays.stream(repositories)
                .filter(Repository::isNotFork)
                .toList();
    }

    private List<Branch> fetchBranches(String username, String repositoryName) {
        String branchesUrl = githubApiUrl + "/repos/" + username + "/" + repositoryName + "/branches";
        Branch[] branches = restTemplate.getForObject(branchesUrl, Branch[].class);
        return branches != null ? List.of(branches) : List.of();
    }

    private void handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new UserNotFoundException();
        }
    }

}
