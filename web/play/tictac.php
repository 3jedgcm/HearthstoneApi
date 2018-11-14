<!doctype html>
<?php
//
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";
// Contrôleur

session_start();
$error = "";
$lesGamesDAO = new gameDAO(MaBD::getInstance());
$Game = $lesGamesDAO->getOne('Tic Tac Toe V1');

if(!isset($_SESSION["user"]) || $Game->getEtat() == 0)
{
  header('Location: /logout');
}
?>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <title>Titre de la page</title>
  <link rel="stylesheet" href="/src/css/tictac.css">
  <script src="/src/js/script_tic.js"></script>
</head>
<body>
  <h1> Tic Tac Toe </h1>
  <h2 id="turn"> Au tour de : </h2>
  <input class="button" type=button onClick="nouvellePartie()" value="NOUVELLE PARTIE" />
  <input id="size_x" class="textBox" type=text min=1 max="2" value="4" placeholder="Taille de la colonne"/>
  <input id="size_y" class="textBox" type=text min=1 max="2" value="4" placeholder="Taille de la ligne"/>
  <table id="idTable">



  </table>
  <input class="button" type=button onClick="returnLobby()" value="Retour au Lobby" />
</body>
</html>
