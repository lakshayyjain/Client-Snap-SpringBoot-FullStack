package com.lakshayjain.Customers;

import com.lakshayjain.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainer {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomer() {
        // GIVEN
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().emailAddress() + UUID.randomUUID(),
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        // WHEN
        List<Customer> actual = underTest.selectAllCustomer();

        // THEN
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomer() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        Optional<Customer> actual = underTest.selectCustomer(id);

        // THEN
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void WillReturnEmptyWhenSelectCustomerById() {
        // GIVEN
        Integer id = -1;

        // WHEN
        Optional<Customer> actual = underTest.selectCustomer(id);

        // THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );

        // WHEN
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomer()
                .stream()
                        .filter(c -> c.getEmail().equals(email))
                                .map(Customer::getId)
                                        .findFirst().orElseThrow();

        // THEN
        Optional<Customer> actual = underTest.selectCustomer(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void isEmailAlreadyExists() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        // WHEN
        boolean actual = underTest.IsEmailAlreadyExists(email);

        // THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExist() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();

        // WHEN
        boolean actual = underTest.IsEmailAlreadyExists(email);

        // THEN
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomer() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomer()
                .stream()
                        .filter(c -> c.getEmail().equals(email))
                                .map(Customer::getId)
                                        .findFirst().orElseThrow();

        // WHEN
        underTest.deleteCustomer(id);

        // THEN
        Optional<Customer> actual = underTest.selectCustomer(id);
        assertThat(actual).isEmpty();
    }

    @Test
    void isExistsById() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomer()
                .stream()
                    .filter(c -> c.getEmail().equals(email))
                        .map(Customer::getId)
                            .findFirst().orElseThrow();

        // WHEN
        boolean actual = underTest.IsExistsById(id);

        // THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithIdReturnsFalseWhenDoesNotExist() {
        // GIVEN
        Integer id = -1;

        // WHEN
        boolean actual = underTest.IsExistsById(id);

        // THEN
        assertThat(actual).isFalse();
    }

    @Test
    void updateCustomerName() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();

        var newName = "foo";

        // WHEN
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName(newName);
        updatedCustomer.setEmail(email);
        updatedCustomer.setAge(20);
        updatedCustomer.setGender(Gender.MALE);

        underTest.updateCustomer(updatedCustomer);

        // THEN
        Optional<Customer> customer1 = underTest.selectCustomer(id);
        assertThat(customer1).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName); //change
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void updateCustomerEmail() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();

        var newEmail = faker.internet().emailAddress() + UUID.randomUUID();

        // WHEN
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(newEmail);
        updatedCustomer.setAge(20);
        updatedCustomer.setGender(Gender.MALE);

        underTest.updateCustomer(updatedCustomer);

        // THEN
        Optional<Customer> customer1 = underTest.selectCustomer(id);
        assertThat(customer1).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void UpdateCustomerAge() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();

        var newAge = -1;

        // WHEN
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setEmail(email);
        updatedCustomer.setAge(newAge);
        updatedCustomer.setGender(Gender.MALE);

        underTest.updateCustomer(updatedCustomer);

        // THEN
        Optional<Customer> customer1 = underTest.selectCustomer(id);
        assertThat(customer1).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(email);
            assertThat(c.getAge()).isEqualTo(newAge);
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void WillUpdateAllCustomerProperties() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();

        var newName = "foo";
        var newEmail = faker.internet().emailAddress() + UUID.randomUUID();
        var newAge = -1;
        var newGender = Gender.FEMALE;

        // WHEN
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName(newName);
        updatedCustomer.setEmail(newEmail);
        updatedCustomer.setAge(newAge);
        updatedCustomer.setGender(newGender);

        underTest.updateCustomer(updatedCustomer);

        // THEN
        Optional<Customer> actual = underTest.selectCustomer(id);

        assertThat(actual).isPresent().hasValue(updatedCustomer);
    }

    @Test
    void WillNotUpdateWhenNothingToUpdate() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20,
                Gender.MALE
        );
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();
        //no update

        // WHEN
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);

//        underTest.updateCustomer(updatedCustomer);

        // THEN
        Optional<Customer> actual = underTest.selectCustomer(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(email);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }
}