package homework8Gradle.homework8Gradle.controller;

import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.service.ManufacturerService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManufacturerControllerTest {

    @Mock
    private ManufacturerService service;

    @InjectMocks
    private ManufacturerController controller;

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
        List<ManufacturerDto> manufacturers = new ArrayList<>();
        manufacturers.add(new ManufacturerDto());
        manufacturers.add(new ManufacturerDto());

        when(service.findAll()).thenReturn(manufacturers);

        mockMvc.perform(get("/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(view().name("/manufacturers/findall"))
                .andExpect(model().attribute("manufacturers", hasSize(2)));
    }

    @Test
    void saveForm() {
    }

    @Test
    void save() {
    }

    @Test
    void updateForm() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}