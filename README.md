# E-Bank Application Backend - PART II Spring Security

Ce projet consiste à sécuriser et déployer une application bancaire en utilisant Spring Boot, Spring Security et Docker.

## Contexte du projet

Vous êtes développeur junior au sein du service informatique de l'enseigne bancaire Bank Solutions. Votre tâche est de sécuriser et protéger l'application avec Spring Boot, et de la déployer en utilisant Docker.

## Fonctionnalités

### Authentification avec Spring Security

#### Authentification des Utilisateurs

- Les utilisateurs doivent pouvoir s'authentifier via un nom d'utilisateur et un mot de passe.
- Les mots de passe doivent être hachés avant d'être enregistrés dans la base de données.
- Lors de l'authentification réussie, un JWT doit être généré et retourné à l'utilisateur.
- L'utilisateur doit inclure le JWT token dans les en-têtes des requêtes ultérieures pour prouver son identité.

#### Protection des Endpoints API

- Contrôle d'accès basé sur les rôles pour différentes fonctionnalités (consultation de solde, transfert d'argent, etc.).

### Déploiement avec Docker

#### Dockerisation de l'Application

- Créer un fichier Dockerfile pour containeriser l'application Spring Boot.
- Tester votre application (docker container) après le déploiement.

## Prérequis

- Java 17
- Maven 3.x
- Docker
