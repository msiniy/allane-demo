package se.allane.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.allane.demo.persistence.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT new se.allane.demo.repositories.VehicleDto(v.id, b.name, m.name, m.year, v.vin, v.price) " +
            "FROM Vehicle v " +
            "JOIN VehicleModel m ON m = v.model " +
            "JOIN VehicleBrand b ON b = m.brand ")
    Page<VehicleDto> findVehicles(Pageable pageRequest);


    @Query("SELECT new se.allane.demo.repositories.VehicleDetailsDto (v.id, v.version, v.vin, v.price, " +
            "b.id, b.name, m.id, m.name, m.year, c.id, c.contractNumber) " +
            "FROM Vehicle v " +
            "JOIN VehicleModel m ON m = v.model " +
            "JOIN VehicleBrand b ON b = m.brand " +
            "LEFT JOIN LeasingContract c ON c = v.contract " +
            "WHERE v.id = :id")
    Optional<VehicleDetailsDto> getVehicleDetails(Long id);

    @Query("SELECT new se.allane.demo.repositories.ModelAndBrandDto(m.id, m.name, m.year, b.id, b.name) " +
            "FROM VehicleModel m " +
            "JOIN VehicleBrand b on b = m.brand")
    List<ModelAndBrandDto> getModelsAndBrands();
}
