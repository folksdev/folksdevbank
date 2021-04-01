
package com.folksdevbank.controller;

import com.folksdevbank.dto.CreateCustomerRequest;
import com.folksdevbank.dto.CustomerDto;
import com.folksdevbank.dto.UpdateCustomerRequest;
import com.folksdevbank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String id){
        return ResponseEntity.ok(customerService.getCustomerDtoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable String id,
                                                      @RequestBody UpdateCustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
    }
}
