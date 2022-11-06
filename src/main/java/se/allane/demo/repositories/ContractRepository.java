package se.allane.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.allane.demo.persistence.LeasingContract;

public interface ContractRepository extends JpaRepository<LeasingContract, Long> {
    @Query("SELECT new se.allane.demo.repositories.ContractDto(c.id, c.version, c.contractNumber, c.monthlyRate, " +
            "customer.firstName, customer.lastName, " +
            "vehicle.vin, vehicle.price, " +
            "brand.name, model.name, model.year) " +
            "FROM LeasingContract c " +
            "JOIN Customer customer on c.customer = customer " +
            "JOIN Vehicle vehicle on c.vehicle = vehicle " +
            "JOIN VehicleModel model on vehicle.model = model " +
            "JOIN VehicleBrand brand on model.brand = brand ")
    Page<ContractDto> findContracts(Pageable pageRequest);
}
