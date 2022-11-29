package ru.kolesnikov.desiccisionfarm.enums;

import lombok.Getter;

@Getter
public enum Statuses {

    RENDERING("RENDERING"),
    COMPLETE("COMPLETE");

    Statuses(String rendering) {
    }
}
