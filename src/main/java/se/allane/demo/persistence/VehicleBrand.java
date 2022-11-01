package se.allane.demo.persistence;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class VehicleBrand extends BaseEntity {
    static final String JOIN_COLUMN_NAME = "vehicle_brand_id";

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand")
    private Set<VehicleModel> models = new HashSet<>();
}
