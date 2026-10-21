
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.service.IVehiculeService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehiculeServiceTest {

    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void testCreateAndFindVehicule() {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-123");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setTarifJournalier(new BigDecimal("120"));

        Vehicule saved = vehiculeService.create(vehicule);

        try {
            assertNotNull(saved.getIdVehicule());

            Vehicule found =
                    vehiculeService.findById(saved.getIdVehicule());

            assertEquals("Toyota", found.getMarque());
        } finally {
            vehiculeService.deleteById(saved.getIdVehicule());
        }
    }

    @Test
    void testFindAllVehicules() {
        assertNotNull(vehiculeService.findAll());
    }

    @Test
    void testDeleteVehicule() {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("DELETE-123");
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setTarifJournalier(new BigDecimal("100"));

        Vehicule saved = vehiculeService.create(vehicule);
        Long id = saved.getIdVehicule();

        vehiculeService.deleteById(id);

        assertThrows(
                ResourceNotFoundException.class,
                () -> vehiculeService.findById(id)
        );
    }

    @Test
    void testTarifNegatif() {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("-50"));

        assertThrows(
                IllegalArgumentException.class,
                () -> vehiculeService.create(vehicule)
        );
    }

    @Test
    void testCountAndExistsVehicule() {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("COUNT-123");
        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");
        vehicule.setTarifJournalier(new BigDecimal("100"));

        long nombreAvant = vehiculeService.count();
        Vehicule saved = vehiculeService.create(vehicule);

        try {
            assertEquals(nombreAvant + 1, vehiculeService.count());
            assertTrue(vehiculeService.existsById(saved.getIdVehicule()));
        } finally {
            vehiculeService.deleteById(saved.getIdVehicule());
        }

        assertFalse(vehiculeService.existsById(saved.getIdVehicule()));
        assertEquals(nombreAvant, vehiculeService.count());
    }

}
