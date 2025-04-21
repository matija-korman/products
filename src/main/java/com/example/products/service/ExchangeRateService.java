package com.example.products.service;

import com.example.products.controller.exception.ExchangeRateUnavailableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {

    private static final String HNB_API_URL = "https://api.hnb.hr/tecajn-eur/v3";
    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getUsdExchangeRate() {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(HNB_API_URL)
                    .queryParam("valuta", "USD")
                    .toUriString();

            List<Map<String, String>> response = restTemplate.getForObject(url, List.class);

            if (response == null || response.isEmpty()) {
                throw new ExchangeRateUnavailableException("HNB API returned empty response.");
            }

            String srednjiTecajStr = response.get(0).get("srednji_tecaj").replace(",", ".");
            return new BigDecimal(srednjiTecajStr);

        } catch (Exception e) {
            throw new ExchangeRateUnavailableException("Failed to fetch USD exchange rate from HNB.", e);
        }
    }
}

