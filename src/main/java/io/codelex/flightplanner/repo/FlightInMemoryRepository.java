package io.codelex.flightplanner.repo;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightInMemoryRepository {

    private final List<Flight> flightList = new ArrayList<>();

    public void addFlight(Flight flight) {
        flightList.add(flight);
    }

    public void deleteFlight(String id) {
        flightList.removeIf(flight -> flight.getId().equals(id));
    }

    public Flight searchFlightById(String id) {
        return flightList.stream().filter(flight -> flight.getId().equals(id))
                .findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Airport> searchAirports(String phrase) {
        return flightList.stream().map(Flight::getFrom)
                .filter(airport -> airport.isContainingPhrase(phrase)).toList();
    }

    public PageResult searchFlights(SearchFlightRequest searchFlightRequest) {
        return new PageResult(flightList.stream().filter(searchFlightRequest::isSearchedFlight).toList());
    }

    public void clearFlight() {
        flightList.clear();
    }

    public boolean isSameFlight(Flight flight) {
        return flightList.stream().anyMatch(flight::isTheSameFlight);
    }
}
