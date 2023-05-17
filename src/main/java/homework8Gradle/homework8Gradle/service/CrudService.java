package homework8Gradle.homework8Gradle.service;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {
    public T save(T object);
    public List<T> findAll ();
    public T findById(UUID id);
    public void deleteById(UUID id);
}
