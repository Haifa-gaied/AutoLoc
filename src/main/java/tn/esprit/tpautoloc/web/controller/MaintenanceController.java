
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.service.IMaintenanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des maintenances",
        description = "API REST pour la gestion des opérations de maintenance AutoLoc"
)
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    // GET - Lister toutes les maintenances
    @Operation(summary = "Lister toutes les maintenances")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des maintenances récupérée avec succès"
    )
    @GetMapping
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    // GET - Consulter une maintenance
    @Operation(summary = "Consulter une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance trouvée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @GetMapping("/{id}")
    public Maintenance findById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    // POST - Créer une maintenance
    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Maintenance créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    // PUT - Modifier une maintenance
    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance modifiée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @PutMapping("/{id}")
    public Maintenance update(
            @PathVariable Long id,
            @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    // DELETE - Supprimer une maintenance
    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Maintenance supprimée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
