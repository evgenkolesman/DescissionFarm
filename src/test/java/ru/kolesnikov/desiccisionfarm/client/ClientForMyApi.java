package ru.kolesnikov.desiccisionfarm.client;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kolesnikov.desiccisionfarm.controller.dto.TaskResponseDTO;
import ru.kolesnikov.desiccisionfarm.controller.dto.UserDTO;
import ru.kolesnikov.desiccisionfarm.enums.Statuses;
import ru.kolesnikov.desiccisionfarm.model.Task;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientForMyApi {

    private static final String HTTP_LOCALHOST = "http://localhost";
    private static final String ADD_USER_ENDPOINT = "/api/v1/user";
    private static final String DELETE_USER_ENDPOINT = "/api/v1/user/{login}";
    private static final String ADD_TASK_ENDPOINT = "/api/v1/user/%s/task";
    private static final String COMPLETE_TASK_ENDPOINT = "/api/v1/user/%s/task/%s/complete";
    private static final String CREATED_LOGIN_TASK_ENDPOINT = "/api/v1/user/{login}/task/created";
    private static final String CREATED_ALL_TASK_ENDPOINT = "/api/v1/user/{login}/allCreated";
    private static final String HISTORY_TASK_ENDPOINT = "/api/v1/user/{login}/task/{taskId}/history";
    @LocalServerPort
    public int port;


    @BeforeEach
    void init() {
        RestAssured.port = port;

    }

    public UriComponentsBuilder builder() {
        return UriComponentsBuilder
                .fromHttpUrl(HTTP_LOCALHOST)
                .port(String.valueOf(port));
    }

    /**
     * Client for testing how api works with REST
     * and task scenario
     * all data about requests will be in logs
     */

    @Test
    void testScenarioForTask() {

        final String login = "login";
        final String name = "name";
        final String password = "password";

        // add user to DB
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .post(builder()
                        .replacePath(ADD_USER_ENDPOINT)
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(200);

        //add task for user

        var task = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .post(builder()
                        .replacePath(String.format(ADD_TASK_ENDPOINT, login))
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(Task.class);

        //make task complete

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .get(builder()
                        .replacePath(String.format(COMPLETE_TASK_ENDPOINT, login, task.getId()))
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(200);

        // take info about tasks of current user

        var listLoginTasks = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .get(builder()
                        .replacePath(String.format(CREATED_LOGIN_TASK_ENDPOINT, login))
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", TaskResponseDTO.class);
        assertThat(listLoginTasks)
                .isEqualTo(List.of(new TaskResponseDTO(task.getId(), task.getStatus()),
                        new TaskResponseDTO(task.getId(), Statuses.COMPLETE.name())));

        // take info about tasks of all users

        var listAllTasks = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .get(builder()
                        .replacePath(String.format(CREATED_ALL_TASK_ENDPOINT, login))
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", TaskResponseDTO.class);
        assertThat(listAllTasks)
                .isEqualTo(List.of(new TaskResponseDTO(task.getId(), task.getStatus()),
                        new TaskResponseDTO(task.getId(), Statuses.COMPLETE.name())));

        // take task`s history

        var listHistory = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .get(builder()
                        .replacePath(String.format(HISTORY_TASK_ENDPOINT, login, task.getId()))
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", String.class);
        assertThat(listHistory)
                .isEqualTo(List.of(Statuses.RENDERING.name(), Statuses.COMPLETE.name()));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserDTO(login, name, password))
                .when()
                .delete(builder()
                        .replacePath(String.format(DELETE_USER_ENDPOINT, login))
                        .toUriString())
                .then()
                .and().log()
                .all()
                .assertThat()
                .statusCode(204);

    }


}