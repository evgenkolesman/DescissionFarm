package ru.kolesnikov.desiccisionfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolesnikov.desiccisionfarm.model.Task;
import ru.kolesnikov.desiccisionfarm.model.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserAndStatus(User user, String name);

    List<Task> findAllByName(String name);

    List<Task> findAllByStatus(String status);
}
