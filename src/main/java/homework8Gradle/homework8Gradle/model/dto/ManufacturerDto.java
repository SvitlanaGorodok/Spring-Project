package homework8Gradle.homework8Gradle.model.dto;

import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dao.Product;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ManufacturerDto {
    String id;
    String name;

    public static Manufacturer toManufacturer(ManufacturerDto manufacturerDto){
        if (manufacturerDto == null) {
            return null;
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(UUID.fromString(manufacturerDto.getId()));
        manufacturer.setName(manufacturerDto.getName());
        manufacturer.setProducts(new HashSet<>());
        return manufacturer;
    }

    public static ManufacturerDto fromManufacturer(Manufacturer manufacturer){
        if (manufacturer == null) {
            return null;
        }
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(manufacturer.getId().toString());
        manufacturerDto.setName(manufacturer.getName());
        return manufacturerDto;
    }
}
