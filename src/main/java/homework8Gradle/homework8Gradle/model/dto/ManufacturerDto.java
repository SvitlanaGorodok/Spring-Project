package homework8Gradle.homework8Gradle.model.dto;

import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dao.Product;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ManufacturerDto {
    UUID id;
    String name;
}
