package homework8Gradle.homework8Gradle.model.dto;

import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import homework8Gradle.homework8Gradle.model.dao.Product;
import lombok.Data;

@Data
public class ProductDto {
    String id;
    String name;
    Long price;
    String manufacturerId;

    public static Product toProduct(ProductDto productDto, ManufacturerDto manufacturerDto){
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setManufacturer(ManufacturerDto.toManufacturer(manufacturerDto));
        return product;
    }

    public static ProductDto fromProduct(Product product){
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setManufacturerId(product.getManufacturer().getId());
        return productDto;
    }
}
