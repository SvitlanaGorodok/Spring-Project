package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.model.dto.FindProductParam;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapperImpl;
import homework8Gradle.homework8Gradle.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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
    void saveWithNullId() {
        when(repository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);
        ProductDto toSave = new ProductDto();
        assertNull(toSave.getId());

        ProductDto saved = service.save(toSave);
        assertNotNull(saved.getId());

    }

    @Test
    void findAll() {

        UUID productId = UUID.randomUUID();
        UUID manufacturerId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("name");
        productDto.setPrice(0L);
        productDto.setManufacturerId(manufacturerId);

        Product product = new Product();
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerId);
        product.setId(productId);
        product.setName("name");
        product.setPrice(0L);
        product.setManufacturer(manufacturer);

        when(repository.findAll()).thenReturn(List.of(product));

        List<ProductDto> productDtos = service.findAll();

        assertEquals(productDtos.get(0).getId(), productDto.getId());
        assertEquals(productDtos.get(0).getName(), productDto.getName());
        assertEquals(productDtos.get(0).getPrice(), productDto.getPrice());
        assertEquals(productDtos.get(0).getManufacturerId(), productDto.getManufacturerId());

    }

    @Test
    void findById() {
        UUID productId = UUID.randomUUID();

        Product product = new Product();
        product.setId(productId);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(product));

        ProductDto productDto = service.findById(productId);

        assertEquals(productId, productDto.getId());
    }

    @Test
    void findByIdNoSuchEntityFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NoSuchEntityFoundException exception = assertThrows(NoSuchEntityFoundException.class,
                () -> service.findById(id));
        assertEquals("Product with id " + id + "not found!", exception.getMessage());
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(any(UUID.class));
        service.deleteById(id);
        verify(repository,times(1)).deleteById(any(UUID.class));
    }

    @Test
    void findByParameters() {
        FindProductParam productParam = new FindProductParam();
        UUID manufacturerId = UUID.randomUUID();
        productParam.setName("name");
        productParam.setUnderPrice(3L);
        productParam.setOverPrice(1L);
        productParam.setManufacturerId(manufacturerId);

        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("name");
        productDto.setPrice(2L);
        productDto.setManufacturerId(manufacturerId);

        Product product = new Product();
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerId);
        product.setId(productId);
        product.setName("name");
        product.setPrice(2L);
        product.setManufacturer(manufacturer);

        when(repository.findByParameters(anyString(), anyLong(), anyLong(), any(UUID.class)))
                .thenReturn(List.of(product));

        ProductDto findProduct = service.findByParameters(productParam).get(0);

        assertEquals(findProduct.getName(), productParam.getName());
        assertEquals(findProduct.getManufacturerId(), productParam.getManufacturerId());
        assertTrue(findProduct.getPrice() > productParam.getOverPrice());
        assertTrue(findProduct.getPrice() < productParam.getUnderPrice());
    }
}