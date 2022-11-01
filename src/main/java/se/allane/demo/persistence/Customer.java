package se.allane.demo.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Customer extends BaseEntity {
    static final String JOIN_COLUMN_NAME = "customer_id";

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;
}
