package se.allane.demo.persistence;

import lombok.Getter;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Year;

@Entity
@Getter
public class VehicleModel extends BaseEntity {
    static final String JOIN_COLUMN_NAME = "vehicle_model_id";

    @Converter(autoApply = true)
    static class YearConverter implements AttributeConverter<Year, Short> {

        @Override
        public Short convertToDatabaseColumn(Year attribute) {
            return (short) attribute.getValue();
        }

        @Override
        public Year convertToEntityAttribute(Short dbData) {
            return Year.of(dbData);
        }
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Year year;

    @ManyToOne(optional = false)
    @JoinColumn(name = VehicleBrand.JOIN_COLUMN_NAME, nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "vehicle_model_brand_fk"))
    private VehicleBrand brand;
}
