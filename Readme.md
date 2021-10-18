# Telephony WS

## Qu'est-ce que c'est
Un exemple de projet pour créer un API Web Rest en utilisant Java, Jetty et Jersey seulement.

## Contexte
Voici une structure de projet qui n'utilise pas de plate-forme d'injection de dépendance et qui explicite
 la création de l'application et de ses composantes. C'est une application petite, mais complète, qui sera une base de 
 projet pour un travail à l'Université. La couverture des tests est minimale pour encourager les étudiants à explorer. 
L'exercice est de comprendre comment fonctionne le tout pour mieux bâtir une application en utilisant 
 de l'injection de dépendance.

##  Comment l'utiliser
* Avec Java 13 et Maven d'installé et la variable JAVA_HOME configurée; 
  * Dans un terminal, exécutez start.sh si vous êtes sur Linux / OSX
  * Dans un terminal, exécutez start.bat si vous êtes sur Windows
  * Dans un IDE, exécutez la classe `TelephonyWsMain` en tant que "Java Application"
* Une fois démarré, vous trouverez les données aux URLs suivantes:
  * http://localhost:8080/api/telephony/contacts
  * http://localhost:8080/api/telephony/calllogs