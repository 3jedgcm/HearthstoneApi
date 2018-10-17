<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/api/auto_load.php"; //Inclusion de le chargement de tout les fichiers

//Récuperation du premier POST Action
$postAction = $_POST["action"];

//Initialisation de la variable code erreur
$errorCode = EXIT_CODE_OK;

//Initialisation de chaque DAO
$CardDAO = new CardDAO(DB::getInstance());
$ParamDAO = new ParamDAO(DB::getInstance());
$UserDAO = new UserDAO(DB::getInstance());
$DAO = ["Card"=>$CardDAO,"Param"=>$ParamDAO,"User"=>$UserDAO];

// Test de la variable
$errorCode = checkIdUser($DAO);


if(!$errorCode)
{
  if(isset($postAction))
  {
    //Routage des actions
    switch($postAction)
    {
      case 'delete': //Supprimer une carte
      $errorCode = deleteOneCard($DAO,$_POST["id_user"],$_POST["cards"]);
      break;
      case 'random_card': //Action carte aléatoire
      $errorCode = randomCard($DAO,$_POST["id_user"]);
      break;
      case 'fusion': //Fusion d’une carte
      $errorCode = fusion($DAO,$_POST["id_user"],$_POST["cards"]);
      break;
      case 'get_money': //Obtenir l’argent d’un utilisateur
      $errorCode = getMoney($DAO,$_POST["id_user"]);
      break;
      case 'forge': // Forger une carte
      $errorCode = forge($DAO,$_POST["id_user"],$_POST["cards"]);
      break;
      case 'exchange': //Echanger une carte
      $errorCode = exchange($DAO,$_POST["id_user"],$_POST["cards"],$_POST["second_id_user"],$_POST["second_cards"]);
      break;
      case 'get_answer': //Obtenir une question
      $errorCode = getAnswer();
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

echo json_encode(["Code Retour",$errorCode]);
function checkIdUser($pDAO)
{
  $postIdUser = $_POST["id_user"];
  if(isset($postIdUser))
  {
    if($pDAO["User"]->checkOneById(null))//Checking de l'id user coté serveur
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

function deleteOneCard($pDAO,$idUser,$cards)
{
  if(isset($cards))
  {



  }
  else
  {
      return EXIT_CODE_CARDS_MISSING;
  }
}

function randomCard($pDAO,$idUser)
{

}

function fusion($pDAO,$idUser,$cards)
{

}

function getMoney($pDAO,$idUser)
{

}

function forge($pDAO,$idUser,$cards)
{

}

function exchange($pDAO,$idUser,$idUser_secondary,$cards,$cards_secondary)
{

}

function getAnswer($pDAO)
{

}
