# Application de publication de sondages pour la compagnie d'assurance OXO

## 1 - Description du projet

Le client souhaite la création d'une nouvelle plateforme à disposition des salariés, pour leur permettre de voter sur des sujets liés à la vie de l'entreprise.
Le projet Service développe la partie back-end de l'application.

## 2 - Fonctionnalités du site :

Les points d'accès demandés sont :

- Afficher la liste de tous les sondages : GET/sondages
- Afficher en détail un sondage : GET /sondages/{id} 
- Créer d'un nouveau sondage avec enregistrement dans la base de données : POST /sondages
- Mise à jour des données d'un sondage : PUT /sondages/{id}
- Suppression du sondage {id} : DELETE /sondages/{id}

Un sondage comporte les données suivantes:

- Une description (chaîne vide/blanche interdite, min 3 caractères et max 120 caractères)
- Une question (max 120 car.)
- Une date de création (automatiquement remplie au jour actuel),
- Une date de cloture (doit être après la date de création)
- Le nom de la personne qui l'a proposé (chaine vide/blanche interdite, max 64 car.)




## 3 - Test des fonctionnalités du site :

Des Tests unitaires pour chacune des fonctionnalités du site ont été réalisés.

Par ailleurs, une page "swagger-ui.html" permettant de lister et tester vos points d'accès est disponible à l'adresse: http://localhost:8080/swagger-ui/index.html


## 4 - Outils de réalisation :

Code réalisé avec l'iDE Intellij
Projet réalisé en Java
Framework utilisé : Spring Boot


### Bibliothèques utilisées :

- Spring Boot DevTools
- Spring Web
- Mysql driver
- Spring Date JPA


## 5 - Installation du projet :

- Logiciel requis : Intellij
- Une base de donnée avec une table "festivals"
- Attention penser à changer le port du serveur de la classe : application.properties afin d'adapter le code pour que
  celui-ci fonctionne avec votre base de donnée.


  
### Merci d'avoir pris le temp de lire le ReadMe
