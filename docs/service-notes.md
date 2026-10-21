
# Atelier 4 — Couche Service, IoC et injection de dépendances

## A. Modes d'injection

### Injection par constructeur
- Utilise `@RequiredArgsConstructor` de Lombok.
- Les dépendances sont déclarées `private final`.
- Recommandée pour faciliter les tests et garantir l'initialisation.

### Injection par attribut
- Utilise `@Autowired` directement sur un attribut.
- Testée temporairement dans `ContratServiceImpl`.
- SonarQube a signalé : "Remove this field injection and use constructor injection instead."
- L'injection par constructeur a ensuite été restaurée.

## B. Services implémentés

| Service | Dépendance injectée |
|---|---|
| AgenceServiceImpl | IAgenceRepository |
| ClientServiceImpl | IClientRepository |
| ContratServiceImpl | IContratRepository |
| EmployeServiceImpl | IEmployeRepository |
| EquipementServiceImpl | IEquipementRepository |
| MaintenanceServiceImpl | IMaintenanceRepository |
| PaiementServiceImpl | IPaiementRepository |
| ReservationServiceImpl | IReservationRepository |
| VehiculeServiceImpl | IVehiculeRepository |

Les services proposent les opérations CRUD.
Exception : PaiementService est en lecture seule.

## C. Erreurs d'injection Spring

1. NoSuchBeanDefinitionException :
   Bean demandé mais introuvable.

2. NoUniqueBeanDefinitionException :
   Plusieurs Beans correspondent à une dépendance.

3. BeanCurrentlyInCreationException :
   Dépendance circulaire entre Beans.

Observation : la suppression de @Service dans
ContratServiceImpl n'a pas empêché le démarrage,
car aucun composant ne demandait IContratService.

## D. Analyse SonarQube

Observation confirmée :
- Injection par attribut déconseillée.
- Règle : utilisation de l'injection par constructeur.

Autres points à examiner dans l'IDE :
- Imports inutilisés.
- Duplication de code.
- Complexité et lisibilité des méthodes.

Ces trois derniers points ne sont pas des
avertissements SonarQube confirmés dans ce TP.

## E. Questions de compréhension

Q1. Quel est le rôle de @Service ?
R : Déclarer une classe comme Bean Spring détectable.

Q2. Pourquoi utiliser @RequiredArgsConstructor ?
R : Générer le constructeur des attributs final.

Q3. Pourquoi séparer Service et Repository ?
R : Le Repository gère la persistance, tandis que
le Service centralise les règles métier.

Q4. Pourquoi utiliser ResourceNotFoundException ?
R : Signaler clairement l'absence d'une ressource.

Q5. Pourquoi privilégier l'injection par constructeur ?
R : Pour rendre les dépendances explicites
et faciliter les tests unitaires.
