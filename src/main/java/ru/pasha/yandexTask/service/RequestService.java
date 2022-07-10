package ru.pasha.yandexTask.service;

import org.springframework.stereotype.Component;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Request;
import ru.pasha.yandexTask.exception.ValidationFailedException;

import java.text.ParseException;
import java.util.List;

public interface RequestService {

    List<Product> parseAndSave(Request request) throws ValidationFailedException, ParseException;
}
