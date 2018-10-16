<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/api/auto_load.php"; //Inclusion de le chargement de tout les fichiers

//Récuperation du premier POST Action
$postAction = $_POST["action"];

//Initialisation de la variable code erreur
$errorCode = EXIT_CODE_OK;
// Test de la variable
$errorCode = checkIdUser();

if(!$errorCode)
  if(isset($postAction))
  {
    //Routage des actions
    switch($postAction)
    {
      case 'delete': //Supprimer une carte
      $resultat = deleteOneCard();
      break;
      case 'random_card': //Action carte aléatoire
      $resultat = randomCard();
      break;
      case 'fusion': //Fusion d’une carte
      $resultat = fusion();
      break;
      case 'get_money': //Obtenir l’argent d’un utilisateur
      $resultat = getMoney();
      break;
      case 'forge': // Forger une carte
      $resultat = forge();
      break;
      case 'exchange': //Echanger une carte
      $resultat = exchange();
      break;
      case 'get_answer': //Obtenir une question

      break;
      default: //Si aucune action
      $errorCode = EXIT_CODE_UNKNOW_ACTION;

    }
  }
  else
  {
    $errorCode = EXIT_CODE_ACTION_MISSING;
  }

function checkIdUser()
{
  $postIdUser = $_POST["id_user"];
  if(isset($postIdUser))
  {
    if(true)//Checking de l'id user coté serveur
    {
      $errorCode = EXIT_CODE_OK;
    }
    else
    {
      $errorCode = EXIT_CODE_INCORRECT_ID_USER;
    }
  }
  else
  {
    $errorCode = EXIT_CODE_UNKNOW_ID_USER;
  }
  return $errorCode;
}


function deleteOneCard($idUser,$cards)
{

}

function randomCard($idUser)
{

}

function fusion($idUser,$cards)
{

}

function getMoney($idUser)
{

}

function forge($idUser,$cards)
{

}

function exchange($idUser,$idUser_secondary,$cards,$cards_secondary)
{

}

function getAnswer()
{

}
