package se.allane.demo.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Vehicle extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = VehicleModel.JOIN_COLUMN_NAME, nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "vehicle_model_fk"))
    private VehicleModel model;

    @Column(length = 17)
    private String vin;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = LeasingContract.JOIN_COLUMN_NAME,
            foreignKey = @ForeignKey(name = "vehicle_contract_fk"))
    private LeasingContract contract;
}
