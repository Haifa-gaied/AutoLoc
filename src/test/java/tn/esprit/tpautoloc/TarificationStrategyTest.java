package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import tn.esprit.tpautoloc.service.strategy.TarifStandardStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifWeekendStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifLongueDureeStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TarificationStrategyTest {

    @Test
    void testTarifStandard() {

        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("150"));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 11, 16));
        reservation.setDateFin(LocalDate.of(2026, 11, 19));

        TarifStandardStrategy strategy = new TarifStandardStrategy();

        BigDecimal resultat = strategy.calculerTarif(vehicule, reservation);

        assertEquals(0, new BigDecimal("450").compareTo(resultat));
    }

    @Test
    void testTarifWeekend() {

        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("150"));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 11, 21));
        reservation.setDateFin(LocalDate.of(2026, 11, 23));

        TarifWeekendStrategy strategy = new TarifWeekendStrategy();

        BigDecimal resultat = strategy.calculerTarif(vehicule, reservation);

        assertEquals(0, new BigDecimal("270").compareTo(resultat));
    }

    @Test
    void testTarifLongueDuree() {

        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("150"));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 11, 16));
        reservation.setDateFin(LocalDate.of(2026, 11, 26));

        TarifLongueDureeStrategy strategy = new TarifLongueDureeStrategy();

        BigDecimal resultat = strategy.calculerTarif(vehicule, reservation);

        assertEquals(0, new BigDecimal("1200").compareTo(resultat));
    }
}