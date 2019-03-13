<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Content-type: application/json; charset=UTF-8');
header('Access-Control-Allow-Headers: X-Requested-With');

$errorCode = EXIT_CODE_OK;

$arrayUri = getArrayUri();
$typeReqHttp = $_SERVER['REQUEST_METHOD'];

if(!$errorCode)
{
  switch($typeReqHttp)
  {
    case REQUEST_DELETE:
    switch($arrayUri[1])
    {
      case ROUTE_INVENTORY:
        $resultat = deleteCardByUserid($DAO,$arrayUri[2],$arrayUri[3]);
        $errorCode = $resultat["error"];
      break;
      default:
        $errorCode = EXIT_CODE_INVALID_URI;
      break;
    }
    break;

    case REQUEST_POST:
    switch($arrayUri[1])
    {
      case ROUTE_INVENTORY:
      if($arrayUri[3] == SUB_ROUTE_EMPTY)
      {
        if($arrayUri[2] == SUB_ROUTE_EXCHANGE)
        {
          if(isset($_POST['cardUserOne']) && isset($_POST['cardUserTwo']) && isset($_POST['idUserTwo']) && isset($_POST['idUserOne']))
          {
            $resultat = exchangeCard($DAO,$_POST['idUserOne'],$_POST['idUserTwo'],$_POST['cardUserOne'],$_POST['cardUserTwo']);
            $errorCode = $resultat["error"];
          }
          else
          {
            $errorCode = EXIT_CODE_MISSING_PARAMETER;
          }
        }
        else
        {
          if(is_numeric($arrayUri[2]))
          {
            if(isset($_POST['idCard']))
            {
              $resultat = setCardInInventoryByUserId($DAO,$arrayUri[2],$_POST['idCard']);
              $errorCode = $resultat["error"];
            }
            else
            {
              $errorCode = EXIT_CODE_MISSING_PARAMETER;
            }
          }
          else
          {
            $errorCode = EXIT_CODE_INVALID_URI;
          }
        }
      }
      else
      {
        $errorCode = EXIT_CODE_TOO_LONG_URI;
      }
      break;
      case ROUTE_MONEY:
      if(isset($_POST['value']))
      {
        if($arrayUri[3] == SUB_ROUTE_EMPTY)
        {
          if($arrayUri[2] == SUB_ROUTE_EMPTY)
          {
            $resultat = setMoney($DAO,"",$_POST['value']);
            $errorCode = $resultat["error"];

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
        $errorCode = EXIT_CODE_UNKNOW_VALUE_PARAMETER;
      }
      break;
      case ROUTE_OTHER:
        switch($arrayUri[2])
        {
          case SUB_ROUTE_MELT:
          if(isset($_POST['idCard']))
          {
            $resultat = meltCard($DAO,$arrayUri[3],$_POST['idCard']);
            $errorCode = $resultat["error"];
            $result = $resultat["result"];
          }
          else
          {
            $errorCode = EXIT_CODE_MISSING_PARAMETER;
          }
          break;

          case SUB_ROUTE_CRAFTCARD:
          if(isset($_POST['idCardOne']) && isset($_POST['idCardTwo']) && isset($_POST['idCardThree']))
          {
            $resultat = craftOneCard($DAO,$arrayUri[3],$_POST['idCardOne'],$_POST['idCardTwo'],$_POST['idCardThree']);
            $errorCode = $resultat["error"];
            $result = $resultat["result"];
          }
          else
          {
            $errorCode = EXIT_CODE_MISSING_PARAMETER;
          }
          break;
          case SUB_ROUTE_QUIZZ:
          if(isset($_POST['answer']) && isset($_POST['question']))
          {
            $resultat = setAnswer($DAO,$arrayUri[3],$_POST['answer'],$_POST['question']);
            $result = $resultat["resultat"];

            $errorCode = $resultat["error"];
          }
          else
          {
            $errorCode = EXIT_CODE_MISSING_PARAMETER;
          }
          break;

          case SUB_ROUTE_ADD_CARD_SPECIAL:
          insertAllCardInDataBase($DAO);
          break;

          default:
          $errorCode = EXIT_CODE_INVALID_URI;
        }
      break;
      case ROUTE_CONNECT:
      if((isset($_POST['login']) && isset($_POST['pass']) && $arrayUri[2] == "" ) || (isset($_POST['key']) && ($arrayUri[2] == "facebook" ||  $arrayUri[2] == "google")))
      {
        $resultat = connect($DAO,$_POST['login'],$_POST['pass'],$_POST['key'],$arrayUri[2]);
        $errorCode = $resultat["error"];
        $connect = $resultat["connect"]?true:false;
        $user = $resultat["connect"]?$resultat["connect"]:json_decode('{}');
      }
      else
      {
        $errorCode = EXIT_CODE_UNKNOW_VALUE_PARAMETER;
      }
      break;
      case ROUTE_REGISTER:
      if((isset($_POST['login']) && isset($_POST['pass']) && $arrayUri[2] == "" ) || (isset($_POST['key']) && ($arrayUri[2] == "facebook" ||  $arrayUri[2] == "google")))
      {
        $resultat = register($DAO,$_POST['login'],$_POST['pass'],$_POST['key'],$arrayUri[2]);
        $errorCode = $resultat["error"];
        $connect = $resultat["connect"];
      }
      else
      {
        $errorCode = EXIT_CODE_UNKNOW_VALUE_PARAMETER;
      }
      break;

      default:
      $errorCode = EXIT_CODE_INVALID_URI;
    }
    break;

    case REQUEST_GET:
    switch($arrayUri[1])
    {
      case ROUTE_INVENTORY:
        if($arrayUri[3] == SUB_ROUTE_EMPTY)
        {
          if(is_numeric($arrayUri[2]))
          {
            $resultat = getInventory($DAO,$arrayUri[2]);
            $errorCode = $resultat["error"];
            $inventory = $resultat["inventory"];
            $user = $arrayUri[2];
          }
          else
          {
            $errorCode = EXIT_CODE_INVALID_URI;
          }
        }
        else
        {
          $errorCode = EXIT_CODE_TOO_LONG_URI;
        }
      break;

      case ROUTE_USER:
        if($arrayUri[3] == SUB_ROUTE_EMPTY)
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
      case ROUTE_MONEY:
        if($arrayUri[3] == SUB_ROUTE_EMPTY)
        {
          if($arrayUri[2] == SUB_ROUTE_EMPTY)
          {
            $resultat = getMoney($DAO,$arrayUri[2]);
            $errorCode = $resultat["error"];
            $money = $resultat["money"];

          }
          else if(is_numeric($arrayUri[2]))
          {
            $resultat = getMoney($DAO,$arrayUri[2]);
            $errorCode = $resultat["error"];
            $money = $resultat["money"];
            $user = $arrayUri[2];
          }
          else
          {
            $errorCode = EXIT_CODE_INVALID_URI;
          }
        }
        else
        {
          $errorCode = EXIT_CODE_TOO_LONG_URI;
        }
      break;
      case ROUTE_PARAM:
        if($arrayUri[2] == SUB_ROUTE_EMPTY)
        {
          $resultat = getParam($DAO);
          $errorCode = $resultat["error"];
          $param = $resultat["param"];
        }
        else
        {
          $errorCode = EXIT_CODE_TOO_LONG_URI;
        }
      break;
      case ROUTE_CARD:

        if(SUB_ROUTE_RANDOM == $arrayUri[2])
        {
          $resultat = getRandomCard($DAO);
          $errorCode = $resultat["error"];
          $card = $resultat["randomCard"];
        }
        else if(SUB_ROUTE_EMPTY == $arrayUri[2])
        {
          $resultat = getCard($DAO,"",$_GET["filter"],$_GET["value_filter"]);
          $errorCode = $resultat["error"];
          $card = $resultat["card"];
        }
        else
        {
          $resultat = getCard($DAO,$arrayUri[2],"","");
          $errorCode = $resultat["error"];
          $card = $resultat["card"];
        }
      break;
      case ROUTE_OTHER:
      if($arrayUri[3] == SUB_ROUTE_EMPTY)
      {
        if($arrayUri[2] == SUB_ROUTE_QUIZZ)
        {
          $resultat = getQuestion($DAO);
          $errorCode = $resultat["error"];
          $result = $resultat["result"];
        }
        else if($arrayUri[2] == SUB_ROUTE_ADD_CARD_SPECIAL)
        {
          insertAllCardInDataBase($DAO);
        }
        else
        {
          $errorCode = EXIT_CODE_INVALID_URI;
        }
      }
      else
      {
        $errorCode = EXIT_CODE_INVALID_URI;
      }
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

sendHttpRespond($arrayUri[1],$DAO,$arrayUri[2],$money,$inventory,$errorCode,$typeReqHttp,$param,$card,$user,$connect,$result);

function sendHttpRespond($firstArgR,$pDAO,$secondArgR,$pMoney,$pInventory,$pErrorCode,$typeReqHttp,$pParam,$pCard,$pUser,$pConnect,$pResult)
{
  header('Content-type: application/json');
  if($pErrorCode == EXIT_CODE_OK)
  {
    if($typeReqHttp == REQUEST_GET)
    {
      switch($firstArgR)
      {
        case ROUTE_INVENTORY:
        if($secondArgR != null)
        {
          $data = ["user"=>$pUser,"inventory"=>$pInventory];
        }
        else
        {
          $data = ["inventory"=>$pInventory];
        }
        break;
        case ROUTE_MONEY:
        if($secondArgR != null)
        {
          $data = ["user"=>$pUser,"money"=>$pMoney];
        }
        else
        {
          $data = ["money"=>$pMoney];
        }
        break;
        case ROUTE_PARAM:
        {
          $data = ["parameter"=>$pParam];
        }
        break;
        case ROUTE_CARD:
        {

          $data = $pCard;
        }
        break;
        case ROUTE_USER:
        {
          $data = $pUser;
        }
        break;
        case ROUTE_OTHER:
        {
          $data = ["question"=>$pResult];
        }
        break;

        default:
        $data = ["user"=>$pUser,"cards"=>$pInventory];
        break;
      }
      echo json_encode(["exitCode"=>$pErrorCode,"data"=>$data]);
    }
    else if($typeReqHttp == REQUEST_POST)
    {
      if($firstArgR == ROUTE_CONNECT)
      {
        echo json_encode(["exitCode"=>$pErrorCode,"connect"=>$pConnect,"user"=>$pUser]);
      }
      else if($firstArgR == ROUTE_OTHER)
      {
        echo json_encode(["exitCode"=>$pErrorCode,"result"=>$pResult]);
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
  else
  {
    echo json_encode(["exitCode"=>$pErrorCode]);
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



function debugVarDump($var)
{
  echo var_dump($var);
}
