package io.codelex.flightplanner.repo;

import io.codelex.flightplanner.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDBRepository extends JpaRepository<Airport, String> {

    @Query(value = "SELECT a FROM Airport a WHERE LOWER(a.city) LIKE CONCAT('%', :searchPhrase,'%') "
            + "OR LOWER(a.country) LIKE CONCAT('%', :searchPhrase,'%')"
            + "OR LOWER(a.airport) LIKE CONCAT('%', :searchPhrase,'%')")
    List<Airport> searchAirportByPhrase(@Param("searchPhrase") String searchPhrase);
}
