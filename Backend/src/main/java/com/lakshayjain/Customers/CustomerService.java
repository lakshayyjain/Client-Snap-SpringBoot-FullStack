package com.lakshayjain.Customers;

import com.lakshayjain.Exception.DuplicateRecordFoundException;
import com.lakshayjain.Exception.RecordNotFoundException;
import com.lakshayjain.Exception.RequestValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao,
                           PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomer();
    }

    public Customer getCustomerById(Integer id){
        return customerDao.selectCustomer(id).orElseThrow(
                () -> new RecordNotFoundException("Customer not found")
        );
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check email
        //if exists
        if(customerDao.IsEmailAlreadyExists(customerRegistrationRequest.email())){
            throw new DuplicateRecordFoundException("Customer already exists");
        }else {
            //add
            Customer customer = new Customer(
                    customerRegistrationRequest.name(),
                    customerRegistrationRequest.email() ,
                    passwordEncoder.encode(customerRegistrationRequest.password()),
                    customerRegistrationRequest.age(),
                    customerRegistrationRequest.gender()
            );
            customerDao.insertCustomer(customer);
        }
    }

    public void deleteCustomerById(Integer id){
        // check
        // if exists
        if(customerDao.IsExistsById(id)){
            customerDao.deleteCustomer(id);
        } else{
            throw new RecordNotFoundException("Customer not found");
        }
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        //  ToDo: for JPA use .getReferenceById(customerID)  as it does doe
        Customer customer = getCustomerById(customerId);
//        String age = Integer.toString(updateRequest.age());
        boolean changes = false;

        if(updateRequest.name() != null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes = true;
        }

//        if (!age.equals(Integer.toString(customer.getAge()) )){
        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())){
            if (customerDao.IsEmailAlreadyExists(updateRequest.email())){
                throw new DuplicateRecordFoundException("Customer already exists");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.gender() != null && !updateRequest.gender().equals(customer.getGender())) {
            customer.setGender(updateRequest.gender());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }
         customerDao.updateCustomer(customer);
    }
}
