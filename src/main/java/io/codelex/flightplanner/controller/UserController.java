package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightRequest;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    public FlightService service;

    public UserController(FlightService service) {
        this.service = service;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search) {
        return service.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult searchFlights(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {
        return service.searchFlights(searchFlightRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight searchFlightById(@PathVariable String id) {
        return service.searchFlightById(id);

    }
}

