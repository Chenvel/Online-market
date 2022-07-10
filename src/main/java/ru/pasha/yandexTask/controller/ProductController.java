package ru.pasha.yandexTask.controller;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Request;
import ru.pasha.yandexTask.domain.Types;
import ru.pasha.yandexTask.exception.ItemNotFoundException;
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
    public ResponseEntity<MainPattern> importProduct(@RequestBody(required = false) Request request) throws ParseException {
        try {
            requestService.parseAndSave(request);

            return ResponseEntity.ok(new MainPattern((short) 200, "Operation completed successfully"));
        } catch (ValidationFailedException e) {
            return ResponseEntity.badRequest().body(new MainPattern((short) 400, "Validation Failed"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MainPattern> deleteProduct(@PathVariable UUID id) {
        try {
            productService.delete(id);

            return ResponseEntity.ok(new MainPattern((short) 200, "Operation completed successfully"));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MainPattern((short) 404, "Item not found"));
        }
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> allProducts(@PathVariable UUID id) throws ItemNotFoundException {
        try {
            return ResponseEntity.ok().body(productService.getNodes(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MainPattern((short) 404, "Item not found"));
        }
    }
}
