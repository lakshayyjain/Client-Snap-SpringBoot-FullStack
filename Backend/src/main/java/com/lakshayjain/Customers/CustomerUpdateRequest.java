package com.lakshayjain.Customers;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
