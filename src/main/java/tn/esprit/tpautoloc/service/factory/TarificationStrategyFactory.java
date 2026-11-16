
package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.ITarificationStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TarificationStrategyFactory {

    private final Map<String, ITarificationStrategy> strategies;

    public TarificationStrategyFactory(
            List<ITarificationStrategy> strategyList) {

        this.strategies = new HashMap<>();

        for (ITarificationStrategy strategy : strategyList) {
            strategies.put(strategy.getCode(), strategy);
        }
    }

    public ITarificationStrategy resoudre(Reservation reservation) {

        if (reservation == null ||
                reservation.getDateDebut() == null ||
                reservation.getDateFin() == null) {

            throw new IllegalArgumentException(
                    "Les dates de réservation sont obligatoires"
            );
        }

        long nombreJours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );

        if (nombreJours <= 0) {
            throw new IllegalArgumentException(
                    "La date de fin doit être après la date de début"
            );
        }

        String code;

        if (nombreJours >= 7) {
            code = "LONGUE_DUREE";
        } else if (estWeekend(reservation)) {
            code = "WEEKEND";
        } else {
            code = "STANDARD";
        }

        ITarificationStrategy strategy = strategies.get(code);

        if (strategy == null) {
            throw new IllegalStateException(
                    "Aucune stratégie trouvée pour : " + code
            );
        }

        return strategy;
    }

    private boolean estWeekend(Reservation reservation) {

        LocalDate dateDebut = reservation.getDateDebut();

        return DayOfWeek.SATURDAY.equals(dateDebut.getDayOfWeek())
                || DayOfWeek.SUNDAY.equals(dateDebut.getDayOfWeek());
    }
}
