
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.service.IAgenceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testCreateAndFindAgence() {

        Agence agence = new Agence();
        agence.setNom("Agence Test Service");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71000000");

        Agence saved = agenceService.create(agence);

        assertNotNull(saved.getIdAgence());

        Agence found = agenceService.findById(saved.getIdAgence());

        assertEquals("Agence Test Service", found.getNom());

        System.out.println("Agence créée et retrouvée : "
                + found.getIdAgence());
    }

    @Test
    void testFindAllAgences() {

        var agences = agenceService.findAll();

        assertNotNull(agences);

        System.out.println("Nombre d'agences : " + agences.size());
    }

    @Test
    void testDeleteAgence() {

        Agence agence = new Agence();
        agence.setNom("Agence à supprimer");
        agence.setVille("Sousse");

        Agence saved = agenceService.create(agence);
        Long id = saved.getIdAgence();

        agenceService.deleteById(id);

        assertThrows(
                RuntimeException.class,
                () -> agenceService.findById(id)
        );

        System.out.println("Agence supprimée : " + id);
    }
    @Test
    void testCountAndExistsAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence Count Test");
        agence.setVille("Tunis");

        long nombreAvant = agenceService.count();
        Agence saved = agenceService.create(agence);

        try {
            assertEquals(nombreAvant + 1, agenceService.count());
            assertTrue(agenceService.existsById(saved.getIdAgence()));
        } finally {
            agenceService.deleteById(saved.getIdAgence());
        }

        assertFalse(agenceService.existsById(saved.getIdAgence()));
        assertEquals(nombreAvant, agenceService.count());
    }
}
