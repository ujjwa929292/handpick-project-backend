package com.handpick.thehandpickapp.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handpick.thehandpickapp.constants.UrlConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(UrlConstants.HEALTH_API_BASE_PATH)
@RestController
@Tag(name = "Health", description = "Check if api is up and running")
@Validated
public class HealthController {

    @GetMapping("/")
    public String getHealth() {
        return "Healthy";
    }
}
