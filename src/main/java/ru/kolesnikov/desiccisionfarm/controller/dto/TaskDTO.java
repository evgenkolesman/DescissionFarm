package ru.kolesnikov.desiccisionfarm.controller.dto;

import javax.validation.constraints.NotBlank;

public record TaskDTO(@NotBlank String name,
                      @NotBlank String description) {
}
