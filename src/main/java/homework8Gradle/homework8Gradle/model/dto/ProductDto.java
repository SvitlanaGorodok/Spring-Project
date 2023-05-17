package homework8Gradle.homework8Gradle.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    UUID id;
    String name;
    Long price;
    UUID manufacturerId;
    String productDetails;
}
