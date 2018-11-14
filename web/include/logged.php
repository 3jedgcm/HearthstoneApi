<?php
/*include this to check if user is logged*/
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";

session_start();

$lesUtilisateurDAO = new utilisateurDAO(MaBD::getInstance());

if (!isset($_SESSION["user"])) {
    if(!isset($logPage) || !$logPage == ISLOGPAGE)
    {
        $_SESSION["url"] = $_SERVER['PHP_SELF'];
        header('Location: /');
    }
}
else {
    $user = $_SESSION["user"];
    $_SESSION["user"] = $lesUtilisateurDAO->rafraichir($user->getLogin());
    $user = $_SESSION["user"];
}
?>