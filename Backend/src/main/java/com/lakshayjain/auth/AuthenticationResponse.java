package com.lakshayjain.auth;

import com.lakshayjain.Customers.CustomerDTO;

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO
) {
}
