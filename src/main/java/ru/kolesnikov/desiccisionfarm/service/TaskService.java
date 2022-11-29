package ru.kolesnikov.desiccisionfarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolesnikov.desiccisionfarm.enums.Statuses;
import ru.kolesnikov.desiccisionfarm.exceptions.NotFoundTaskException;
import ru.kolesnikov.desiccisionfarm.model.Task;
import ru.kolesnikov.desiccisionfarm.model.User;
import ru.kolesnikov.desiccisionfarm.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task findTaskById(Long taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException(taskId));
    }

    public void updateStatusTask(Task task) {
        taskRepository.save(task);
    }

    public List<Task> findCreatedTasksByLogin(User user) {
        return taskRepository.findAllByUserAndStatus(user, Statuses.RENDERING.name());
    }

    public List<Task> findAllCreatedTasks() {
        return taskRepository.findAllByStatus(Statuses.RENDERING.name());

    }

    public List<String> historyStatusesTask(Long taskId) {
        Task taskById = findTaskById(taskId);

        return taskRepository.findAllByName(taskById.getName())
                .stream()
                .sequential()
                .map(Task::getStatus)
                .collect(Collectors.toList());
    }
}
