
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.service.IContratService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des contrats",
        description = "API REST pour la gestion des contrats de location"
)
public class ContratController {

    private final IContratService contratService;

    // GET - Tous les contrats
    @Operation(
            summary = "Lister tous les contrats",
            description = "Retourne la liste complète des contrats de location"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des contrats récupérée avec succès")
    })
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }


    // GET - Contrat par ID
    @Operation(
            summary = "Consulter un contrat",
            description = "Recherche un contrat de location par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrat trouvé"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @GetMapping("/{id}")
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }


    // POST - Créer un contrat
    @Operation(
            summary = "Créer un contrat",
            description = "Enregistre un nouveau contrat de location"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contrat créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données du contrat invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    // PUT - Modifier un contrat
    @Operation(
            summary = "Modifier un contrat",
            description = "Met à jour les informations d'un contrat existant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrat modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données du contrat invalides"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @PutMapping("/{id}")
    public Contrat update(
            @PathVariable Long id,
            @RequestBody Contrat contrat) {
        return contratService.update(id, contrat);
    }


    // DELETE - Supprimer un contrat
    @Operation(
            summary = "Supprimer un contrat",
            description = "Supprime un contrat de location par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contrat supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
