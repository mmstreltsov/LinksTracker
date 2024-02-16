package ru.tinkoff.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ServiceNames {
    GITHUB("github.com"),
    STACKOVERFLOW("stackoverflow.com");

    public final String hostName;
}
