<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

$errorCode = EXIT_CODE_OK;

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
    switch($arrayUri[1])
    {
      default:
      $errorCode = EXIT_CODE_INVALID_URI;
      break;
    }
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
          if($arrayUri[3] == "")
          {
            $resultat = getUser($DAO,$arrayUri[2]);
            $errorCode = $resultat["error"];
            $user = $resultat["user"];
          }
          else
          {
            $errorCode = EXIT_CODE_TOO_LONG_URI;
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
