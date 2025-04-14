package com.lakshayjain.Customers;

import com.lakshayjain.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.jwtUtil = new JWTUtil();
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
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
        String token = jwtUtil.issueToken(request.email(),"USER_ROLE");
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).build();
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