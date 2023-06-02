package com.abn.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource(value="classpath:application-integration-tests.properties")
class RecipesManagerApplicationTests {

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String databaseDialect;

    @Test
    void contextLoads() {
        assertFalse(databaseDialect.isEmpty());
    }

}
