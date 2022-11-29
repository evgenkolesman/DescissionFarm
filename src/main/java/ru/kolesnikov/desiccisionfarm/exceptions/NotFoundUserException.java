package ru.kolesnikov.desiccisionfarm.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundUserException extends ResponseStatusException {
    public NotFoundUserException(@NonNull String login) {
        super(HttpStatusCode.valueOf(404),
                "User was not found with login " + login);
    }
}
