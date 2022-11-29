package ru.kolesnikov.desiccisionfarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kolesnikov.desiccisionfarm.controller.dto.TaskDTO;
import ru.kolesnikov.desiccisionfarm.controller.dto.TaskResponseDTO;
import ru.kolesnikov.desiccisionfarm.enums.Statuses;
import ru.kolesnikov.desiccisionfarm.model.Task;
import ru.kolesnikov.desiccisionfarm.service.TaskService;
import ru.kolesnikov.desiccisionfarm.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;


    @PostMapping("/api/v1/user/{login}/task")
    public Task addTask(@RequestBody TaskDTO task,
                        @PathVariable String login) {

       return taskService.addTask(new Task(task.name(),
                task.description(),
                userService.findByLogin(login)));

    }

    @GetMapping("/api/v1/user/{login}/task/{taskId}/complete")
    public void updateCompleteTask(@PathVariable String login,
                                   @PathVariable Long taskId) {
        var task = taskService.findTaskById(taskId);
        taskService.updateStatusTask(new Task(
                task.getName(),
                task.getDescription(),
                Statuses.COMPLETE.name(),
                userService.findByLogin(login)));

    }

    @GetMapping("/api/v1/user/{login}/task/created")
    public List<TaskResponseDTO> createdTasksByUser(@PathVariable String login) {
        userService.findByLogin(login); //for check authorization
        return taskService.findCreatedTasksByLogin(userService.findByLogin(login)).stream()
                .map(task -> new TaskResponseDTO(task.getId(), task.getStatus()))
                .collect(Collectors.toList());

    }

    @GetMapping("/api/v1/user/{login}/allCreated")
    public List<TaskResponseDTO> createdTasks(@PathVariable String login) {
        userService.findByLogin(login); //for check authorization
        return taskService.findAllCreatedTasks().stream()
                .map(task -> new TaskResponseDTO(task.getId(), task.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/user/{login}/task/{taskId}/history")
    public List<String> historyStatusesTask(@PathVariable String login,
                                            @PathVariable Long taskId) {
        userService.findByLogin(login); //for check authorization

        return taskService.historyStatusesTask(taskId);
    }

}
