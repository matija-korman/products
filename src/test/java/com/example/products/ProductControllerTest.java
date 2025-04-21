package com.example.products;

import com.example.products.model.PagedResponse;
import com.example.products.model.ProductResponse;
import com.example.products.service.ExchangeRateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExchangeRateService exchangeRateService;

    @Test
    void shouldCreateProduct() throws Exception {
        Mockito.when(exchangeRateService.getUsdExchangeRate()).thenReturn(new BigDecimal("1.10"));

        var newProduct = Map.of(
                "code", "TESTPROD0000001",
                "name", "Test Product",
                "priceEur", 50.0,
                "description", "Test product description"
        );

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test product description"))
                .andExpect(jsonPath("$.priceEur").value(50.0))
                .andExpect(jsonPath("$.priceUsd").value(55.0));
    }

    @Test
    void shouldReturnTopThreePopularProducts() throws Exception {
        mockMvc.perform(get("/products/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Mega Kit"))
                .andExpect(jsonPath("$[0].averageRating").value(5))
                .andExpect(jsonPath("$[1].name").value("Super Widget"))
                .andExpect(jsonPath("$[1].averageRating").value(4))
                .andExpect(jsonPath("$[2].name").value("Gadget Max"))
                .andExpect(jsonPath("$[2].averageRating").value(3));
    }

    @Test
    void shouldFilterProductsByNameAndCode() throws Exception {
        var result = mockMvc.perform(get("/products")
                        .param("name", "Widget")
                        .param("code", "PROD"))
                .andExpect(status().isOk())
                .andReturn();


        PagedResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(response).isNotNull();
        assertThat(response.content()).isNotEmpty();
        assertThat(response.content().size()).isEqualTo(1);
        assertThat(response.content().getFirst().name()).isEqualTo("Super Widget");
        assertThat(response.content().getFirst().code()).isEqualTo("PROD00000000001");

    }

}
