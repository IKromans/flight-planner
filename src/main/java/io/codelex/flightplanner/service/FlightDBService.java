package io.codelex.flightplanner.service;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flightapp", name = "appmode", havingValue = "database")
public class FlightDBService implements FlightService {

    @Override
    public void addFlight(Flight flight) {
        throw new RuntimeException("DB mode not implemented!");
    }

    @Override
    public void deleteFlight(String id) {
        throw new RuntimeException("DB mode not implemented!");
    }

    @Override
    public Flight searchFlightById(String id) {
        return null;
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return null;
    }

    @Override
    public PageResult searchFlights(SearchFlightRequest searchFlightRequest) {
        return null;
    }

    @Override
    public void clearFlight() {
        throw new RuntimeException("DB mode not implemented!");
    }
}
