<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/api/auto_load.php"; //Inclusion de le chargement de tout les fichiers

//Implémentation des constantes
const EXIT_CODE_OK = 0
const EXIT_CODE_ACTION_MISSING = 1
const EXIT_CODE_INCORRECT_PARAMETTER = 2
const EXIT_CODE_ERROR_API_EXTERNAL = 3

//Récuperation du premier POST Action
var $postAction = $_POST["ACTION"];
//Initialisation de la variable code erreur
var $errorCode = EXIT_CODE_OK;
// Test de la variable
if(isset($postAction))
{
  //Routage des Action
  switch($postAction)
  {
    case 'delete': //Supprimer une carte

    break;
    case 'random_card': //Action carte aléatoire

    break;
    case 'fusion': //Fusion d’une carte

    break;
    case 'get_money': //Obtenir l’argent d’un utilisateur

    break;
    case 'forge': // Forger une carte

    break;
    case 'exchange': //Echanger une carte

    break;
    case 'get_answer': //Obtenir une question

    break;

  }
}
else
{
  $errorCode = EXIT_CODE_ACTION_MISSING;
}




//{}
