package se.allane.demo.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.allane.demo.persistence.Customer;
import se.allane.demo.persistence.LeasingContract;
import se.allane.demo.persistence.Vehicle;
import se.allane.demo.repositories.ContractDto;
import se.allane.demo.repositories.ContractRepository;
import se.allane.demo.repositories.CustomerRepository;
import se.allane.demo.repositories.VehicleRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractEndpointIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private ContractRepository contractRepository;

    @Test
    public void testCreateContract() throws Exception {
        var expectedLeasingContract = new LeasingContract();
        expectedLeasingContract.setId(42L);
        // setup mocks
        when(customerRepository.findById(20L)).thenReturn(Optional.of(new Customer()));
        when(vehicleRepository.findById(10L)).thenReturn(Optional.of(new Vehicle()));
        when(contractRepository.save(any())).thenReturn(expectedLeasingContract);
        when(contractRepository.getContractDetails(42L))
                .thenReturn(Optional.of(ContractDto.builder()
                        .id(42L)
                        .customerId(20L)
                        .vehicleId(10L)
                        .build()));

        var contractDto = ContractDto.builder()
                .version(0)
                .contractNumber(11L)
                .monthlyRate(new BigDecimal(100))
                .vehicleId(10L)
                .customerId(20L)
                .build();

        this.mockMvc.perform(post("/api/contracts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractDto)))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contract.id").value(42L))
                .andExpect(jsonPath("$.contract.vehicleId").value(10L))
                .andExpect(jsonPath("$.vehicle.id").value(10L))
                .andExpect(jsonPath("$.contract.customerId").value(20L))
                .andExpect(jsonPath("$.customer.id").value(20L));
    }
}
