package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.service.ManufacturerService;
import homework8Gradle.homework8Gradle.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    ProductService service;
    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ProductController controller;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }


    @Test
    void showAll() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto());
        products.add(new ProductDto());

        when(service.findAll()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/findall"))
                .andExpect(model().attribute("products", hasSize(2)));
    }

    @Test
    void saveForm() throws Exception {
        List<ManufacturerDto> manufacturers = new ArrayList<>();
        manufacturers.add(new ManufacturerDto());
        manufacturers.add(new ManufacturerDto());

        when(manufacturerService.findAll()).thenReturn(manufacturers);
        mockMvc.perform(get("/products/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/save"))
                .andExpect(model().attribute("manufacturers", hasSize(2)));

    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post("/products/save")
                        .param("name", "name")
                        .param("price", "0")
                        .param("manufacturerId", UUID.randomUUID().toString())
                        .flashAttr("productDto", new ProductDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

    }

    @Test
    void updateForm() throws Exception {
        List<ManufacturerDto> manufacturers = new ArrayList<>();
        manufacturers.add(new ManufacturerDto());
        manufacturers.add(new ManufacturerDto());

        UUID id = UUID.randomUUID();

        ProductDto product = new ProductDto();
        product.setId(id);
        product.setName("name");
        product.setPrice(0L);
        product.setManufacturerId(UUID.randomUUID());
        product.setProductDetails("details");

        when(manufacturerService.findAll()).thenReturn(manufacturers);
        when(service.findById(id)).thenReturn(product);

        mockMvc.perform(get("/products/update/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("products/update"))
                .andExpect(model().attribute("product", product))
                .andExpect(model().attribute("manufacturers", hasSize(2)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/products/delete/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    void findForm() {

    }

    @Test
    void find() {
    }
}