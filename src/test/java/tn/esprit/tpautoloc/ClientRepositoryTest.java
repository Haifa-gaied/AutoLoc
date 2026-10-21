
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.repository.IClientRepository;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private IClientRepository clientRepository;

    @Test
    void testSaveClients() {

        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed.test@example.com");
        client1.setTelephone("22111222");
        client1.setNumPermis("PERMIS001");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Sarra");
        client2.setEmail("sarra.test@example.com");
        client2.setTelephone("22333444");
        client2.setNumPermis("PERMIS002");
        client2.setDateInscription(LocalDate.now());

        Client saved1 = clientRepository.save(client1);
        Client saved2 = clientRepository.save(client2);

        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());

        System.out.println("Client 1 enregistré : ID " + saved1.getIdClient());
        System.out.println("Client 2 enregistré : ID " + saved2.getIdClient());
    }

    @Test
    void testFindAllClients() {

        List<Client> clients = clientRepository.findAll();

        System.out.println("Liste des clients :");

        for (Client client : clients) {
            System.out.println(
                    client.getIdClient() + " - " +
                            client.getNom() + " " +
                            client.getPrenom()
            );
        }

        assertFalse(clients.isEmpty());
    }

    @Test
    void testFindClientById() {

        Long id = 1L;

        Optional<Client> client = clientRepository.findById(id);

        assertTrue(client.isPresent());

        System.out.println("Client trouvé : "
                + client.get().getNom() + " "
                + client.get().getPrenom());
    }

    @Test
    void testExistsClientById() {

        Long idExistant = 1L;
        Long idInexistant = 999999L;

        boolean existe = clientRepository.existsById(idExistant);
        boolean inexistant = clientRepository.existsById(idInexistant);

        System.out.println("Client ID 1 existe : " + existe);
        System.out.println("Client ID 999999 existe : " + inexistant);

        assertTrue(existe);
        assertFalse(inexistant);
    }

    @Test
    void testCountClients() {

        long nombreClients = clientRepository.count();

        System.out.println("Nombre total de clients : " + nombreClients);

        assertTrue(nombreClients >= 2);
    }

}
