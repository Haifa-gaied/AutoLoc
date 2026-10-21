
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehiculeRepositoryTest {

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Test
    void testSaveVehicule() {

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("123-TUN-456");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setTarifJournalier(new BigDecimal("120.00"));

        Vehicule saved = vehiculeRepository.save(vehicule);

        assertNotNull(saved.getIdVehicule());

        System.out.println("Véhicule enregistré : ID "
                + saved.getIdVehicule());
    }

    @Test
    void testFindAllVehicules() {

        List<Vehicule> vehicules = vehiculeRepository.findAll();

        System.out.println("Liste des véhicules :");

        for (Vehicule vehicule : vehicules) {
            System.out.println(
                    vehicule.getIdVehicule() + " - " +
                            vehicule.getMarque() + " " +
                            vehicule.getModele()
            );
        }

        assertFalse(vehicules.isEmpty());
    }

    @Test
    void testCountVehicules() {

        long nombreVehicules = vehiculeRepository.count();

        System.out.println("Nombre total de véhicules : "
                + nombreVehicules);

        assertTrue(nombreVehicules > 0);
    }

    @Test
    void testUpdateVehicule() {

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-UPDATE-001");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setTarifJournalier(new BigDecimal("120.00"));

        Vehicule saved = vehiculeRepository.save(vehicule);

        saved.setTarifJournalier(new BigDecimal("150.00"));

        vehiculeRepository.save(saved);

        Vehicule updated = vehiculeRepository
                .findById(saved.getIdVehicule())
                .orElseThrow();

        assertEquals(
                0,
                new BigDecimal("150.00")
                        .compareTo(updated.getTarifJournalier())
        );

        System.out.println("Nouveau tarif : "
                + updated.getTarifJournalier());

        vehiculeRepository.deleteById(saved.getIdVehicule());
    }

    @Test
    void testDeleteVehicule() {

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-DELETE-001");
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setTarifJournalier(new BigDecimal("100.00"));

        Vehicule saved = vehiculeRepository.save(vehicule);
        Long id = saved.getIdVehicule();

        vehiculeRepository.deleteById(id);

        assertFalse(vehiculeRepository.existsById(id));

        System.out.println("Véhicule supprimé : ID " + id);
    }
}
