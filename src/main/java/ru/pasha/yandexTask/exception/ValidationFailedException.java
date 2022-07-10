package ru.pasha.yandexTask.exception;

public class ValidationFailedException extends Throwable {

    public ValidationFailedException() {
        super("Validation Failed");
    }
}
