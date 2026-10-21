
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.service.IClientService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    void testCreateAndFindClient() {
        Client client = new Client();
        client.setNom("Test Client");
        client.setPrenom("Service");
        client.setEmail("client.test@example.com");
        client.setTelephone("22000000");

        Client saved = clientService.create(client);

        try {
            assertNotNull(saved.getIdClient());

            Client found = clientService.findById(saved.getIdClient());
            assertEquals("Test Client", found.getNom());
            assertEquals("client.test@example.com", found.getEmail());

            System.out.println("Client créé et retrouvé : "
                    + found.getIdClient());
        } finally {
            clientService.deleteById(saved.getIdClient());
        }
    }

    @Test
    void testFindAllClients() {
        var clients = clientService.findAll();

        assertNotNull(clients);
        System.out.println("Nombre de clients : " + clients.size());
    }

    @Test
    void testDeleteClient() {
        Client client = new Client();
        client.setNom("Client à supprimer");
        client.setEmail("delete.test@example.com");

        Client saved = clientService.create(client);
        Long id = saved.getIdClient();

        clientService.deleteById(id);

        assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.findById(id)
        );

        System.out.println("Client supprimé : " + id);
    }

    @Test
    void testValidationEmailObligatoire() {
        Client client = new Client();
        client.setNom("Client sans email");

        assertThrows(
                IllegalArgumentException.class,
                () -> clientService.create(client)
        );
    }

    @Test
    void testCountAndExistsClient() {
        Client client = new Client();
        client.setNom("Client Count Test");
        client.setEmail("count.test@example.com");

        long nombreAvant = clientService.count();
        Client saved = clientService.create(client);

        try {
            assertEquals(nombreAvant + 1, clientService.count());
            assertTrue(clientService.existsById(saved.getIdClient()));
        } finally {
            clientService.deleteById(saved.getIdClient());
        }

        assertFalse(clientService.existsById(saved.getIdClient()));
        assertEquals(nombreAvant, clientService.count());
    }

}
