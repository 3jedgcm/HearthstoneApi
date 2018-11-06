<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion de le chargement de tout les fichiers


//Initialisation de la variable code erreur
$errorCode = EXIT_CODE_OK;

//Initialisation de chaque DAO
$CardDAO = new CardDAO(DB::getInstance());
$ParamDAO = new ParamDAO(DB::getInstance());
$UserDAO = new UserDAO(DB::getInstance());
$DAO = ["Card"=>$CardDAO,"Param"=>$ParamDAO,"User"=>$UserDAO];

$arrayUri = getArrayUri();
// Test de la variable
$errorCode = checkIdUser($DAO,$arrayUri);
$returnData = getTypeOfRequestHttp();
$errorCode = $returnData["exitCode"];
$typeReqHttp = $returnData["type"];
if(!$errorCode)
{
  switch($typeReqHttp)
  {
    case'DELETE':

    break;
    case'POST':
    switch($arrayUri[1])
    {
      case'user':

      break;
      case'money':
      if(isset($_POST['value']))
      {
        if($arrayUri[3] == "")
        {
          if($arrayUri[2] == "")
          {
            setMoney($DAO,"",$_POST['value']);
          }
          else
          {
            setMoney($DAO,$arrayUri[2],$_POST['value']);
          }
        }
        else
        {
          $errorCode = EXIT_CODE_TOO_LONG_URI;
        }
      }
      else
      {
        $errorCode = EXIT_CODE_UNKNOW_VALUE_PARAMETTER;
      }

      break;
      case'parametter':

      break;
      default:
      $errorCode = EXIT_CODE_INVALID_URI;
    }
    break;
    case'GET':
      switch($arrayUri[1])
      {
        case'user':
          if(empty($arrayUri[2]))
          {
            //GET ALL USER
          }
          else
          {
            //GET ONE USER
          }
        break;
        case'money':
          if($arrayUri[3] == "")
          {
            $resultat = getMoney($DAO,$arrayUri[2]);
            $errorCode = $resultat["error"];
            $money = $resultat["money"];
          }
          else
          {
            $errorCode = EXIT_CODE_TOO_LONG_URI;
          }
        break;
        case'parametter':
        //Get All Parametter
        break;
        default:
        $errorCode = EXIT_CODE_INVALID_URI;
      }
    break;
    default:
    $errorCode = EXIT_CODE_INCORRECT_TYPE_REQUEST;
    break;
  }

/*
    //Routage des actions
    switch($arrayUri[1])
    {
      case 'delete': //Supprimer une carte
        $errorCode = deleteOneCard($DAO,$arrayUri[2],$_POST["cards"]);
        $inventory = getInventory($DAO,$arrayUri[2]);
      break;
      case 'random_card': //Action carte aléatoire
        $errorCode = randomCard($DAO,$arrayUri[2]);
        $inventory = getInventory($DAO,$arrayUri[2]);
      break;
      case 'fusion': //Fusion d’une carte
        $errorCode = fusion($DAO,$arrayUri[2],$_POST["cards"]);
        $inventory = getInventory($DAO,$arrayUri[2]);
      break;
      case 'get_poney': //Obtenir l’argent d’un utilisateur
      case 'money': //Obtenir l’argent d’un utilisateur

      break;
      case 'forge': // Forger une carte
        $errorCode = forge($DAO,$arrayUri[2],$_POST["cards"]);
        $inventory = getInventory($DAO,$arrayUri[2]);
      break;
      case 'exchange': //Echanger une carte
        $errorCode = exchange($DAO,$arrayUri[2],$_POST["cards"],$_POST["second_id_user"],$_POST["second_cards"]);
        $inventory = getInventory($DAO,$arrayUri[2]);
      break;
      case 'get_answer': //Obtenir une question
        $errorCode = getAnswer();
        $inventory = getInventory($DAO,$arrayUri[2]);
      break;
      default: //Si aucune action
        $errorCode = EXIT_CODE_UNKNOW_ACTION;
      break;
    }
*/

  $newInventory = getInventory($DAO,$arrayUri[2]);
}
sendHttpRespond($arrayUri[1],$DAO,$arrayUri[2],$money,$inventory,$errorCode,$typeReqHttp);






function sendHttpRespond($pRequest,$pDAO,$pUser,$pMoney,$pCards,$pErrorCode,$typeReqHttp)
{
 header('Content-type: application/json');
  if($pErrorCode == EXIT_CODE_OK)
  {
    if($typeReqHttp == "GET")
    {
      switch($pRequest)
      {
        case'money':
          if($pUser != null)
          {
            $data = ["user"=>$pUser,"money"=>$pMoney];
          }
          else
          {
            $data = ["money"=>$pMoney];
          }
        break;

        default:
          $data = ["user"=>$pUser,"cards"=>$cards];
        break;
      }
      echo json_encode(["exitCode"=>$pErrorCode,"data"=>$data]);
    }
    else
    {
      echo json_encode(["exitCode"=>$pErrorCode]);
    }
  }
  else
  {
    echo json_encode(["exitCode"=>$pErrorCode]);
  }


}

function getInventory($pDAO,$idUser)
{
  return $pDAO["Card"]->getAll($idUser);
}

function checkIdUser($pDAO,$arrayUri)
{
  $postIdUser = $arrayUri[2];
  if(isset($postIdUser))
  {
    if($pDAO["User"]->checkOneById($idUser))//Checking de l'id user coté serveur
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
  if(isset($cards) && $cards != null)
  {
    $error = EXIT_CODE_OK;
    foreach($cards as $card)
    {
      if($pDAO["Card"]->checkExist($card,$idUser))
      {
        $pDAO["Card"]->delete($card,$idUser);
      }
      else
      {
        $error = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
      }
    }
    return $error;
  }
  else
  {
      return EXIT_CODE_CARDS_MISSING_IN_PARAMETTER;
  }
}

function randomCard($pDAO,$idUser)
{
  $card = getRandomCard();
  if(isset($card))
  {
    if($pDAO["Card"]->insert($card,$idUser))
    {
      return EXIT_CODE_OK;
    }
    else
    {
      return EXIT_CODE_ERROR_INSERTION_IN_DB;
    }
  }
  else
  {
    return EXIT_CODE_RANDOM_CARD_IS_NULL;
  }
}

function fusion($pDAO,$idUser,$cards)
{
  return EXIT_CODE_OK;
}

function getMoney($pDAO,$idUser)
{
  if($idUser == "")
  {
    $resultat["money"] = $pDAO["User"]->getAllMoney();
  }
  else
  {
    $resultat["money"] = $pDAO["User"]->getMoney($idUser);
  }
  if($resultat["money"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_MONEY_READ;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;

}

function setMoney($pDAO,$idUser,$newValueMoney)
{
  if($idUser == "")
  {
    $resultat["money"] = $pDAO["User"]->setAllMoney(intval($newValueMoney));
  }
  else
  {
    $resultat["money"] = $pDAO["User"]->setMoney($idUser,intval($newValueMoney));
  }
  if($resultat["money"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_MONEY_SETTER;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;

}

function forge($pDAO,$idUser,$cards)
{
  return EXIT_CODE_OK;
}

function exchange($pDAO,$idUser,$idUser_secondary,$cards,$cards_secondary)
{
  return EXIT_CODE_OK;
}

function getAnswer($pDAO)
{
  return EXIT_CODE_OK;
  //Voir API Goub
}

function getRandomCard()
{
  return EXIT_CODE_OK;
  //Voir API Pokemon
}

function getArrayUri()
{
    $basepath = implode('/', array_slice(explode('/', $_SERVER['SCRIPT_NAME']), 0, -1)) . '/';
    $uri = substr($_SERVER['REQUEST_URI'], strlen($basepath));
    if (strstr($uri, '?')) $uri = substr($uri, 0, strpos($uri, '?'));
    $uri = '/' . trim($uri, '/');
    $routes = array();
    $routes = explode('/', $uri);
    return $routes;
}

function getTypeOfRequestHttp()
{
  $method = $_SERVER['REQUEST_METHOD'];
  switch ($method) {
    case 'POST':
    return ["exitCode" => EXIT_CODE_OK,"type" => "POST"];
    break;

    case 'DELETE':
    return ["exitCode" => EXIT_CODE_OK,"type" => "DELETE"];
    break;

    case 'GET':
    return ["exitCode" => EXIT_CODE_OK,"type" => "GET"];
    break;

    default:
    return ["exitCode" => EXIT_CODE_INCORRECT_TYPE_REQUEST,"type" => ""];
    break;
  }
}
