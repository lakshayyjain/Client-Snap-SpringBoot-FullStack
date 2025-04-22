package com.lakshayjain.Customers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomer() {
        // WHEN
        underTest.selectAllCustomer();

        // THEN
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomer() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.selectCustomer(id);

        // THEN
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // GIVEN
        Customer customer = new Customer(
             "lakshay",
             "lakshay@gmail.com",
                "password", 20,
                Gender.MALE
        );

        // WHEN
        underTest.insertCustomer(customer);

        // THEN
        verify(customerRepository).save(customer);
    }

    @Test
    void isEmailAlreadyExists() {
        // GIVEN
        String email = "lakshayjain@gmail.com";

        // WHEN
        underTest.IsEmailAlreadyExists(email);

        // THEN
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void deleteCustomer() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.deleteCustomer(id);

        // THEN
        verify(customerRepository).deleteById(id);
    }

    @Test
    void isExistsById() {
        // GIVEN
        int id = 1;

        // WHEN
        underTest.IsExistsById(id);

        // THEN
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void updateCustomer() {
        // GIVEN
        Customer customer = new Customer(
                "lakshay",
                "lakshay@gmail.com",
                "password", 20,
                Gender.MALE
        );

        // WHEN
        underTest.updateCustomer(customer);

        // THEN
        verify(customerRepository).save(customer);
    }
}