
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.repository.IClientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientRepositoryTest {

    @Autowired
    private IClientRepository clientRepository;

    private Client createClient() {
        Client client = new Client();

        client.setNom("Ben Ali");
        client.setPrenom("Ahmed");
        client.setEmail("ahmed.test@example.com");
        client.setTelephone("22111222");
        client.setNumPermis("PERMIS_TEST");
        client.setDateInscription(LocalDate.of(2026, 10, 28));

        return clientRepository.save(client);
    }

    @Test
    void testSaveClients() {
        Client client = createClient();

        assertNotNull(client.getIdClient());
    }

    @Test
    void testFindAllClients() {
        Client client = createClient();

        List<Client> clients = clientRepository.findAll();

        assertTrue(
                clients.stream().anyMatch(
                        c -> c.getIdClient().equals(client.getIdClient())
                )
        );
    }

    @Test
    void testFindClientById() {
        Client client = createClient();

        Optional<Client> resultat =
                clientRepository.findById(client.getIdClient());

        assertTrue(resultat.isPresent());
        assertEquals(
                client.getIdClient(),
                resultat.get().getIdClient()
        );
    }

    @Test
    void testExistsClientById() {
        Client client = createClient();

        boolean existe =
                clientRepository.existsById(client.getIdClient());

        assertTrue(existe);

        boolean inexistant =
                clientRepository.existsById(999999L);

        assertFalse(inexistant);
    }

    @Test
    void testCountClients() {
        Client client = createClient();

        long nombreClients = clientRepository.count();

        assertTrue(nombreClients >= 1);
        assertTrue(
                clientRepository.existsById(client.getIdClient())
        );
    }
}
