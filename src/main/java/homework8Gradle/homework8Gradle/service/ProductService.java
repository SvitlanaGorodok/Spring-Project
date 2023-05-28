package homework8Gradle.homework8Gradle.service;

import homework8Gradle.homework8Gradle.exception.NoSuchEntityFoundException;
import homework8Gradle.homework8Gradle.model.dto.FindProductParam;
import homework8Gradle.homework8Gradle.model.mapper.EntityMapper;
import homework8Gradle.homework8Gradle.model.dao.Product;
import homework8Gradle.homework8Gradle.model.dto.ProductDto;
import homework8Gradle.homework8Gradle.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService implements CrudService<ProductDto>{
    private final ProductRepository repository;
    private final EntityMapper mapper;

    @Override
    public ProductDto save(ProductDto productDto) {
        if (productDto.getId() == null){
            productDto.setId(UUID.randomUUID());
        }
        Product saved = repository.save(mapper.productToDao(productDto));
        return mapper.productToDto(saved);
    }

    @Override
    public List<ProductDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::productToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(UUID id) {
        Product productById = repository.findById(id)
                .orElseThrow(() -> new NoSuchEntityFoundException("Product with id " + id + "not found!"));
        return mapper.productToDto(productById);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<ProductDto> findByParameters(FindProductParam findProductParam) {
        return null;
    }

    private String sqlFormat(String text){
        if(text == null || text.isEmpty()){
            return "%%";
        }
        return "%" + text.toLowerCase() + "%";
    }
}
