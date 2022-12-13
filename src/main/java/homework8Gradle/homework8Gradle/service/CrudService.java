package homework8Gradle.homework8Gradle.service;

import java.util.List;

public interface CrudService<T> {
    public T save(T object);
    public List<T> findAll ();
    public T findById(String id);
    public void deleteById(String id);
}
