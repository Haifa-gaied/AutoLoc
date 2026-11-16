package tn.esprit.tpautoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.dto.ReservationDTO;

@Component
public class ReservationMapper {

    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDTO dto = new ReservationDTO();

        dto.setIdReservation(reservation.getIdReservation());
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setStatut(reservation.getStatut());

        if (reservation.getClient() != null) {
            dto.setClientId(reservation.getClient().getIdClient());
        }

        if (reservation.getVehicule() != null) {
            dto.setVehiculeId(reservation.getVehicule().getIdVehicule());
        }

        if (reservation.getContrat() != null) {
            dto.setContratId(reservation.getContrat().getIdContrat());
        }

        return dto;
    }

    public Reservation toEntity(ReservationDTO dto) {
        if (dto == null) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setIdReservation(dto.getIdReservation());
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());

        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setIdClient(dto.getClientId());
            reservation.setClient(client);
        }

        if (dto.getVehiculeId() != null) {
            Vehicule vehicule = new Vehicule();
            vehicule.setIdVehicule(dto.getVehiculeId());
            reservation.setVehicule(vehicule);
        }

        if (dto.getContratId() != null) {
            Contrat contrat = new Contrat();
            contrat.setIdContrat(dto.getContratId());
            reservation.setContrat(contrat);
        }

        return reservation;
    }
}