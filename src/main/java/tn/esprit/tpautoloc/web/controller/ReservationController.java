
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.IReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des réservations",
        description = "API REST pour la gestion des réservations AutoLoc"
)
public class ReservationController {

    private final IReservationService reservationService;

    // GET - Lister toutes les réservations
    @Operation(summary = "Lister toutes les réservations")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des réservations récupérée avec succès"
    )
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    // GET - Consulter une réservation
    @Operation(summary = "Consulter une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @GetMapping("/{id}")
    public Reservation findById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    // POST - Créer une réservation
    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    // PUT - Modifier une réservation
    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @PutMapping("/{id}")
    public Reservation update(
            @PathVariable Long id,
            @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    // DELETE - Supprimer une réservation
    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
