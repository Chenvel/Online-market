package ru.pasha.yandexTask.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pasha.yandexTask.domain.Product;
import ru.pasha.yandexTask.domain.Types;
import ru.pasha.yandexTask.exception.ItemNotFoundException;
import ru.pasha.yandexTask.exception.ValidationFailedException;
import ru.pasha.yandexTask.repo.ProductRepo;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
            Product productToSetAvgPrice = product;

            double exPrice = extraPrice;
            int exCount = extraCount;

            if (productRepo.existsById(product.getId())) {
                exPrice = 0.0;
                exCount = 0;
            }

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
    public Product getNodes(UUID id) throws ItemNotFoundException {
        Optional<Product> product = productRepo.findById(id);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public List<Product> sales(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        List<Product> products = new ArrayList<>();
        Date dateRequest = simpleDateFormat.parse(date);;
        for (Product product : productRepo.findAll()) {
            long diffDate = dateRequest.getTime() - product.getUpdateDate().getTime();

            if (diffDate <= 86400000 && diffDate >= 0) {
                products.add(product);
            }
        }

        return products;
    }
}
