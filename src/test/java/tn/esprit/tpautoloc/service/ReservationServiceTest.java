
package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.observer.EmailNotificationObserver;
import tn.esprit.tpautoloc.service.observer.IReservationObserver;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;
import tn.esprit.tpautoloc.service.observer.SmsNotificationObserver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationServiceTest {

    private ReservationSubject reservationSubject;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        List<IReservationObserver> observateurs = List.of(
                new EmailNotificationObserver(),
                new SmsNotificationObserver()
        );

        reservationSubject = new ReservationSubject(observateurs);

        Client client = new Client();
        client.setEmail("client@test.com");
        client.setTelephone("22123456");

        reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setClient(client);
    }

    @Test
    void testConfirmationReservation() {
        assertDoesNotThrow(
                () -> reservationSubject.notifierConfirmation(reservation)
        );
    }

    @Test
    void testAnnulationReservation() {
        assertDoesNotThrow(
                () -> reservationSubject.notifierAnnulation(reservation)
        );
    }
}
