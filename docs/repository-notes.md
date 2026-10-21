
# Atelier 3 - Spring Data JPA : Repository

## 1. Choix des interfaces

Pour le projet AutoLoc, nous avons créé une interface repository
pour chaque entité. Les interfaces héritent de JpaRepository
afin d'utiliser les opérations CRUD sans écrire leur implémentation.

| Interface | Type | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | Gestion des agences |
| IEmployeRepository | JpaRepository<Employe, Long> | Gestion des employés |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | Gestion des véhicules |
| IEquipementRepository | JpaRepository<Equipement, Long> | Gestion des équipements |
| IClientRepository | JpaRepository<Client, Long> | Gestion des clients |
| IReservationRepository | JpaRepository<Reservation, Long> | Gestion des réservations |
| IContratRepository | JpaRepository<Contrat, Long> | Gestion des contrats |
| IPaiementRepository | JpaRepository<Paiement, Long> | Gestion des paiements |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Gestion des maintenances |

Nous avons d'abord utilisé CrudRepository pour IContratRepository,
puis nous l'avons remplacé par JpaRepository.

JpaRepository permet d'utiliser les méthodes CRUD, le tri,
la pagination et des méthodes comme saveAndFlush().

## 2. Vérification

Après la création des neuf repositories, nous avons relancé
l'application Spring Boot.

Message obtenu dans la console :

Found 9 JPA repository interfaces.

L'application démarre correctement sur le port 8081
et la connexion à MySQL fonctionne.

## 3. Analyse SonarQube for IDE

Nous avons installé SonarQube for IDE dans IntelliJ
et lancé une analyse du projet.

Résultats :
- 23 fichiers Java analysés
- 0 issue détectée
- 0 Security Hotspot détecté

Aucune anomalie n'a été signalée par SonarQube lors
de cette analyse.

Les trois anomalies demandées dans le TP n'ont donc
pas pu être relevées à partir de ce résultat.

## 4. Tests CRUD

Cette partie sera complétée après les tests des repositories
de l'Atelier 3 - Partie 2.
