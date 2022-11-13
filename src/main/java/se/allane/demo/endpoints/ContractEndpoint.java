package se.allane.demo.endpoints;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import se.allane.demo.persistence.LeasingContract;
import se.allane.demo.repositories.ContractDto;
import se.allane.demo.repositories.ContractRepository;
import se.allane.demo.repositories.CustomerDto;
import se.allane.demo.repositories.CustomerRepository;
import se.allane.demo.repositories.VehicleDto;
import se.allane.demo.repositories.VehicleRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
@Transactional
public class ContractEndpoint {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @GetMapping
    @ResponseBody
    Page<ContractOverviewDto> getContractOverview(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        var contracts = contractRepository.findContracts(
                PageRequest.of(page, size, Sort.Direction.ASC, "id"));
        return new PageImpl<>(contracts.stream()
                    .map(this::mapToContractOverviewDto)
                    .collect(Collectors.toList()),
                contracts.getPageable(), contracts.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseBody
    ContractOverviewDto getContractDetails(@PathVariable("id") @NotNull Long id) {
        return this.contractRepository.getContractDetails(id)
                .map(this::mapToContractOverviewDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseBody
    ContractOverviewDto updateContract(@PathVariable("id") @NotNull Long id,
                               @RequestBody @NotNull @Valid ContractDto contractDto) {
        var contract = this.contractRepository.findByIdAndVersion(id, contractDto.version())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
        // The design decision was not to allow to change or delete contract customer or vehicle.
        // That the thing that shall be asked or confirmed by PMs or BAs.
        contract = this.contractRepository.save(mapFromDto(contractDto, contract));
        return this.getContractDetails(contract.getId());
    }

    @PostMapping
    @ResponseBody
    ContractOverviewDto createContract(@RequestBody @NotNull @Valid ContractDto contractDto) {
        // make sure the specified customer exists
        var customer = this.customerRepository.findById(contractDto.customerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer"));
        var vehicle = this.vehicleRepository.findById(contractDto.vehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid vehicle"));

        // map and save contract
        var contract = mapFromDto(contractDto, new LeasingContract());
        contract.setCustomer(customer);
        contract = this.contractRepository.save(contract);
        vehicle.setContract(contract);
        this.vehicleRepository.save(vehicle);
        contract = this.contractRepository.save(contract);
        return this.getContractDetails(contract.getId());
    }


    private ContractOverviewDto mapToContractOverviewDto(ContractDto c) {
        return ContractOverviewDto.builder()
                .contract(ContractDto.builder()
                        .id(c.id())
                        .version(c.version())
                        .contractNumber(c.contractNumber())
                        .monthlyRate(c.monthlyRate())
                        .vehicleId(c.vehicleId())
                        .customerId(c.customerId())
                        .build())
                .customer(CustomerDto.builder()
                        .id(c.customerId())
                        .firstName(c.customerFirstName())
                        .lastName(c.customerLastName())
                        .build())
                .vehicle(VehicleDto.builder()
                        .id(c.vehicleId())
                        .brandName(c.brandName())
                        .modelName(c.modelName())
                        .modelYear(c.modelYear())
                        .vin(c.vehicleVin())
                        .price(c.vehiclePrice())
                        .build())
                .build();
    }

    private LeasingContract mapFromDto(ContractDto dto, LeasingContract contract) {
        contract.setContractNumber(dto.contractNumber());
        contract.setMonthlyRate(dto.monthlyRate());
        return contract;
    }

    @Data
    @Builder
    static class ContractOverviewDto {

        private ContractDto contract;

        private CustomerDto customer;

        private VehicleDto vehicle;
    }
}
