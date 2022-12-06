package io.codelex.flightplanner.flights;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.airport.Airport;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Flight {

    @Id
    @Column(name = "flight_id")
    private String id;

    @JoinColumn(name = "from_id")
    @ManyToOne
    @Valid
    @NotNull
    private Airport from;

    @JoinColumn(name = "to_id")
    @ManyToOne
    @Valid
    @NotNull
    private Airport to;

    @NotBlank
    @Column(name = "carrier")
    private String carrier;

    @Column(name = "departure_time")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "arrival_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    public Flight(String id, Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isTheSameFlight(Flight flight) {
        return from.equals(flight.from) &&
                to.equals(flight.to) &&
                carrier.equals(flight.carrier) &&
                departureTime.equals(flight.departureTime) &&
                arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id.equals(flight.id) && from.equals(flight.from) && to.equals(flight.to) && carrier.equals(flight.carrier) && departureTime.equals(flight.departureTime) && arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }

    public boolean hasIncorrectDates() {
        return arrivalTime.isBefore(departureTime) || arrivalTime.isEqual(departureTime);
    }
}
