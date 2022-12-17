package homework8Gradle.homework8Gradle.repository;

import homework8Gradle.homework8Gradle.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String email);
}
