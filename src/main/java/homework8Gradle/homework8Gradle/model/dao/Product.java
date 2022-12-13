package homework8Gradle.homework8Gradle.model.dao;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
public class Product {
    @Id
    String id;

    String name;
    Long price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manufacturer_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Manufacturer manufacturer;

}
