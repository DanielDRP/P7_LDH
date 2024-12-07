package es.ull.flights;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import es.ull.passengers.Passenger;

class FlightTest {

    private Flight flight;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        flight = new Flight("AB123", 2);
        passenger = new Passenger("P001", "John Doe", "US");
    }

    @Test
    void testValidFlightCreation() {
        assertDoesNotThrow(() -> new Flight("XY987", 100));
    }

    @Test
    void testFlightNumberTooShort() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Flight("A123", 100));
        assertEquals("Invalid flight number", exception.getMessage());
    }

    @Test
    void testFlightNumberTooLong() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Flight("ABC12345", 100));
        assertEquals("Invalid flight number", exception.getMessage());
    }

    @Test
    void testAddPassenger() {
        assertTrue(flight.addPassenger(passenger));
        assertEquals(1, flight.getNumberOfPassengers());
    }

    @Test
    void testAddPassengerWhenFlightIsFullThrowsException() {
        flight.addPassenger(new Passenger("P002", "Alice", "FR"));
        flight.addPassenger(new Passenger("P003", "Bob", "DE"));
        Exception exception = assertThrows(RuntimeException.class, () -> flight.addPassenger(passenger));
        assertEquals("Not enough seats for flight AB123", exception.getMessage());
    }

    @Test
    void testRemovePassenger() {
        flight.addPassenger(passenger);
        assertTrue(flight.removePassenger(passenger));
        assertEquals(0, flight.getNumberOfPassengers());
    }

    @Test
    void testRemovePassengerNotInFlight() {
        Passenger newPassenger = new Passenger("P002", "Alice", "FR");
        assertFalse(flight.removePassenger(newPassenger));
    }

    @Test
    void testAddSamePassengerTwice() {
        assertTrue(flight.addPassenger(passenger));
        assertFalse(flight.addPassenger(passenger));
        assertEquals(1, flight.getNumberOfPassengers());
    }

    @Test
    void testInitialStateOfFlight() {
        assertEquals(0, flight.getNumberOfPassengers());
    }

    @Test
    void testFlightWithSingleSeat() {
        Flight smallFlight = new Flight("CD001", 1);
        assertTrue(smallFlight.addPassenger(passenger));

        Passenger anotherPassenger = new Passenger("P003", "Jane", "GB");
        Exception exception = assertThrows(RuntimeException.class, () -> smallFlight.addPassenger(anotherPassenger));
        assertEquals("Not enough seats for flight CD001", exception.getMessage());
    }
}
