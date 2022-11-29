package ru.kolesnikov.desiccisionfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolesnikov.desiccisionfarm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
