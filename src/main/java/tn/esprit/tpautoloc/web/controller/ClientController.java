
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.service.IClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des clients",
        description = "API REST pour la gestion des clients AutoLoc"
)
public class ClientController {

    private final IClientService clientService;

    // GET - Lister tous les clients
    @Operation(summary = "Lister tous les clients")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des clients récupérée avec succès"
    )
    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    // GET - Consulter un client par ID
    @Operation(summary = "Consulter un client")
    @ApiResponse(responseCode = "200", description = "Client trouvé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    // POST - Créer un client
    @Operation(summary = "Créer un client")
    @ApiResponse(responseCode = "201", description = "Client créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientService.create(client);
    }

    // PUT - Modifier un client
    @Operation(summary = "Modifier un client")
    @ApiResponse(responseCode = "200", description = "Client modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @PutMapping("/{id}")
    public Client update(
            @PathVariable Long id,
            @RequestBody Client client) {
        return clientService.update(id, client);
    }

    // DELETE - Supprimer un client
    @Operation(summary = "Supprimer un client")
    @ApiResponse(responseCode = "204", description = "Client supprimé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
