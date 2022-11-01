package se.allane.demo.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.allane.demo.persistence.Vehicle;
import se.allane.demo.repositories.VehicleRepository;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleEndpoint {

    private final VehicleRepository vehicleRepository;

    @GetMapping
    @ResponseBody
    Page<VehicleDto> getVehicles(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size) {
        var pageable = PageRequest.of(page, size);
        var vehicles = this.vehicleRepository.findAll(pageable);
        var vehicleDtos = vehicles.getContent().stream().map(this::vehicleToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(vehicleDtos, pageable, vehicles.getTotalElements());
    }


    @GetMapping("/{id}")
    @ResponseBody
    VehicleDto getVehicleDetails(@PathVariable("id") @NotNull Long id) {
        var vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return this.vehicleToDto(vehicle);
    }

    private VehicleDto vehicleToDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .id(vehicle.getId())
                .brand(vehicle.getModel().getBrand().getName())
                .model(vehicle.getModel().getName())
                .year(vehicle.getModel().getYear().getValue())
                .vin(vehicle.getVin())
                .build();
    }
}
