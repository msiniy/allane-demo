package se.allane.demo.endpoints;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import se.allane.demo.persistence.Customer;
import se.allane.demo.repositories.CustomerDto;
import se.allane.demo.repositories.CustomerRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Transactional
public class CustomerEndpoint {
    private final CustomerRepository customerRepository;

    @GetMapping
    @ResponseBody
    Page<CustomerDto> getCustomers(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        var pageable = PageRequest.of(page, size,  Sort.Direction.ASC, "id");
        return this.customerRepository.findCustomers(pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    CustomerDto getCustomer(@PathVariable("id") @NotNull Long id) {
        return this.customerRepository.getCustomerDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseBody
    CustomerDto createCustomer(@RequestBody @NotNull @Valid CustomerDto customerDto) {
        var customer = this.mapFromDto(customerDto, new Customer());
        customer = this.customerRepository.save(customer);
        return this.getCustomer(customer.getId());
    }

    @PutMapping("/{id}")
    @ResponseBody
    CustomerDto updateCustomer(@PathVariable("id") @NotNull Long id,
                               @RequestBody @NotNull @Valid CustomerDto customerDto) {
        var customer = this.customerRepository.findByIdAndVersion(id, customerDto.version())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
        this.customerRepository.save(mapFromDto(customerDto, customer));
        return this.getCustomer(id);
    }

    private Customer mapFromDto(CustomerDto dto, Customer customer) {
        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setBirthDate(dto.birthDate());
        return customer;
    }
}
