
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
@SpringBootTest
public class AgenceRepositoryTest {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Test
    void testSaveAgence() {

        Agence agence = new Agence();

        agence.setNom("Agence Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71000000");

        Agence agenceEnregistree = agenceRepository.save(agence);

        System.out.println("Agence enregistrée : "
                + agenceEnregistree.getIdAgence());

        assertNotNull(agenceEnregistree.getIdAgence());
    }

    @Test
    void testFindAllAgences() {

        List<Agence> agences = agenceRepository.findAll();

        System.out.println("Liste des agences :");

        for (Agence agence : agences) {
            System.out.println(
                    agence.getIdAgence() + " - " +
                            agence.getNom() + " - " +
                            agence.getVille()
            );
        }

        assertFalse(agences.isEmpty());
    }

    @Test
    void testFindAgenceById() {

        Long id = 1L;

        Optional<Agence> agence = agenceRepository.findById(id);

        assertTrue(agence.isPresent());

        System.out.println("Agence trouvée : "
                + agence.get().getNom()
                + " - " + agence.get().getVille());
    }

    @Test
    void testExistsAgenceById() {

        boolean existe = agenceRepository.existsById(1L);

        System.out.println("Agence ID 1 existe : " + existe);

        assertTrue(existe);
    }

    @Test
    void testCountAgences() {

        long nombreAgences = agenceRepository.count();

        System.out.println("Nombre total d'agences : " + nombreAgences);

        assertTrue(nombreAgences > 0);
    }

    @Test
    void testDeleteAgenceById() {

        Agence agence = new Agence();
        agence.setNom("Agence à supprimer");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71000001");

        // Enregistrer une agence temporaire
        Agence agenceEnregistree = agenceRepository.save(agence);
        Long id = agenceEnregistree.getIdAgence();

        // Supprimer cette agence
        agenceRepository.deleteById(id);

        // Vérifier qu'elle n'existe plus
        boolean existe = agenceRepository.existsById(id);

        assertFalse(existe);

        System.out.println("Agence supprimée avec succès : ID " + id);
    }



}
