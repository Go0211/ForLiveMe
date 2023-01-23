package com.dontleaveme.forliveme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.*;

class ForLiveMeApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(ForLiveMeApplicationTest.class, args);
    }
}