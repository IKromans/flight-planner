package io.codelex.flightplanner.service;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.AddFlightRequest;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightRequest;
import io.codelex.flightplanner.repo.FlightInMemoryRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@Service
@ConditionalOnProperty(prefix = "flightapp", name = "appmode", havingValue = "inmemory")
public class FlightInMemoryService implements FlightService {


    private final FlightInMemoryRepository repository;

    public FlightInMemoryService(FlightInMemoryRepository repository) {
        this.repository = repository;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public synchronized Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = flightRequest.toFlight(FlightInMemoryService.generateUUID());
        if (repository.isSameFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (flight.getFrom().isTheSameAirport(flight.getTo()) || (flight.hasIncorrectDates())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        repository.addFlight(flight);
        return flight;
    }

    @Override
    public synchronized void deleteFlight(String id) {
        repository.deleteFlight(id);
    }

    @Override
    public Flight searchFlightById(String id) {
        return repository.searchFlightById(id);
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return repository.searchAirports(phrase);
    }

    @Override
    public PageResult searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getTo().equals(searchFlightRequest.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return repository.searchFlights(searchFlightRequest);
    }

    @Override
    public void clearFlight() {
        this.repository.clearFlight();
    }
}
