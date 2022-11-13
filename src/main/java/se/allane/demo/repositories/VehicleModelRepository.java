package se.allane.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.allane.demo.persistence.VehicleModel;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
}
