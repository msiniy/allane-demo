package se.allane.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.allane.demo.persistence.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT new se.allane.demo.repositories.VehicleDto(v.id, v.model.brand.name, v.model.name, v.model.year) " +
            "FROM Vehicle v ")
    Page<VehicleDto> getVehiclePage(Pageable pageRequest);
}
