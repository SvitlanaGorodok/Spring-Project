package homework8Gradle.homework8Gradle.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "manufacturers")
@Entity
@Setter
@Getter
public class Manufacturer {
    @Id
    String id;

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="manufacturer")
    Set<Product> products;

    public Manufacturer(){
    }

    public Manufacturer(String id){
        this.id = id;
    }
}
