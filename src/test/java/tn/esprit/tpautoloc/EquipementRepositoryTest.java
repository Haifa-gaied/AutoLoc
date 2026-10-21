
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.repository.IEquipementRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EquipementRepositoryTest {

    @Autowired
    private IEquipementRepository equipementRepository;

    @Test
    void testSaveEquipement() {

        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS");

        Equipement saved = equipementRepository.save(equipement);

        assertNotNull(saved.getIdEquipement());

        System.out.println("Equipement enregistré : ID "
                + saved.getIdEquipement());
    }

    @Test
    void testFindAllEquipements() {

        var equipements = equipementRepository.findAll();

        System.out.println("Liste des équipements :");

        for (Equipement equipement : equipements) {
            System.out.println(
                    equipement.getIdEquipement() + " - " +
                            equipement.getLibelle()
            );
        }

        assertFalse(equipements.isEmpty());
    }

    @Test
    void testExistsEquipement() {

        Equipement equipement = new Equipement();
        equipement.setLibelle("Bluetooth");

        Equipement saved = equipementRepository.save(equipement);
        Long id = saved.getIdEquipement();

        assertTrue(equipementRepository.existsById(id));

        System.out.println("Equipement existe : "
                + equipementRepository.existsById(id));

        equipementRepository.deleteById(id);
    }

    @Test
    void testDeleteEquipement() {

        Equipement equipement = new Equipement();
        equipement.setLibelle("Climatisation");

        Equipement saved = equipementRepository.save(equipement);
        Long id = saved.getIdEquipement();

        equipementRepository.deleteById(id);

        assertFalse(equipementRepository.existsById(id));

        System.out.println("Equipement supprimé : ID " + id);
    }
}
