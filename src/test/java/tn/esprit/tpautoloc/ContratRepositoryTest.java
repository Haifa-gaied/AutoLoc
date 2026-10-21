
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.repository.IContratRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContratRepositoryTest {

    @Autowired
    private IContratRepository contratRepository;

    @Test
    void testSaveContrat() {

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("850.00"));
        contrat.setValide(true);

        Contrat saved = contratRepository.save(contrat);

        assertNotNull(saved.getIdContrat());

        System.out.println("Contrat enregistré : ID "
                + saved.getIdContrat());
    }

    @Test
    void testFindByIdContrat() {

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("950.00"));
        contrat.setValide(true);

        Contrat saved = contratRepository.save(contrat);
        Long id = saved.getIdContrat();

        Contrat found = contratRepository.findById(id)
                .orElseThrow();

        assertEquals(
                0,
                new BigDecimal("950.00")
                        .compareTo(found.getMontantTotal())
        );

        System.out.println("Contrat trouvé : ID "
                + found.getIdContrat());

        contratRepository.deleteById(id);
    }

    @Test
    void testExistsContrat() {

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("700.00"));
        contrat.setValide(false);

        Contrat saved = contratRepository.save(contrat);
        Long id = saved.getIdContrat();

        boolean existe = contratRepository.existsById(id);

        assertTrue(existe);

        System.out.println("Contrat existe : " + existe);

        contratRepository.deleteById(id);
    }

    @Test
    void testDeleteContrat() {

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("600.00"));
        contrat.setValide(false);

        Contrat saved = contratRepository.save(contrat);
        Long id = saved.getIdContrat();

        contratRepository.deleteById(id);

        boolean existe = contratRepository.existsById(id);

        assertFalse(existe);

        System.out.println("Contrat supprimé : ID " + id);
    }
}
