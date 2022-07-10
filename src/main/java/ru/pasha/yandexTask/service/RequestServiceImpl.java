package ru.pasha.yandexTask.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Request;
import ru.pasha.yandexTask.domain.Types;
import ru.pasha.yandexTask.exception.ValidationFailedException;
import ru.pasha.yandexTask.repo.ProductRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final ProductService productService;
    private final ProductRepo productRepo;

    @Override
    public List<Product> parseAndSave(Request request) throws ValidationFailedException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = simpleDateFormat.parse(request.getUpdateDate());
        List<Product> products = new ArrayList<>();
        int extraPrice = 0;
        int extraCount = 0;

        if (date == null) throw new ValidationFailedException();

        Set<UUID> allId = new HashSet<>();

        for (Product product : request.getItems()) {

            if (product.getId() == null || product.getName() == null || product.getType() == null) throw new ValidationFailedException();

            // Offer's price must be not null and bigger or equals to 0

            if (product.getType() == Types.OFFER && product.getPrice() < 0) throw new ValidationFailedException();

            if (product.getType() == Types.CATEGORY && product.getPrice() > 0) throw new ValidationFailedException();
            // Id can't repeat more than 1 time in one request

            int size = allId.size();
            allId.add(product.getId());

            if (size == allId.size()) throw new ValidationFailedException();

            // Valid

            product.setUpdateDate(simpleDateFormat.parse(request.getUpdateDate()));

            if (product.getType() == Types.OFFER) {
                extraPrice += product.getPrice();
                extraCount++;
            }

            products.add(productService.save(product, extraPrice, extraCount));
        }

        return products;
    }
}
