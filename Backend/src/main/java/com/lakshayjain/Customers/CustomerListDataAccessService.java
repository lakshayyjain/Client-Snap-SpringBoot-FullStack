package com.lakshayjain.Customers;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    private static final List<Customer> customers;

    static {

        customers = new ArrayList<>();
        Customer lakshay = new Customer(
                1,
                "Lakshay",
                "lakshayjain312@gmail.com",
                "password", 19,
                Gender.MALE
        );
        customers.add(lakshay);

        Customer akshat = new Customer(
                2,
                "Akshat Jain",
                "akshat123@gmail.com",
                "password", 18,
                Gender.MALE
        );
        customers.add(akshat);
    }

    @Override
    public List<Customer> selectAllCustomer() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomer(Integer id) {
        return customers.stream().filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean IsEmailAlreadyExists(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomer(Integer id) {
        customers.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public boolean IsExistsById(Integer id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getUsername().equals(email))
                .findFirst();
    }
}
