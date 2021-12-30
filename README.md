# Projet de CPOO5: Générateur de fractales

## comment compiler le projet
*(depuis la racine)*

**Avec Gradle :**
* ``./gradlew build`` pour compiler
* ``./gradlew run`` pour executer

**Version simplifiée :**
* ``./run`` pour compiler et executer directement

## Utilisation de l'interface graphique

L'interface permet d'effectuer facilement plusieurs manipulations sur les fractales. Chaque fractale générée est visualisable sur la "preview" et sauvegardée à la bonne résolution dans un fichier de nom ``valeurRéel_valeurImaginaire.png``

<img src="ressources/interface_menu.png"/>

### Notre interface se décompose en 5 parties
----
**1). Le menu dit principal :**
permet de définir les paramètres pour générer sa fractale
* l'expression complexe
* le pas (gap)
* les dimensions du plan

Afin de ne pas avoir à obligatoirement remplir tous les paramètres du menu, les champs sont initialisés au lancement. De plus, le menu déroulant **Fractal** permet de charger tous les champs avec la fractale sélectionnée.

Le bouton **generate fractal** permet finalement de générer la fractale à partir des valeur données plus haut.

Une checkbox **disable feedback** permet de masquer l'affichage sur la "preview".

**2). La partie zoom :** est là pour faire varier le niveau de zoom sur la fractale. Pour se faire, on joue sur les **dimensions** du plan et la valeur du **gap** par rapport au coefficient de zoom.

**3). Multiple view generator :** permet de créer le nombre spécifié de fractales avec, entre chaque image, la valeur de zoom donnée. Toutes les images sont sauvegardées au format **.png** dans un répertoire du nom de l'expression complexe de la fractale. 

Un bouton sous le label **Screen image** permet de sauvegarder dans un fichier la fractale affichée en "preview". L'intérêt est d'éviter que la fractale en preview soit perdue à la modification suivante. 

**4). Shifting :** permet de se déplacer grâce à des flêches directionnelles en fonction d'un coefficient incrémental modifiable.

**5). La preview :** affichant la dernière fractale générée avec ses valeurs.

---

## Considérations techniques

Quelques remarques sur la justification de l'implémentation de certaines fonctionalités.

### Création d'une fractale

Afin de générer une fractale de manière efficace, nous avons utilisé un système de **threads**. L'image à dessiner est décomposée en un nombre de parties égale au nombre de coeur du processeur. Chaque partie est écrite simultanément par son thread.

**Par exemple :** si le processeur contient 4 coeurs, le dessin de la fractale sera décomposé en 4 threads qui écriront en simultané. 

### Menu déroulant

Dans l'interface graphique, un menu déroulant permer de lister les différentes fractales disponibles à charger. Les paramètres de chaque fractale sont stockés dans l'enum ``JuliaFractalList``.

---

## Compléter
* comment lancer les deux versions du projet (compiler/exécuter déjà expliqué)
* exposer les points forts du projet : nombre threads par rapport au nombre de coeurs, interface et fonctionnalités (déplacement, zoom, enum), data model...
* les sources (si besoin) -> pas d'idée particulière

Soutenance du projet en janvier 2022.
