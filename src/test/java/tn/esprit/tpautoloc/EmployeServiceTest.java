
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.service.IEmployeService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeServiceTest {

    @Autowired
    private IEmployeService employeService;

    @Test
    void testCreateFindAndDelete() {
        Employe employe = new Employe();
        employe.setNom("Employe Test");
        employe.setPrenom("Service");

        Employe saved = employeService.create(employe);
        Long id = saved.getIdEmploye();

        assertNotNull(id);

        try {
            Employe found = employeService.findById(id);
            assertEquals("Employe Test", found.getNom());

            assertTrue(employeService.findAll()
                    .stream()
                    .anyMatch(e -> id.equals(e.getIdEmploye())));
        } finally {
            employeService.deleteById(id);
        }

        assertThrows(ResourceNotFoundException.class,
                () -> employeService.findById(id));
    }

    @Test
    void testNomObligatoire() {
        Employe employe = new Employe();

        assertThrows(IllegalArgumentException.class,
                () -> employeService.create(employe));
    }
}
