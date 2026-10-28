
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.service.IVehiculeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des véhicules",
        description = "API REST pour la gestion des véhicules AutoLoc"
)
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // GET - Lister tous les véhicules
    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des véhicules récupérée avec succès"
    )
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    // GET - Consulter un véhicule
    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    // POST - Créer un véhicule
    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    // PUT - Modifier un véhicule
    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @PutMapping("/{id}")
    public Vehicule update(
            @PathVariable Long id,
            @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    // DELETE - Supprimer un véhicule
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
