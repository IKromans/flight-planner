package io.codelex.flightplanner.repo;

import io.codelex.flightplanner.airport.Airport;
import io.codelex.flightplanner.flights.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FlightDBRepository extends JpaRepository<Flight, String> {

    @Query("SELECT f FROM Flight f WHERE f.from = :from AND f.to = :to AND f.carrier = :carrier AND f.departureTime = :departureTime AND f.arrivalTime = :arrivalTime")
    Optional<Flight> findFlight(@Param("from") Airport from, @Param("to") Airport to,
                                @Param("carrier") String carrier, @Param("departureTime") LocalDateTime departureTime,
                                @Param("arrivalTime") LocalDateTime arrivalTime);
}
