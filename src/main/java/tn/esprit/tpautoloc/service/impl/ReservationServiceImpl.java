package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;

import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;

import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private static final String RESERVATION = "Reservation";

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationSubject reservationSubject;

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {

        validerReservation(reservation);

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant"
            );
        }

        associerClientEtVehicule(reservation);

        Reservation sauvegardee = reservationRepository.save(reservation);

        if (sauvegardee.getStatut() == StatutReservation.CONFIRMEE) {
            reservationSubject.notifierConfirmation(sauvegardee);
        }

        return sauvegardee;
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public Reservation update(Long id, Reservation reservation) {

        Reservation existante = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION, id));

        validerReservation(reservation);

        StatutReservation ancienStatut = existante.getStatut();

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        Reservation sauvegardee = reservationRepository.save(existante);

        if (ancienStatut != StatutReservation.CONFIRMEE
                && sauvegardee.getStatut() == StatutReservation.CONFIRMEE) {

            reservationSubject.notifierConfirmation(sauvegardee);
        }

        if (ancienStatut != StatutReservation.ANNULEE
                && sauvegardee.getStatut() == StatutReservation.ANNULEE) {

            reservationSubject.notifierAnnulation(sauvegardee);
        }

        return sauvegardee;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESERVATION, id);
        }

        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Reservation annulerReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(RESERVATION, id));

        if (reservation.getStatut() == StatutReservation.ANNULEE) {
            return reservation;
        }

        reservation.setStatut(StatutReservation.ANNULEE);

        Reservation sauvegardee = reservationRepository.save(reservation);

        reservationSubject.notifierAnnulation(sauvegardee);

        return sauvegardee;
    }

    private void associerClientEtVehicule(Reservation reservation) {

        if (reservation.getClient() == null
                || reservation.getClient().getIdClient() == null) {

            throw new IllegalArgumentException(
                    "Un client existant est obligatoire"
            );
        }

        if (reservation.getVehicule() == null
                || reservation.getVehicule().getIdVehicule() == null) {

            throw new IllegalArgumentException(
                    "Un véhicule existant est obligatoire"
            );
        }

        Long clientId = reservation.getClient().getIdClient();
        Long vehiculeId = reservation.getVehicule().getIdVehicule();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", clientId));

        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", vehiculeId));

        reservation.setClient(client);
        reservation.setVehicule(vehicule);
    }

    private void validerReservation(Reservation reservation) {

        if (reservation == null) {
            throw new IllegalArgumentException(
                    "La réservation est obligatoire"
            );
        }

        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}