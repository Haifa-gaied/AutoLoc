
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.service.IPaiementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des paiements",
        description = "API REST pour la consultation des paiements"
)
public class PaiementController {

    private final IPaiementService paiementService;

    // GET - Liste de tous les paiements
    @Operation(
            summary = "Lister tous les paiements",
            description = "Retourne la liste complète des paiements enregistrés"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des paiements récupérée avec succès"
            )
    })
    @GetMapping
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }

    // GET - Paiement par identifiant
    @Operation(
            summary = "Consulter un paiement",
            description = "Recherche un paiement par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paiement trouvé"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paiement introuvable"
            )
    })
    @GetMapping("/{id}")
    public Paiement findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }
}
