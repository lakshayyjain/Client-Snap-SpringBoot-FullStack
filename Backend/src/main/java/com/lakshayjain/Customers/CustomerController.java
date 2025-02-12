package com.lakshayjain.Customers;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerID}")
    public Customer getCustomer(
            @PathVariable("customerID") Integer customerID
    ){
        return customerService.getCustomerById(customerID);
    }

    @PostMapping
    public void registerCustomer(
            @RequestBody CustomerRegistrationRequest request){
    customerService.addCustomer(request);
    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer (
            @PathVariable("customerID") Integer customerID
    ){
        customerService.deleteCustomerById(customerID);
    }

    @PutMapping("{customerID}")
    public void updateCustomer(
            @PathVariable("customerID") Integer customerId,
            @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId,updateRequest);
    }

}