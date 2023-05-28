package homework8Gradle.homework8Gradle.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class FindProductParam {
    String name;
    Long underPrice;
    Long overPrice;
    UUID manufacturerId;
}
