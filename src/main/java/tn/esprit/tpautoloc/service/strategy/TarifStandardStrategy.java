
package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
public class TarifStandardStrategy implements ITarificationStrategy {

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {

        long nombreJours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );

        if (nombreJours <= 0) {
            throw new IllegalArgumentException(
                    "La date de fin doit être après la date de début"
            );
        }

        if (vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException(
                    "Le tarif journalier du véhicule est obligatoire"
            );
        }

        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(nombreJours));
    }

    @Override
    public String getCode() {
        return "STANDARD";
    }
}
