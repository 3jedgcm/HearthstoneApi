<?php 

require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";
session_start();
$user = $_SESSION["user"];

if(isset($_GET["action"]))
{
    $message = $_GET["message"];
    $auteur = $user->getLogin();
    $action = intval($_GET["action"]); 
    $num = $_GET["num"];

}else
{
    if(isset($_POST["action"]))
    {
        $message = $_POST["message"];
        $auteur = $user->getLogin();
        $action = $_POST["action"]; 
        $num = $_POST["num"];
    }
    else
    {
        $message = "MESSAGE DEMO POUR VISITE";
        $auteur = "DEMO";
        $action = -1;
        $num = 0;
    }
}


switch($action)
{
    case 0: //SEND MESSAGE INTO BDD
        setMessage($message,$auteur);
        echo json_encode("ENVOI MESSAGE");
        break;

    case 1: //DROP MESSAGE
        dropMessage();
        echo json_encode("DROP TABLE");
        break;

    case 2: //GET MESSAGE
        $result = getMessage($num);
        echo json_encode($result);
        break;

    case -1: // DEMO
        echo "<h1> DEMO ACTIVATE </h1> </br>";
        echo json_encode(getMessage(0));
        break;

    default:
        break;  
}


function setMessage($message,$login)
{
    $pdo = MaBD::getInstance();
    $stmt = $pdo->prepare("INSERT INTO `api_tchat` (`id`, `message`, `auteur`, `date`) VALUES (NULL, ?, ?, ?);");
    $stmt->execute(array(htmlentities($message),$login,date('l \t\h\e jS')));   
}

function dropMessage()
{
    $pdo = MaBD::getInstance();
    $stmt = $pdo->prepare("TRUNCATE TABLE `api_tchat`");
    $stmt->execute();
}

function getMessage($numCurrentMessage)
{
    $pdo = MaBD::getInstance();
    $stmt = $pdo->prepare("SELECT * FROM `api_tchat` WHERE id > ?");
    $stmt->execute(array($numCurrentMessage));
    $res = $stmt->fetchAll();
    return $res;
}

?>
