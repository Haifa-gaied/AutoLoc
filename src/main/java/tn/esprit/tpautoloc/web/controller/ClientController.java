package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.dto.ClientDTO;
import tn.esprit.tpautoloc.mapper.ClientMapper;
import tn.esprit.tpautoloc.service.IClientService;

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
    private final ClientMapper clientMapper;

    // GET - Lister tous les clients
    @Operation(summary = "Lister tous les clients")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des clients récupérée avec succès"
    )
    @GetMapping
    public List<ClientDTO> findAll() {
        return clientService.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .toList();
    }

    // GET - Consulter un client par ID
    @Operation(summary = "Consulter un client")
    @ApiResponse(responseCode = "200", description = "Client trouvé")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        return clientMapper.toDTO(client);
    }

    // POST - Créer un client
    @Operation(summary = "Créer un client")
    @ApiResponse(responseCode = "201", description = "Client créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO create(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client clientCree = clientService.create(client);
        return clientMapper.toDTO(clientCree);
    }

    // PUT - Modifier un client
    @Operation(summary = "Modifier un client")
    @ApiResponse(responseCode = "200", description = "Client modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Client introuvable")
    @PutMapping("/{id}")
    public ClientDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ClientDTO clientDTO) {

        Client client = clientMapper.toEntity(clientDTO);
        Client clientModifie = clientService.update(id, client);
        return clientMapper.toDTO(clientModifie);
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