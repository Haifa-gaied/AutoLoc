
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.service.IMaintenanceService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MaintenanceServiceTest {

    @Autowired
    private IMaintenanceService maintenanceService;

    @Test
    void testCreateFindAndDelete() {
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription("Vidange Test Service");
        maintenance.setDateDebut(LocalDate.of(2026, 10, 1));
        maintenance.setDateFin(LocalDate.of(2026, 10, 2));

        Maintenance saved = maintenanceService.create(maintenance);
        Long id = saved.getIdMaintenance();

        assertNotNull(id);

        try {
            Maintenance found = maintenanceService.findById(id);
            assertEquals("Vidange Test Service",
                    found.getDescription());

            assertTrue(maintenanceService.findAll()
                    .stream()
                    .anyMatch(m -> id.equals(m.getIdMaintenance())));
        } finally {
            maintenanceService.deleteById(id);
        }

        assertThrows(ResourceNotFoundException.class,
                () -> maintenanceService.findById(id));
    }

    @Test
    void testDatesInvalides() {
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription("Maintenance invalide");
        maintenance.setDateDebut(LocalDate.of(2026, 10, 10));
        maintenance.setDateFin(LocalDate.of(2026, 10, 5));

        assertThrows(IllegalArgumentException.class,
                () -> maintenanceService.create(maintenance));
    }
}
