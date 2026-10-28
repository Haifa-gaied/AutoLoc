
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.service.IEquipementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des équipements",
        description = "API REST pour la gestion des équipements des véhicules AutoLoc"
)
public class EquipementController {

    private final IEquipementService equipementService;

    // GET - Lister tous les équipements
    @Operation(summary = "Lister tous les équipements")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des équipements récupérée avec succès"
    )
    @GetMapping
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    // GET - Consulter un équipement
    @Operation(summary = "Consulter un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @GetMapping("/{id}")
    public Equipement findById(@PathVariable Long id) {
        return equipementService.findById(id);
    }

    // POST - Créer un équipement
    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    // PUT - Modifier un équipement
    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @PutMapping("/{id}")
    public Equipement update(
            @PathVariable Long id,
            @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }

    // DELETE - Supprimer un équipement
    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
