package com.handpick.thehandpickapp.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handpick.thehandpickapp.models.Price;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new Converter<String, Price>() {
            public Price convert(MappingContext<String, Price> context) {
                return convertJsonToPrice(context.getSource());
            }
        });

        modelMapper.addConverter(new Converter<Price, String>() {
            public String convert(MappingContext<Price, String> context) {
                return convertPriceToJson(context.getSource());
            }
        });

        return modelMapper;
    }
    
    // Convert JSON string to Address object
    private Price convertJsonToPrice(String json) {
        try {
            if (json == null)
                return null;
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Price.class); // Deserialize JSON to Price object
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to Price: " + e.getMessage(), e);
        }
    }

    // Convert Price object to JSON string
    private String convertPriceToJson(Price price) {
        if (price == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(price);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Price to JSON: " + e.getMessage(), e);
        }
    }    
}
