package io.codelex.flightplanner.service;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightRequest;

import java.util.List;

public interface FlightService {

    void addFlight(Flight flight);

    void deleteFlight(String id);

    Flight searchFlightById(String id);

    List<Airport> searchAirports(String phrase);

    PageResult searchFlights(SearchFlightRequest searchFlightRequest);

    void clearFlight();
}
