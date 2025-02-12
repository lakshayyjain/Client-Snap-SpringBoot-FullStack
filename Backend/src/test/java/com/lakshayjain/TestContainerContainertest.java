package com.lakshayjain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestContainerContainertest extends AbstractTestContainer {
    @Test
    void canStartPostgresdb() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        System.out.println("postgreSQLContainer is running");
    }
}