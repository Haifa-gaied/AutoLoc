package tn.esprit.tpautoloc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tn.esprit.tpautoloc.domain.enums.StatutReservation;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    private StatutReservation statut;

    @NotNull(message = "Le client est obligatoire")
    private Long clientId;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long vehiculeId;

    private Long contratId;

    // Vérifier que la date de fin est après la date de début
    @JsonIgnore
    @AssertTrue(message = "La date de fin doit être après la date de début")
    public boolean isDatesValides() {

        if (dateDebut == null || dateFin == null) {
            return true;
        }

        return dateFin.isAfter(dateDebut);
    }
}