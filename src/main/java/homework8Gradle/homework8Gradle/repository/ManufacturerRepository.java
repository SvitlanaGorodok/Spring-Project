package homework8Gradle.homework8Gradle.repository;

import homework8Gradle.homework8Gradle.model.dao.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {
    @Query(value = "SELECT m.name FROM manufacturers m", nativeQuery  = true)
    List<String> findAllNames();
}
