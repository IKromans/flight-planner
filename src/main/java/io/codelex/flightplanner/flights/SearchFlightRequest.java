package io.codelex.flightplanner.flights;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class SearchFlightRequest {
    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    private LocalDate departureDate;

    public SearchFlightRequest(String from, String to, LocalDate departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public boolean isSearchedFlight(Flight flight) {
        return flight.getFrom().getAirport().equals(from) &&
                flight.getTo().getAirport().equals(to) &&
                flight.getDepartureTime().toLocalDate().isEqual(departureDate);
    }
}
