package ru.practicum.services;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    public ManagerSaveException() {
    }

    public ManagerSaveException(String message) {
        super(message);
    }
}
