
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.repository.IAgenceRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AgenceRepositoryTest {

    @Autowired
    private IAgenceRepository agenceRepository;

    private Agence createAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71000000");

        return agenceRepository.save(agence);
    }

    @Test
    void testSaveAgence() {
        Agence agence = createAgence();

        assertNotNull(agence.getIdAgence());
    }

    @Test
    void testFindAllAgences() {
        Agence agence = createAgence();

        List<Agence> agences = agenceRepository.findAll();

        assertTrue(
                agences.stream().anyMatch(
                        a -> a.getIdAgence().equals(agence.getIdAgence())
                )
        );
    }

    @Test
    void testFindAgenceById() {
        Agence agence = createAgence();

        Optional<Agence> resultat =
                agenceRepository.findById(agence.getIdAgence());

        assertTrue(resultat.isPresent());
        assertEquals(
                agence.getIdAgence(),
                resultat.get().getIdAgence()
        );
    }

    @Test
    void testExistsAgenceById() {
        Agence agence = createAgence();

        boolean existe =
                agenceRepository.existsById(agence.getIdAgence());

        assertTrue(existe);
    }

    @Test
    void testCountAgences() {
        Agence agence = createAgence();

        long nombreAgences = agenceRepository.count();

        assertTrue(nombreAgences >= 1);
        assertTrue(
                agenceRepository.existsById(agence.getIdAgence())
        );
    }

    @Test
    void testDeleteAgenceById() {
        Agence agence = createAgence();
        Long id = agence.getIdAgence();

        agenceRepository.deleteById(id);

        assertFalse(agenceRepository.existsById(id));
    }
}
