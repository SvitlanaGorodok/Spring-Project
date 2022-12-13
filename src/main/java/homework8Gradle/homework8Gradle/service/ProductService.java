package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService implements CrudService<Product>{
    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        if (product.getId() == null || product.getId().isEmpty()){
            product.setId(UUID.randomUUID().toString());
        }
        return repository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Product findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
