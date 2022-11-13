package se.allane.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.allane.demo.persistence.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT new se.allane.demo.repositories.CustomerDto(c.id, c.version, c.firstName, c.lastName, c.birthDate) " +
            "FROM Customer c ")
    Page<CustomerDto> findCustomers(Pageable pageRequest);

    @Query("SELECT new se.allane.demo.repositories.CustomerDto(c.id, c.version, c.firstName, c.lastName, c.birthDate) " +
            "FROM Customer c WHERE c.id = :id")
    Optional<CustomerDto> getCustomerDetails(Long id);

    Optional<Customer> findByIdAndVersion(Long id, Integer version);
}
