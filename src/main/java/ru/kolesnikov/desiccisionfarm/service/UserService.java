package ru.kolesnikov.desiccisionfarm.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kolesnikov.desiccisionfarm.exceptions.NotFoundUserException;
import ru.kolesnikov.desiccisionfarm.model.User;
import ru.kolesnikov.desiccisionfarm.repository.UserRepository;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void addUser(User user) {
        try {
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),
                    MessageFormat.format("{0}  This user can`t be saved {1} because {2}",
                            UUID.randomUUID().toString(),
                            user,
                            e.getMessage()));
        }
    }

    public void deleteUser(String login) {
        userRepository.deleteByLogin(login);
    }

    public User findByLogin(@NonNull String login) {
        return userRepository
                .findById(login)
                .orElseThrow(() -> new NotFoundUserException(login));
    }
}
