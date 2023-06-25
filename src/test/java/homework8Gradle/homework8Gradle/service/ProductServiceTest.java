package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapperImpl;
import homework8Gradle.homework8Gradle.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    ProductRepository repository;

    ProductService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new ProductService(repository, new EntityMapperImpl());
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void save() {
        UUID productId = UUID.randomUUID();
        UUID manufacturerId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("name");
        productDto.setPrice(0L);
        productDto.setManufacturerId(manufacturerId);

        when(repository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        ProductDto saved = service.save(productDto);

        assertEquals(productDto.getId(), saved.getId());
        assertEquals(productDto.getName(), saved.getName());
        assertEquals(productDto.getPrice(), saved.getPrice());
        assertEquals(productDto.getManufacturerId(), saved.getManufacturerId());

    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByParameters() {
    }
}