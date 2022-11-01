package se.allane.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.allane.demo.persistence.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
