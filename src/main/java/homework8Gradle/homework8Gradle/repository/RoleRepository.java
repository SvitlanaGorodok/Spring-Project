package homework8Gradle.homework8Gradle.repository;

import homework8Gradle.homework8Gradle.model.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
