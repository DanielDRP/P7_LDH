package es.ull.passengers;

import es.ull.flights.Flight;
import es.ull.passengers.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    private Passenger passenger;
    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("P001", "John Doe", "US");
        flight1 = new Flight("AB123", 2); // Vuelo con 2 asientos
        flight2 = new Flight("XY987", 3); // Vuelo con 3 asientos
    }

    @Test
    void testValidPassengerCreation() {
        assertDoesNotThrow(() -> new Passenger("P002", "Alice", "GB"));
    }

    @Test
    void testJoinFlightNoPreviousFlight() {
        // El pasajero no tiene un vuelo anterior, se une al vuelo1
        passenger.joinFlight(flight1);
        assertEquals(flight1, passenger.getFlight());
        assertEquals(1, flight1.getNumberOfPassengers());
    }

    @Test
    void testJoinFlightFromPreviousFlight() {
        // El pasajero ya está en flight1 y se une a flight2
        passenger.joinFlight(flight1);
        passenger.joinFlight(flight2);
        assertEquals(flight2, passenger.getFlight());
        assertEquals(1, flight2.getNumberOfPassengers());
        assertEquals(0, flight1.getNumberOfPassengers());
    }

    @Test
    void testToString() {
        // Verificar el formato del método toString
        String expected = "Passenger John Doe with identifier: P001 from US";
        assertEquals(expected, passenger.toString());
    }

    @Test
    void testInvalidCountryCode() {
        // Verificar que la creación del pasajero con código de país inválido lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> new Passenger("P004", "Eve", "XX"));
        assertEquals("Invalid country code", exception.getMessage());
    }

    @Test
    void testLeaveFlightAndJoinNew() {
        // El pasajero deja un vuelo y luego se une a otro
        passenger.joinFlight(flight1);
        passenger.joinFlight(null); // Deja el vuelo
        passenger.joinFlight(flight2); // Se une al segundo vuelo
        assertEquals(flight2, passenger.getFlight());
        assertEquals(1, flight2.getNumberOfPassengers());
        assertEquals(0, flight1.getNumberOfPassengers());
    }

    @Test
    void testJoinFlightWhenAlreadyInFlight() {
        // El pasajero ya está en flight1 y se une nuevamente a flight1
        passenger.joinFlight(flight1);
        passenger.joinFlight(flight1); // No debería causar error
        assertEquals(flight1, passenger.getFlight());
        assertEquals(1, flight1.getNumberOfPassengers());
    }

    @Test
    void testRemovePassengerFromFlight() {
        // Verificar que el pasajero se puede eliminar de un vuelo
        passenger.joinFlight(flight1);
        assertTrue(flight1.removePassenger(passenger));
        assertNull(passenger.getFlight());
        assertEquals(0, flight1.getNumberOfPassengers());
    }

    @Test
    void testRemovePassengerNotInFlight() {
        // Intentar eliminar a un pasajero no agregado previamente
        Passenger newPassenger = new Passenger("P005", "Mike", "CA");
        assertFalse(flight1.removePassenger(newPassenger));
    }
}
