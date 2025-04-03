package com.lakshayjain.Customers;

import com.lakshayjain.Exception.DuplicateRecordFoundException;
import com.lakshayjain.Exception.RecordNotFoundException;
import com.lakshayjain.Exception.RequestValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;
    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
//        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);  // either this or extendwith annotation on class
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        // WHEN
        underTest.getAllCustomers();

        // THEN
        verify(customerDao).selectAllCustomer();
    }

    @Test
    void getCustomerById() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id,
                "lakshay",
                "lakshayjain@gmail.com",
                19,
                Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        // WHEN
        Customer actual = underTest.getCustomerById(id);

        // THEN
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void FailsWhenGetCustomerByIdReturnsEmptyOptional() {
        // GIVEN
        int id = 10;
        when(customerDao.selectCustomer(id)).thenReturn(Optional.empty());

        // WHEN
        // THEN
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(RecordNotFoundException.class)
                        .hasMessage("Customer not found");
    }

    @Test
    void addCustomer() {
        // GIVEN
        String email = "lakshayjain@gmail.com";

        when(customerDao.IsEmailAlreadyExists(email)).thenReturn(false);

        // WHEN
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                "lakshay",
                19,
                email,
                Gender.MALE
        );
        underTest.addCustomer(customerRegistrationRequest);

        // THEN
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerCaptor.capture());

        Customer customer = customerCaptor.getValue();
         assertThat(customer.getId()).isNull();
         assertThat(customer.getName()).isEqualTo(customerRegistrationRequest.name());
         assertThat(customer.getEmail()).isEqualTo(customerRegistrationRequest.email());
         assertThat(customer.getAge()).isEqualTo(customerRegistrationRequest.age());
         assertThat(customer.getGender()).isEqualTo(customerRegistrationRequest.gender());
    }

    @Test
    void FailsToAddCustomerWhenEmailAlreadyExists() {
        // GIVEN
        String email = "lakshayjain@gmail.com";

        when(customerDao.IsEmailAlreadyExists(email)).thenReturn(true);
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                "lakshay",
                19,
                email,
                Gender.MALE
        );
        // WHEN
        assertThatThrownBy(() -> underTest.addCustomer(customerRegistrationRequest))
                .isInstanceOf(DuplicateRecordFoundException.class)
                .hasMessage("Customer already exists");

        // THEN
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        int id = 10;
        when(customerDao.IsExistsById(id)).thenReturn(true);

        // WHEN
        underTest.deleteCustomerById(id);

        // THEN
        verify(customerDao).deleteCustomer(id);
    }

    @Test
    void FailsToDeleteCustomerByIdWhenCustomerDoesNotExist() {
        // GIVEN
        int id = 10;
        when(customerDao.IsExistsById(id)).thenReturn(false);

        // WHEN
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Customer not found");

        // THEN
        verify(customerDao, never()).deleteCustomer(any());
    }

    @Test
    void canUpdateAllCustomerProperties() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                "akshat","akshatjain@gmail.com",19, Gender.FEMALE
        );

        when(customerDao.IsEmailAlreadyExists(customerUpdateRequest.email())).thenReturn(false);

        // WHEN
        underTest.updateCustomer(id, customerUpdateRequest);

        // THEN
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(argumentCaptor.capture());

        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getAge()).isEqualTo(customerUpdateRequest.age());
        assertThat(capturedCustomer.getName()).isEqualTo(customerUpdateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customerUpdateRequest.email());
        assertThat(capturedCustomer.getGender()).isEqualTo(customerUpdateRequest.gender());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                "akshat",null,null, Gender.FEMALE
        );

        // WHEN
        underTest.updateCustomer(id, customerUpdateRequest);

        // THEN
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(argumentCaptor.capture());

        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customerUpdateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(customerUpdateRequest.gender());
    }

    @Test
    void canUpdateOnlyCustomerEmailWhenEmailDoesNotExist() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                null,"akshatjain@gmail.com",null, Gender.FEMALE
        );

        // WHEN
        when(customerDao.IsEmailAlreadyExists(customerUpdateRequest.email())).thenReturn(false);

        underTest.updateCustomer(id, customerUpdateRequest);

        // THEN
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(argumentCaptor.capture());

        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customerUpdateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getGender()).isEqualTo(customerUpdateRequest.gender());
    }

    @Test
    void canUpdateOnlyCustomerEmailWhenEmailExist() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                null,"akshatjain@gmail.com",null, Gender.FEMALE
        );

        when(customerDao.IsEmailAlreadyExists(customerUpdateRequest.email())).thenReturn(true);
        // WHEN
        // THEN
        assertThatThrownBy(() -> underTest.updateCustomer(id, customerUpdateRequest))
                .isInstanceOf(DuplicateRecordFoundException.class)
                .hasMessage("Customer already exists");

        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                null,null,19, Gender.FEMALE
        );

        // WHEN
        underTest.updateCustomer(id, customerUpdateRequest);

        // THEN
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(argumentCaptor.capture());

        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customerUpdateRequest.age());
        assertThat(capturedCustomer.getGender()).isEqualTo(customerUpdateRequest.gender());
    }

    @Test
    void willThrowErrorWhenCustomerHasNoChanges() {
        // GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );
        when(customerDao.selectCustomer(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                customer.getName(),customer.getEmail(),customer.getAge(), customer.getGender()
        );

        // WHEN
        // THEN
        assertThatThrownBy(() ->underTest.updateCustomer(id, customerUpdateRequest))
                .isInstanceOf(RequestValidationException.class)
                        .hasMessage("no data changes found");

        verify(customerDao, never()).updateCustomer(any());
    }
}