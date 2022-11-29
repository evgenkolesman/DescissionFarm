package ru.kolesnikov.desiccisionfarm.controller.dto;

import javax.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank String login,
        @NotBlank String name,
        @NotBlank String password
) {
}
