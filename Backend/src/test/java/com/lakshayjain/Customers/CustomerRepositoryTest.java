package com.lakshayjain.Customers;

import com.lakshayjain.AbstractTestContainer;
import com.lakshayjain.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class CustomerRepositoryTest extends AbstractTestContainer {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                "password", 20,
                Gender.MALE
        );
        underTest.save(customer);

        // WHEN
         var actual = underTest.existsCustomerByEmail(email);

        // THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailDoesNotExist() {
        // GIVEN
        String email = faker.internet().emailAddress() + UUID.randomUUID();

        // WHEN
        var actual = underTest.existsCustomerByEmail(email);

        // THEN
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerById() {
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                "password", 20,
                Gender.MALE
        );
        underTest.save(customer);
        Integer id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();

        // WHEN
        var actual = underTest.existsCustomerById(id);

        // THEN
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByIdFailsWhenIdDoesNotExist() {
        // GIVEN
        Integer id = -1;

        // WHEN
        var actual = underTest.existsCustomerById(id);

        // THEN
        assertThat(actual).isFalse();
    }
}