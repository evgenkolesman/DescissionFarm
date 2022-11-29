package ru.kolesnikov.desiccisionfarm.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundTaskException extends ResponseStatusException {
    public NotFoundTaskException(Long id) {
        super(HttpStatusCode.valueOf(404),
                "User was not found with login " + id);
    }
}
