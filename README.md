# Meta.gg

Ce projet est réalisé par Andrea PRUVOST LAMY et Naïman MPOGOLO, étudiants en L2-I dans le groupe TD-B à Cergy-Paris Université. Ce projet met en application les connaissances acquises dans les UE "Programmation orientée Objet".

## Introduction
Ce projet vise à la création d'une application bureautique orientée sur le traitement d'image. L'intégralité du code est en Java.
Cette application offre les options suivantes :
* L'extraction des métadonnées d'un fichier ODT, ODP, ODG ou ODS
* L'exploration complète d'un répertoire contenant des fichiers ODT, ODP, ODG ou ODS
* Le renseignement de métadonnées manquantes ou leur modification

## Exigence
> Cette application est compilée pour fonctionner avec Java 11 ou supérieur. Pour installer Java, veuillez-vous rendre [ici](https://www.oracle.com/java/technologies/downloads/).

## Utilisation de la version en ligne de commande
La version en ligne de commande doit être utilisée dans le terminal. Pour l'appeler, il suffit de naviguer vers le répertoire où se situe le fichier `cli.jar`. Une fois dans le bon répertoire, l'appel se fait avec la commande suivante :  `java -jar cli.jar`

Vous pouvez ensuite placer les options suivantes :
* `-h` ou `--help` : affiche l'aide de l'application (version cli)
* `-d <chemin d'un répertoire>` : affiche les métadonnées des fichiers présents dans le répertoire
* `-f <chemin d'accès d'une image>` : affiche les métadonnées du fichier renseigné en paramètre.

## Utilisation de la version graphique

La version graphique de l'application offre les mêmes possibilités. Pour utiliser la version graphique, il est nécessaire de naviguer vers le répertoire ou est situé le fichier `gui.jar`. L'appel de ce dernier se fait par la commande : `java -jar gui.jar`
