package homework8Gradle.homework8Gradle.model.dao;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

//@Entity
@Setter
@Getter
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
