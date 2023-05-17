package homework8Gradle.homework8Gradle.repository;

import homework8Gradle.homework8Gradle.model.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
