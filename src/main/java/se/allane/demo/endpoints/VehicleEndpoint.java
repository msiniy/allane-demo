package se.allane.demo.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.allane.demo.repositories.VehicleDetailsDto;
import se.allane.demo.repositories.VehicleDto;
import se.allane.demo.repositories.VehicleRepository;

import javax.validation.constraints.NotNull;

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
        return this.vehicleRepository.getVehiclePage(pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    VehicleDetailsDto getVehicleDetails(@PathVariable("id") @NotNull Long id) {
        return this.vehicleRepository.getVehicleDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
