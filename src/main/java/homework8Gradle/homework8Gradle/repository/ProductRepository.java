package homework8Gradle.homework8Gradle.repository;

import homework8Gradle.homework8Gradle.model.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "SELECT p.* FROM products p " +
            "WHERE LOWER(p.name) LIKE :name " +
            "AND p.price >= COALESCE( :overPrice , p.price) " +
            "AND p.price <= COALESCE( :underPrice , p.price)" +
            "AND p.manufacturer_id = COALESCE( :manufacturerId , p.manufacturer_id)",
            nativeQuery = true)
    List<Product> findByParameters(@Param("name") String name, @Param("overPrice") Long overPrice,
                                      @Param("underPrice") Long underPrice, UUID manufacturerId);
}
