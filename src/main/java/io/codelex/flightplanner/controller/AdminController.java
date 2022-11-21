package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.flights.AddFlightRequest;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.service.FlightInMemoryService;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    public FlightService service;

    public AdminController(FlightService service) {
        this.service = service;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest flightRequest) {
        return service.addFlight(flightRequest);
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFLight(@PathVariable("id") String id) {
        service.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable("id") String id) {
        return service.searchFlightById(id);
    }
}