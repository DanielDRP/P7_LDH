package es.ull.passengers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import es.ull.flights.Flight;

class PassengerTest {

    private Passenger passenger;
    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("P001", "John Doe", "US");
        flight1 = new Flight("AB123", 2);
        flight2 = new Flight("XY987", 3);
    }

    @Test
    void testValidPassengerCreation() {
        assertDoesNotThrow(() -> new Passenger("P002", "Alice", "FR"));
    }

    @Test
    void testInvalidCountryCodeThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Passenger("P003", "Charlie", "XX"));
        assertEquals("Invalid country code", exception.getMessage());
    }

    @Test
    void testCreatePassengerWithEmptyName() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Passenger("P001", "", "US"));
        assertEquals("Name cannot be empty", exception.getMessage()); // Ajusta si es necesario
    }

    @Test
    void testCreatePassengerWithNullIdentifier() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Passenger(null, "John", "US"));
        assertEquals("Identifier cannot be null", exception.getMessage()); // Ajusta si es necesario
    }

    @Test
    void testJoinFlight() {
        passenger.joinFlight(flight1);
        assertEquals(flight1, passenger.getFlight());
        assertEquals(1, flight1.getNumberOfPassengers());
    }

    @Test
    void testPassengerCannotJoinMultipleFlights() {
        passenger.joinFlight(flight1);
        passenger.joinFlight(flight2);

        assertEquals(flight2, passenger.getFlight());
        assertEquals(1, flight2.getNumberOfPassengers());
        assertEquals(0, flight1.getNumberOfPassengers());
    }

    @Test
    void testPassengerLeavesFlight() {
        passenger.joinFlight(flight1);
        passenger.joinFlight(null);
        assertNull(passenger.getFlight());
        assertEquals(0, flight1.getNumberOfPassengers());
    }

    @Test
    void testPassengerJoinsNewFlightWithoutPrevious() {
        passenger.joinFlight(flight1);
        assertEquals(flight1, passenger.getFlight());
        assertEquals(1, flight1.getNumberOfPassengers());
    }

    @Test
    void testSwitchFlights() {
        passenger.joinFlight(flight1);
        passenger.joinFlight(flight2);

        assertEquals(flight2, passenger.getFlight());
        assertEquals(1, flight2.getNumberOfPassengers());
        assertEquals(0, flight1.getNumberOfPassengers());
    }
}
