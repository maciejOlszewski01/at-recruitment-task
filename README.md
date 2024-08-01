
# Atipera Recruitment Task

This API allows fetching all of a user's non-fork public repositories with branches and the last commit SHA.

## Available Endpoints

### GET /users/{username}/repos

**Example:**


GET http://localhost:8080/users/octocat/repos


**Example Return:**

```json
{
    "username": "octocat",
    "repositoryDtoList": [
        {
            "name": "Hello-World",
            "ownerLogin": "octocat",
            "branchesList": [
                {
                    "name": "main",
                    "lastCommitSha": "5a934e"
                }
            ]
        }
    ]
}
```

## Requirements

- Java 21
- Maven
