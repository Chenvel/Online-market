package ru.pasha.yandexTask.service;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Request;
import ru.pasha.yandexTask.domain.Types;
import ru.pasha.yandexTask.exception.ItemNotFoundException;
import ru.pasha.yandexTask.exception.ValidationFailedException;
import ru.pasha.yandexTask.repo.ProductRepo;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public Product save(Product product, Integer extraPrice, Integer extraCount) {

        boolean isProductExist = false;

        if (product.getParentId() != null) {
            Optional<Product> optionalParent = productRepo.findById(product.getParentId());

            // Add parent to offer

            optionalParent.ifPresent(product::setParent);
        }

        // Update product

        Optional<Product> oldProductExist = productRepo.findById(product.getId());
        if (oldProductExist.isPresent()) {
            oldProductExist.get().update(product);

            // Clean session from second same object

            isProductExist = true;
            product = oldProductExist.get();
        }

        // Set parent
        if (product.getType() == Types.OFFER) {
            System.out.println("Product's parent: " + product.getParent());

            Product productToSetAvgPrice = product;

            double exPrice = extraPrice;
            int exCount = extraCount;

            if (productRepo.existsById(product.getId())) {
                exPrice = 0.0;
                exCount = 0;
            }

            System.out.println(exPrice);

            while (productToSetAvgPrice != null) {

                if (productToSetAvgPrice.getType() == Types.CATEGORY) {
                    productToSetAvgPrice.setPrice((int) productToSetAvgPrice.averagePrice(exPrice, exCount));

                    exPrice = 0.0;
                    exCount = 0;
                }

                productToSetAvgPrice = productToSetAvgPrice.getParent();
            }
        }

        if (isProductExist) {
            return oldProductExist.get();
        }

        return productRepo.save(product);
    }

    @Override
    public void delete(UUID id) throws ItemNotFoundException {
        Optional<Product> productToDelete = productRepo.findById(id);

        if (productToDelete.isPresent()) {
            productRepo.delete(productToDelete.get());
        } else {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public RequestEntity<Product> getNodes(String id) {
        return null;
    }
}
