<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/api/auto_load.php"; //Inclusion de le chargement de tout les fichiers

//Récuperation du premier POST Action
$postAction = $_POST["action"];

//Initialisation de la variable code erreur
$errorCode = EXIT_CODE_OK;
// Test de la variable
$errorCode = checkIdUser();

//Initialisation de chaque DAO
$CardDAO = new CardDAO(DB::getInstance());
$ParamDAO = new ParamDAO(DB::getInstance());
$UserDAO = new UserDAO(DB::getInstance());


if(!$errorCode)
{
  if(isset($postAction))
  {
    //Routage des actions
    switch($postAction)
    {
      case 'delete': //Supprimer une carte
      $resultat = deleteOneCard(null,null);
      break;
      case 'random_card': //Action carte aléatoire
      $resultat = randomCard(null);
      break;
      case 'fusion': //Fusion d’une carte
      $resultat = fusion(null,null);
      break;
      case 'get_money': //Obtenir l’argent d’un utilisateur
      $resultat = getMoney(null);
      break;
      case 'forge': // Forger une carte
      $resultat = forge(null,null);
      break;
      case 'exchange': //Echanger une carte
      $resultat = exchange(null,null,null,null);
      break;
      case 'get_answer': //Obtenir une question
      $resultat = getAnswer();
      break;
      default: //Si aucune action
      $errorCode = EXIT_CODE_UNKNOW_ACTION;
      break;
    }
  }
  else
  {
    $errorCode = EXIT_CODE_ACTION_MISSING;
  }
}

echo json_encode(["test",$errorCode]);
function checkIdUser()
{
  $postIdUser = $_POST["id_user"];
  if(isset($postIdUser))
  {
    echo($UserDAO->toString());
    if($UserDAO->checkOneById())//Checking de l'id user coté serveur
    {
      return EXIT_CODE_OK;
    }
    else
    {
      return EXIT_CODE_INCORRECT_ID_USER;
    }
  }
  else
  {
    return EXIT_CODE_UNKNOW_ID_USER;
  }
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
