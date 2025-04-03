package com.lakshayjain.Customers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // GIVEN
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(20);
        when(resultSet.getString("name")).thenReturn("lakshay");
        when(resultSet.getString("email")).thenReturn("lakshayjain@gmail.com");
        when(resultSet.getString("gender")).thenReturn("MALE");

        // WHEN
        Customer actualCustomer = customerRowMapper.mapRow(resultSet, 1);

        // THEN
        Customer expected = new Customer(
                1, "lakshay", "lakshayjain@gmail.com", 20, Gender.MALE
        );

        assertThat(actualCustomer).isEqualTo(expected);
    }
}