package com.market.category;

import com.market.category.jpa.repository.AttributeGroupRepository;
import com.market.category.jpa.repository.AttributeRepository;
import com.market.category.jpa.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/* FIXME
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@Testcontainers*/
public class CategoryServiceApplicationTests {

    /*@Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
            .withReuse(true)
            .withDatabaseName("category_service_db")
            .withUsername("category_service")
            .withPassword("category_service");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    protected AttributeGroupRepository attributeGroupRepository;

    @Autowired
    protected AttributeRepository attributeRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    void cleanUp() {
        attributeGroupRepository.deleteAll();
        attributeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void testAppStarts() throws Exception {
        String health = mockMvc.perform(get("/actuator/health"))
                               .andExpect(status().isOk())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        log.info("actuator health: {}", health);
    }*/
}
