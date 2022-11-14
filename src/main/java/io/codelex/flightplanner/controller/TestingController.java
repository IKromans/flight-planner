package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.service.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {

    public FlightService service;

    public TestingController(FlightService service) {
        this.service = service;
    }

    @PostMapping("/clear")
    public void clearFlight() {
        this.service.clearFlight();
    }
}
