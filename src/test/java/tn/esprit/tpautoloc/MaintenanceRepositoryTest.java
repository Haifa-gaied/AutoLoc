
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MaintenanceRepositoryTest {

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Test
    void testSaveMaintenance() {

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription("Vidange du moteur");
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(1));

        Maintenance saved = maintenanceRepository.save(maintenance);

        assertNotNull(saved.getIdMaintenance());

        System.out.println("Maintenance enregistrée : ID "
                + saved.getIdMaintenance());
    }

    @Test
    void testFindByIdMaintenance() {

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription("Contrôle des freins");
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(1));

        Maintenance saved = maintenanceRepository.save(maintenance);

        Maintenance found = maintenanceRepository
                .findById(saved.getIdMaintenance())
                .orElseThrow();

        assertEquals("Contrôle des freins", found.getDescription());

        System.out.println("Maintenance trouvée : "
                + found.getDescription());

        maintenanceRepository.deleteById(saved.getIdMaintenance());
    }

    @Test
    void testExistsMaintenance() {

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription("Changement des pneus");
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(1));

        Maintenance saved = maintenanceRepository.save(maintenance);
        Long id = saved.getIdMaintenance();

        assertTrue(maintenanceRepository.existsById(id));

        System.out.println("Maintenance existe : true");

        maintenanceRepository.deleteById(id);
    }

    @Test
    void testDeleteMaintenance() {

        Maintenance maintenance = new Maintenance();
        maintenance.setDescription("Révision générale");
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(1));

        Maintenance saved = maintenanceRepository.save(maintenance);
        Long id = saved.getIdMaintenance();

        maintenanceRepository.deleteById(id);

        assertFalse(maintenanceRepository.existsById(id));

        System.out.println("Maintenance supprimée : ID " + id);
    }
}
