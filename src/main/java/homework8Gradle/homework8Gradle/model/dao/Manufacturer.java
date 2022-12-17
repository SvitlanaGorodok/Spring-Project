package homework8Gradle.homework8Gradle.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Manufacturer {
    @Id
    String id;

    String name;

    @OneToMany(mappedBy="manufacturer")
    @JsonIgnore
    Set<Product> products;

    public Manufacturer(){
    }

    public Manufacturer(String id){
        this.id = id;
    }
}
