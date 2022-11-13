package se.allane.demo.endpoints;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.allane.demo.repositories.ContractDto;
import se.allane.demo.repositories.ContractRepository;
import se.allane.demo.repositories.CustomerDto;
import se.allane.demo.repositories.VehicleDto;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractEndpoint {

    private final ContractRepository contractRepository;

    @GetMapping
    @ResponseBody
    Page<ContractOverviewDto> getContractOverview(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        var contracts = contractRepository.findContracts(PageRequest.of(page, size));
        return new PageImpl<>(contracts.stream()
                .map(this::mapToContractOverviewDto).collect(Collectors.toList()),
                contracts.getPageable(), contracts.getTotalElements());
    }


    private ContractOverviewDto mapToContractOverviewDto(ContractDto c) {
        return ContractOverviewDto.builder()
                .contract(ContractDto.builder()
                        .id(c.id())
                        .contractNumber(c.contractNumber())
                        .monthlyRate(c.monthlyRate())
                        .build())
                .customer(CustomerDto.builder()
                        .firstName(c.customerFirstName())
                        .lastName(c.customerLastName())
                        .build())
                .vehicle(VehicleDto.builder()
                        .brandName(c.brandName())
                        .modelName(c.modelName())
                        .modelYear(c.modelYear())
                        .vin(c.vehicleVin())
                        .price(c.vehiclePrice())
                        .build())
                .build();
    }

    @Data
    @Builder
    private static class ContractOverviewDto {

        private ContractDto contract;

        private CustomerDto customer;

        private VehicleDto vehicle;
    }
}
