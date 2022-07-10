package ru.pasha.yandexTask.exception;

public class ItemNotFoundException extends Throwable {

    public ItemNotFoundException() {
        super("Item not found");
    }
}
