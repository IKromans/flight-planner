package io.codelex.flightplanner.service;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.AddFlightRequest;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.PageResult;
import io.codelex.flightplanner.flights.SearchFlightRequest;
import io.codelex.flightplanner.repo.AirportDBRepository;
import io.codelex.flightplanner.repo.FlightDBRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@ConditionalOnProperty(prefix = "flightapp", name = "appmode", havingValue = "database")
public class FlightDBService implements FlightService {

    private final FlightDBRepository flightDBRepository;
    private final AirportDBRepository airportDBRepository;

    public FlightDBService(FlightDBRepository flightDBRepository, AirportDBRepository airportDBRepository) {
        this.flightDBRepository = flightDBRepository;
        this.airportDBRepository = airportDBRepository;
    }

    @Override
    public Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = flightRequest.toFlight(UUID.randomUUID().toString());

        if (flight.getFrom().isTheSameAirport(flight.getTo()) || (flight.hasIncorrectDates())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (flightDBRepository.findFlight(flight.getFrom(), flight.getTo(), flight.getCarrier(),
                flight.getDepartureTime(), flight.getArrivalTime()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            flight.setFrom(findOrCreate(flight.getFrom()));
            flight.setTo(findOrCreate(flight.getTo()));
            return flightDBRepository.save(flight);
        }
    }

    private Airport findOrCreate(Airport airport) {
        Optional<Airport> existingAirport = airportDBRepository.findById(airport.getAirport());
        return existingAirport.orElseGet(() -> airportDBRepository.save(airport));
    }

    @Override
    public void deleteFlight(String id) {
        flightDBRepository.findById(id).ifPresent(flightDBRepository::delete);
        if (flightDBRepository.existsById(id)) {
            flightDBRepository.deleteById(id);
        }
    }

    @Override
    public Flight searchFlightById(String id) {
        return flightDBRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return airportDBRepository.searchAirportByPhrase(phrase.trim().toLowerCase());
    }

    @Override
    public PageResult searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getTo().equals(searchFlightRequest.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Flight> flights = flightDBRepository.searchFlights(
                searchFlightRequest.getFrom(),
                searchFlightRequest.getTo(),
                searchFlightRequest.getDepartureDate().atStartOfDay(),
                searchFlightRequest.getDepartureDate().plusDays(1).atStartOfDay());

        return new PageResult(0, flights.size(), flights);
    }

    @Override
    public void clearFlight() {
        flightDBRepository.deleteAll();
    }
}
