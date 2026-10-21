
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.service.IEquipementService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EquipementServiceTest {

    @Autowired
    private IEquipementService equipementService;

    @Test
    void testCreateFindAndDelete() {
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS Test Service");

        Equipement saved = equipementService.create(equipement);
        Long id = saved.getIdEquipement();

        assertNotNull(id);
        assertEquals("GPS Test Service",
                equipementService.findById(id).getLibelle());

        assertTrue(equipementService.findAll()
                .stream()
                .anyMatch(e -> id.equals(e.getIdEquipement())));

        equipementService.deleteById(id);

        assertThrows(ResourceNotFoundException.class,
                () -> equipementService.findById(id));
    }

    @Test
    void testLibelleObligatoire() {
        Equipement equipement = new Equipement();

        assertThrows(IllegalArgumentException.class,
                () -> equipementService.create(equipement));
    }
}
