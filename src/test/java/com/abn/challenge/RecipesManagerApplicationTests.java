package com.abn.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
class RecipesManagerApplicationTests {

    @Test
    void contextLoads() {
    }

}
