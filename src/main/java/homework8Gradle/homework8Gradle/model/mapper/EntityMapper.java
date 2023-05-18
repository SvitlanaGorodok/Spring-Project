package homework8Gradle.homework8Gradle.model.mapper;

import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.model.dao.User;
import homework8Gradle.homework8Gradle.model.dto.ManufacturerDto;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "Spring", imports = {LocalDate.class})
public interface EntityMapper {

    @Mapping(target = "roleId", source = "user.role.id")
    @Mapping(target = "userDetails", source = "user.role.name")
    UserDto userToDto(User user);
    @Mapping(target = "role.id", source = "userDto.roleId")
    User userToDao(UserDto userDto);

    @Mapping(target = "products", ignore = true)
    Manufacturer manufacturerToDao(ManufacturerDto manufacturerDto);
    ManufacturerDto manufacturerToDto(Manufacturer manufacturer);

    @Mapping(target = "manufacturerId", source = "product.manufacturer.id")
    @Mapping(target = "productDetails", source = "product.manufacturer.name")
    ProductDto productToDto(Product product);
    @Mapping(target = "manufacturer.id", source = "productDto.manufacturerId")
    Product productToDao(ProductDto productDto);
}
