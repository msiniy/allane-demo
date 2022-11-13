package se.allane.demo.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.allane.demo.persistence.Vehicle;
import se.allane.demo.repositories.ModelAndBrandDto;
import se.allane.demo.repositories.VehicleDetailsDto;
import se.allane.demo.repositories.VehicleDto;
import se.allane.demo.repositories.VehicleModelRepository;
import se.allane.demo.repositories.VehicleRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Transactional
public class VehicleEndpoint {

    private final VehicleRepository vehicleRepository;

    private final VehicleModelRepository modelRepository;

    @GetMapping
    @ResponseBody
    Page<VehicleDto> getVehicles(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size) {
        var pageable = PageRequest.of(page, size);
        return this.vehicleRepository.findVehicles(pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    VehicleDetailsDto getVehicleDetails(@PathVariable("id") @NotNull Long id) {
        return this.vehicleRepository.getVehicleDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/modelsAndBrands")
    @ResponseBody
    List<ModelAndBrandDto> getModelsAndBrands() {
        return this.vehicleRepository.getModelsAndBrands();
    }

    @PostMapping
    @ResponseBody
    VehicleDetailsDto createVehicle(@RequestBody @NotNull @Valid VehicleDetailsDto vehicleDto) {
        var vehicle = this.mapFromDto(vehicleDto, new Vehicle());
        var model = this.modelRepository.findById(vehicleDto.modelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Specified model is incorrect"));
        vehicle.setModel(model);
        vehicle = this.vehicleRepository.save(vehicle);
        return this.getVehicleDetails(vehicle.getId());
    }

    @PutMapping("/{id}")
    @ResponseBody
    VehicleDetailsDto updateVehicle(@PathVariable("id") @NotNull Long id,
                                    @RequestBody @NotNull @Valid VehicleDetailsDto vehicleDto) {
        var vehicle = this.vehicleRepository.findByIdAndVersion(id, vehicleDto.version())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
        this.vehicleRepository.save(mapFromDto(vehicleDto, vehicle));
        return this.getVehicleDetails(id);
    }

    private Vehicle mapFromDto(VehicleDetailsDto dto, Vehicle vehicle) {
        vehicle.setVin(dto.vin());
        vehicle.setPrice(dto.price());
        return vehicle;
    }
}
