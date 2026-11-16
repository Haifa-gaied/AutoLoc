package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.dto.ReservationDTO;
import tn.esprit.tpautoloc.mapper.ReservationMapper;
import tn.esprit.tpautoloc.service.IReservationService;

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
    private final ReservationMapper reservationMapper;

    // GET - Lister toutes les réservations
    @Operation(summary = "Lister toutes les réservations")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping
    public List<ReservationDTO> findAll() {
        return reservationService.findAll()
                .stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    // GET - Consulter une réservation
    @Operation(summary = "Consulter une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @GetMapping("/{id}")
    public ReservationDTO findById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return reservationMapper.toDTO(reservation);
    }

    // POST - Créer une réservation
    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@Valid @RequestBody ReservationDTO dto) {
        Reservation reservation = reservationMapper.toEntity(dto);
        Reservation creee = reservationService.create(reservation);
        return reservationMapper.toDTO(creee);
    }

    // PUT - Modifier une réservation
    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @PutMapping("/{id}")
    public ReservationDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO dto) {

        Reservation reservation = reservationMapper.toEntity(dto);
        Reservation modifiee = reservationService.update(id, reservation);
        return reservationMapper.toDTO(modifiee);
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