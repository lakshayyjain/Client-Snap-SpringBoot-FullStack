package com.lakshayjain.Customers;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomer();
    Optional<Customer> selectCustomer(Integer id);
    void insertCustomer(Customer customer);
    boolean IsEmailAlreadyExists(String email);
    void deleteCustomer(Integer id);
    boolean IsExistsById(Integer id);
    void updateCustomer(Customer update);
}
