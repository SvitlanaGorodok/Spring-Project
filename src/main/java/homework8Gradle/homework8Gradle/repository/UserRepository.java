package homework8Gradle.homework8Gradle.repository;

import homework8Gradle.homework8Gradle.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.email FROM users u", nativeQuery  = true)
    List<String> findAllEmails();

    @Query(value = "SELECT u.* FROM users u " +
            "WHERE LOWER(u.first_name) LIKE :firstName " +
            "AND LOWER(u.last_name) LIKE :lastName " +
            "AND LOWER(u.email) LIKE :email " +
            "AND u.role_id = COALESCE( :roleId , u.role_id) ",
            nativeQuery  = true)
    List<User> findByParameters(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                       @Param("email") String email, @Param("roleId") UUID roleId);
}
