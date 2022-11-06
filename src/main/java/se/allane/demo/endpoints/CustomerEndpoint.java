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
import se.allane.demo.repositories.CustomerDto;
import se.allane.demo.repositories.CustomerRepository;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerEndpoint {
    private final CustomerRepository customerRepository;

    @GetMapping
    @ResponseBody
    Page<CustomerDto> getCustomers(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        var pageable = PageRequest.of(page, size);
        return this.customerRepository.findCustomers(pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    CustomerDto getCustomer(@PathVariable("id") @NotNull Long id) {
        return this.customerRepository.getCustomerDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
