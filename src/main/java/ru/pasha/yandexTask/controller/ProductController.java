package ru.pasha.yandexTask.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Request;
import ru.pasha.yandexTask.domain.Types;
import ru.pasha.yandexTask.exception.ValidationFailedException;
import ru.pasha.yandexTask.repo.ProductRepo;
import ru.pasha.yandexTask.response_patterns.MainPattern;
import ru.pasha.yandexTask.service.ProductService;
import ru.pasha.yandexTask.service.RequestService;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepo productRepo;
    private final RequestService requestService;

    @PostMapping(value = "/imports", produces = "application/json")
    public MainPattern importProduct(@RequestBody(required = false) Request request) throws ValidationFailedException, ParseException {
        try {
            requestService.parseAndSave(request);

            return new MainPattern((short) 200, "Операция успешно выполнена");
        } catch (ValidationFailedException e) {
            return new MainPattern((short) 400, "Validation Failed");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        ResponseEntity<?> response = productService.delete(id);

        return response;
    }

    @GetMapping("/products")
    public List<Product> allProducts() {
        return productRepo.findAll();
    }
}