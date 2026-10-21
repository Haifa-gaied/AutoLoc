
package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IVehiculeService;

import java.util.List;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant");
        }

        validerVehicule(vehicule);

        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);

        validerVehicule(vehicule);

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setStatut(vehicule.getStatut());
        existant.setTarifJournalier(vehicule.getTarifJournalier());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }

        vehiculeRepository.deleteById(id);
    }

    private void validerVehicule(Vehicule vehicule) {
        BigDecimal tarif = vehicule.getTarifJournalier();

        if (tarif != null && tarif.signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif");
        }
    }
    @Override
    public long count() {
        return vehiculeRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return vehiculeRepository.existsById(id);
    }
}

