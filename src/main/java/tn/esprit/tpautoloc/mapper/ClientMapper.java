package tn.esprit.tpautoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.dto.ClientDTO;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();

        dto.setIdClient(client.getIdClient());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setEmail(client.getEmail());
        dto.setTelephone(client.getTelephone());
        dto.setNumPermis(client.getNumPermis());
        dto.setDateInscription(client.getDateInscription());

        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();

        client.setIdClient(dto.getIdClient());
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setNumPermis(dto.getNumPermis());
        client.setDateInscription(dto.getDateInscription());

        return client;
    }
}