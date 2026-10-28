
package tn.esprit.tpautoloc.web.controller;

import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.service.IEmployeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(
        name = "Gestion des employés",
        description = "API REST pour la gestion des employés AutoLoc"
)
public class EmployeController {

    private final IEmployeService employeService;

    // GET - Lister tous les employés
    @Operation(summary = "Lister tous les employés")
    @ApiResponse(
            responseCode = "200",
            description = "Liste des employés récupérée avec succès"
    )
    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    // GET - Consulter un employé
    @Operation(summary = "Consulter un employé")
    @ApiResponse(responseCode = "200", description = "Employé trouvé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @GetMapping("/{id}")
    public Employe findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    // POST - Créer un employé
    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    // PUT - Modifier un employé
    @Operation(summary = "Modifier un employé")
    @ApiResponse(responseCode = "200", description = "Employé modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @PutMapping("/{id}")
    public Employe update(
            @PathVariable Long id,
            @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    // DELETE - Supprimer un employé
    @Operation(summary = "Supprimer un employé")
    @ApiResponse(responseCode = "204", description = "Employé supprimé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
