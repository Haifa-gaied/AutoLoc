
package tn.esprit.tpautoloc.service.strategy;

import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.Reservation;

import java.math.BigDecimal;

public interface ITarificationStrategy {

    BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation);

    String getCode();
}
