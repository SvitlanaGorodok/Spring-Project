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
            "AND p.start_date >= COALESCE( :startDateFrom , p.start_date) " +
            "AND p.start_date <= COALESCE( :startDateTo , p.start_date)",
            nativeQuery = true)
    List<Product> findByParameters(@Param("name") String name, @Param("startDateFrom") Long startDateFrom,
                                      @Param("startDateTo") Long startDateTo);
}
