package ru.pasha.yandexTask.service;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Request;
import ru.pasha.yandexTask.exception.ItemNotFoundException;
import ru.pasha.yandexTask.exception.ValidationFailedException;

import java.util.UUID;

public interface ProductService {

    Product save(Product product, Integer extraPrice, Integer extraCount) throws ValidationFailedException;
    void delete(UUID id) throws ItemNotFoundException;
    Product getNodes(UUID id) throws ItemNotFoundException;
}
