# Projet Java - Gestion de Vente

## Informations Utiles
- [Explication du projet]()
  
## Description
L'application de gestion de vente de bijoux permet aux clients de naviguer efficacement dans un catalogue en ligne, d'ajouter des articles à leur panier et de finaliser leurs commandes. Elle offre également aux administrateurs des outils avancés pour gérer les produits, suivre les commandes et administrer les factures. Grâce à une interface intuitive et des fonctionnalités de filtrage dynamiques, l'expérience utilisateur est optimisée.

## Prototype

## Fonctionnalités
- **Catalogue de bijoux** : Affichage des bagues et colliers avec images, descriptions et stocks disponibles.
- **Panier d'achat** : Ajout, modification des quantités et suppression des articles avant validation.
- **Gestion des commandes **: Suivi des commandes avec statut évolutif : "En cours", "Validée", "Livrée".
- **Facturation** : Génération automatique de factures téléchargeables et envoyées par email.
- **Gestion des clients** : Ajout et modification des informations clients pour un suivi personnalisé.
- **Gestion des stocks **: Mise à jour des quantités après validation des commandes et ajustement en cas de retour client.
- **Filtrage avancé **: Certains filtres, comme le matériau, affichent dynamiquement les valeurs en fonction des bijoux disponibles dans la base de données.
- **Quick Search pour les administrateurs** : Recherche rapide par ID client, email, ID commande ou ID facture pour afficher les informations associées et effectuer des modifications en cas de besoin.

## Exécution du jeu

1. **Cloner le dépôt** 

2. **Télécharger le JAR et exécuter le jeu en version console**
    ```bash
    jar cvfe IAmTheHero.jar main.JeuMain -C bin .
    java -jar IAmTheHero.jar

   ```
   
3. **Base de données**
Un script SQL est conservé dans les ressources du projet pour initialiser les tables de la base de données au lancement de l’application "init.sql".

##Technologies Utilisées
- Java (JDK 18)
- Swing (Interface graphique)
- JDBC (Gestion de la base de données avec MySQL)
- MySQL (Stockage des données)
- Maven (Gestion des dépendances)
