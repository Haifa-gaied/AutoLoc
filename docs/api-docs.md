# Atelier 5 — Spring MVC et SpringDoc
## Documentation technique de l'API REST AutoLoc

**Projet :** AutoLoc — Gestion de location de véhicules  
**Technologies :** Java 17, Spring Boot, Spring MVC, Spring Data JPA, MariaDB (XAMPP), Postman, SpringDoc OpenAPI, Swagger UI et SonarQube for IDE.

---

## A. Endpoints REST

L'application AutoLoc expose les services métiers sous forme d'API REST. Les contrôleurs sont placés dans `tn.esprit.tpautoloc.web.controller` et communiquent avec la couche Service par l'intermédiaire des interfaces `I...Service`.

L'application utilise le préfixe `/api` et fonctionne sur le port `8081`.

**URL de base :** `http://localhost:8081/api`

### A.1. Gestion des agences

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/agences` | `findAll()` | 200 | — |
| GET | `/agences/{id}` | `findById(id)` | 200 | 404 |
| POST | `/agences` | `create(agence)` | 201 | 400 |
| PUT | `/agences/{id}` | `update(id, agence)` | 200 | 400, 404 |
| DELETE | `/agences/{id}` | `deleteById(id)` | 204 | 404 |

### A.2. Gestion des employés

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/employes` | `findAll()` | 200 | — |
| GET | `/employes/{id}` | `findById(id)` | 200 | 404 |
| POST | `/employes` | `create(employe)` | 201 | 400 |
| PUT | `/employes/{id}` | `update(id, employe)` | 200 | 400, 404 |
| DELETE | `/employes/{id}` | `deleteById(id)` | 204 | 404 |

### A.3. Gestion des véhicules

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/vehicules` | `findAll()` | 200 | — |
| GET | `/vehicules/{id}` | `findById(id)` | 200 | 404 |
| POST | `/vehicules` | `create(vehicule)` | 201 | 400 |
| PUT | `/vehicules/{id}` | `update(id, vehicule)` | 200 | 400, 404 |
| DELETE | `/vehicules/{id}` | `deleteById(id)` | 204 | 404 |

### A.4. Gestion des équipements

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/equipements` | `findAll()` | 200 | — |
| GET | `/equipements/{id}` | `findById(id)` | 200 | 404 |
| POST | `/equipements` | `create(equipement)` | 201 | 400 |
| PUT | `/equipements/{id}` | `update(id, equipement)` | 200 | 400, 404 |
| DELETE | `/equipements/{id}` | `deleteById(id)` | 204 | 404 |

### A.5. Gestion des clients

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/clients` | `findAll()` | 200 | — |
| GET | `/clients/{id}` | `findById(id)` | 200 | 404 |
| POST | `/clients` | `create(client)` | 201 | 400 |
| PUT | `/clients/{id}` | `update(id, client)` | 200 | 400, 404 |
| DELETE | `/clients/{id}` | `deleteById(id)` | 204 | 404 |

### A.6. Gestion des réservations

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/reservations` | `findAll()` | 200 | — |
| GET | `/reservations/{id}` | `findById(id)` | 200 | 404 |
| POST | `/reservations` | `create(reservation)` | 201 | 400 |
| PUT | `/reservations/{id}` | `update(id, reservation)` | 200 | 400, 404 |
| DELETE | `/reservations/{id}` | `deleteById(id)` | 204 | 404 |

### A.7. Gestion des contrats

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/contrats` | `findAll()` | 200 | — |
| GET | `/contrats/{id}` | `findById(id)` | 200 | 404 |
| POST | `/contrats` | `create(contrat)` | 201 | 400 |
| PUT | `/contrats/{id}` | `update(id, contrat)` | 200 | 400, 404 |
| DELETE | `/contrats/{id}` | `deleteById(id)` | 204 | 404 |

### A.8. Consultation des paiements

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/paiements` | `findAll()` | 200 | — |
| GET | `/paiements/{id}` | `findById(id)` | 200 | 404 |
| POST | `/paiements` | Aucun | — | 405 |

Le contrôleur des paiements est en lecture seule. La création des paiements s'effectue à travers les contrats.

### A.9. Gestion des maintenances

| Méthode | Endpoint | Service appelé | Succès | Erreurs prévues |
|---|---|---|---|---|
| GET | `/maintenances` | `findAll()` | 200 | — |
| GET | `/maintenances/{id}` | `findById(id)` | 200 | 404 |
| POST | `/maintenances` | `create(maintenance)` | 201 | 400 |
| PUT | `/maintenances/{id}` | `update(id, maintenance)` | 200 | 400, 404 |
| DELETE | `/maintenances/{id}` | `deleteById(id)` | 204 | 404 |

**Remarque :** Les erreurs 400 correspondent aux validations métier prévues par les services. Leur présence dans Swagger ne garantit pas, à elle seule, que tous les champs invalides sont effectivement rejetés.

---

## B. Annotations Jackson et gestion des associations

### B.1. Problème rencontré

Pendant les tests Swagger, certaines requêtes GET produisaient des réponses JSON très volumineuses à cause des associations bidirectionnelles entre les entités JPA.

Par exemple :

`Client → Reservation → Client → ...`

Les objets associés se référençaient mutuellement, entraînant une récursion lors de la sérialisation JSON.

### B.2. Corrections appliquées

| Entité | Association | Annotation | Justification |
|---|---|---|---|
| `Agence` | `employes` | `@JsonIgnore` | Éviter la boucle Agence–Employe |
| `Agence` | `vehicules` | `@JsonIgnore` | Éviter la boucle Agence–Vehicule |
| `Vehicule` | `reservations` | `@JsonIgnore` | Éviter la boucle Vehicule–Reservation |
| `Vehicule` | `maintenances` | `@JsonIgnore` | Éviter la boucle Vehicule–Maintenance |
| `Client` | `reservations` | `@JsonIgnore` | Éviter la boucle Client–Reservation |
| `Contrat` | `reservation` | `@JsonIgnore` | Éviter la boucle Contrat–Reservation |
| `Contrat` | `paiements` | `@JsonManagedReference` | Sérialiser les paiements du contrat |
| `Paiement` | `contrat` | `@JsonBackReference` | Éviter la référence inverse répétée |

### B.3. Différence entre les annotations

**`@JsonIgnore` :** exclut une propriété du traitement JSON, à la sérialisation et généralement à la désérialisation.

**`@JsonManagedReference` :** désigne le côté principal d'une association parent-enfant qui reste visible dans le JSON.

**`@JsonBackReference` :** désigne le côté inverse de l'association, omis à la sérialisation, mais pris en charge lors de la désérialisation du parent.

### B.4. Résultat

Après les corrections, les requêtes `GET /clients` et `GET /reservations` renvoient des réponses exploitables, sans répétition infinie des objets.

Ces annotations ne modifient pas les relations JPA dans la base de données. Elles modifient cependant les propriétés visibles ou acceptées dans les messages JSON.

---

## C. Postman et Swagger

### C.1. Postman

Postman est un outil de test des API REST. Il permet d'envoyer des requêtes HTTP, de modifier leurs paramètres et leurs corps JSON, puis d'inspecter les réponses.

Dans AutoLoc, il sert notamment à vérifier :

- Les opérations CRUD.
- Les statuts HTTP de succès et d'erreur.
- La création d'un contrat contenant des paiements.
- La suppression en cascade des paiements lorsqu'un contrat est supprimé.

**Exemple :** une requête `GET /api/contrats/9999` doit produire le statut `404 Not Found` si le contrat n'existe pas.

### C.2. Swagger / SpringDoc OpenAPI

SpringDoc génère une documentation OpenAPI à partir des contrôleurs Spring Boot.

Swagger UI affiche cette documentation dans une interface interactive.

Les principales annotations utilisées sont :

- `@Tag` : regroupe les opérations d'un contrôleur.
- `@Operation` : décrit une opération.
- `@ApiResponse` : documente un code de réponse.
- `@Parameter` : permet de décrire un paramètre.

**Exemple :** l'opération `DELETE /contrats/{id}` documente les réponses 204 (suppression réussie) et 404 (contrat introuvable).

### C.3. Différence entre Postman et Swagger

Postman est principalement destiné à préparer, sauvegarder, exécuter et automatiser des tests HTTP.

Swagger UI sert principalement à consulter la documentation technique générée depuis le code et à essayer les endpoints.

Les deux outils sont complémentaires.

**Swagger UI :** `http://localhost:8081/api/swagger-ui/index.html`

**OpenAPI JSON :** `http://localhost:8081/api/v3/api-docs`

---

## D. Anomalies SonarQube for IDE

L'analyse statique du package `web.controller` a permis d'identifier des anomalies de maintenabilité et des points d'amélioration architecturale.

### D.1. Tableau des anomalies

| Anomalie | Explication | Correction / décision |
|---|---|---|
| `Remove the 'ApiResponses' wrapper from this annotation group` | L'annotation conteneur est inutile lorsque `@ApiResponse` est répétable. | Suppression du conteneur dans la méthode `findById()` de `AgenceController`. |
| `Explicitly specify the time zone by passing a ZoneId or a Clock to the .now() method` | La méthode `LocalDateTime.now()` dépend implicitement du fuseau horaire système. | Utilisation de `LocalDateTime.now(ZoneId.of("Africa/Tunis"))` dans les deux gestionnaires d'exceptions. |
| `Replace this persistent entity with a simple POJO or DTO object` | L'exposition directe des entités JPA couple la couche API au modèle de persistance et peut révéler des champs internes. | Amélioration identifiée, reportée au chapitre consacré aux DTO, conformément au périmètre pédagogique de l'Atelier 5. |

### D.2. Première correction : simplification des annotations Swagger

**Avant :**

```java
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Agence trouvée"),
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
})
```

**Après :**

```java
@ApiResponse(responseCode = "200", description = "Agence trouvée")
@ApiResponse(responseCode = "404", description = "Agence introuvable")
```

Cette correction simplifie le code et conserve les deux réponses dans la documentation Swagger.

### D.3. Deuxième correction : gestion explicite du fuseau horaire

**Avant :**

```java
LocalDateTime.now()
```

**Après :**

```java
LocalDateTime.now(ZoneId.of("Africa/Tunis"))
```

Cette modification évite une dépendance implicite au fuseau horaire du serveur.

### D.4. Limitation architecturale restante

Le remplacement des entités JPA par des DTO constitue une amélioration importante pour l'encapsulation, la validation et la stabilité des contrats JSON.

Cependant, l'Atelier 5 demande volontairement d'exposer directement les entités JPA. Le refactoring vers des DTO sera étudié dans le chapitre correspondant.

---

## E. Questions de compréhension

### 1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?

Le `DispatcherServlet` est le contrôleur frontal de Spring MVC.

Il reçoit les requêtes HTTP et s'appuie sur les mécanismes de mapping de Spring MVC pour retrouver la méthode correspondant à l'URL et au verbe HTTP, notamment grâce aux annotations `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping` et `@DeleteMapping`.

### 2. Pourquoi ContratController dépend-il de IContratService et non de ContratRepository ni de ContratServiceImpl ?

Le contrôleur dépend de l'interface `IContratService` afin de respecter la séparation des couches et le principe d'inversion des dépendances.

L'accès aux données appartient à la couche Repository, tandis que les règles métier appartiennent à la couche Service.

L'injection par interface facilite aussi la maintenance et les tests.

### 3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?

Le code `201 Created` indique qu'une nouvelle ressource a été créée.

Le code `204 No Content` indique que l'opération a réussi sans nécessiter de corps de réponse.

Le code `404 Not Found` indique qu'une ressource demandée n'existe pas.

Dans AutoLoc, `ResourceNotFoundException` est interceptée par `GlobalExceptionHandler`, qui renvoie une réponse 404 au lieu d'une erreur serveur générique 500.

### 4. Pourquoi PaiementController n'expose-t-il que des GET ? Quelle réponse reçoit un POST /api/paiements ?

`PaiementController` est volontairement en lecture seule.

Les paiements sont créés et gérés dans le cadre d'un contrat, notamment grâce à la relation JPA avec cascade entre `Contrat` et `Paiement`.

Un `POST /api/paiements` n'est pas pris en charge et doit renvoyer `405 Method Not Allowed`.

### 5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi Contrat.paiements utilise-t-il @JsonManagedReference alors que Agence.vehicules utilise @JsonIgnore ?

L'exposition directe des entités JPA peut provoquer :

- Des dépendances fortes entre le modèle de données et l'API.
- L'exposition de propriétés internes.
- Des problèmes de chargement paresseux.
- Des références circulaires dans les réponses JSON.
- Une gestion insuffisamment précise des champs autorisés en entrée.

`Contrat.paiements` utilise `@JsonManagedReference` parce que les paiements constituent des objets enfants du contrat, notamment lors de leur création en cascade.

`Agence.vehicules` utilise `@JsonIgnore` parce qu'il s'agit d'une collection inverse qui n'est pas nécessaire dans la représentation JSON de l'agence et qui peut provoquer une récursion.

---

## Conclusion

Cet atelier a permis de construire et de documenter la couche REST d'AutoLoc à partir des services métiers existants.

Les neuf contrôleurs exposent les opérations HTTP appropriées, et Swagger fournit une documentation OpenAPI interactive.

Les tests ont notamment permis de vérifier la création, la modification, la consultation et la suppression de ressources, ainsi que la gestion des erreurs HTTP et des associations JSON.

L'analyse SonarQube a permis de corriger des problèmes de qualité du code et d'identifier l'utilisation future de DTOs comme amélioration architecturale.
