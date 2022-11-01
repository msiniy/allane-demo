package se.allane.demo.persistence;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@ToString
public class LeasingContract extends BaseEntity {

    static final String JOIN_COLUMN_NAME = "contract_id";

    @Column(nullable = false)
    private Long contractNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = Customer.JOIN_COLUMN_NAME, nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "contract_customer_fk"))
    private Customer customer;

    @OneToOne(optional = false, mappedBy = "contract")
    private Vehicle vehicle;

    @Column(nullable = false)
    private BigDecimal monthlyRate;
}
