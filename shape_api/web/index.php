<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

header('Access-Control-Allow-Origin: https://coopuniverse.fr');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Content-type: application/json; charset=UTF-8');
header('Access-Control-Allow-Headers: X-Requested-With');

$arrayUri = getArrayUri();
$typeReqHttp = $_SERVER['REQUEST_METHOD'];
$error = "null";
$isDead = false;
$isFinish = false;
header('Content-type: application/json');

function testPost($pTypeReqHttp)
{
  if($pTypeReqHttp == "POST")
  return true;
  return false;
}




if(testPost($typeReqHttp) && isset($_POST["idKey"]) && isset($_POST["idAccount"]))
{
  $idKey = $_POST["idKey"];
  $idAccount = $_POST["idAccount"];
  $displayCharacter = $CellDAO->getCharacterById($idAccount);


  if($displayCharacter["life"] <= 0)
  {
    $isFinish = true;
    $CharacterDAO->updateCharacterLife($idAccount,100);
    $CharacterDAO->updateCharacterLocation($idAccount,ID_SPAWN_CELL);
    $displayCharacter = $CellDAO->getCharacterById($idAccount);
  }

//  if(isset($_POST["idCell"])  && !$isFinish)
  if(isset($_POST["idCell"]))
  {
    $idCell = $_POST["idCell"];
    if($displayCharacter) //Si le personnage existe
    {
      $linkUse = $CellDAO->testLink($displayCharacter["location"],$_POST["idCell"]);
      if($linkUse)
      {
        $deltaLife = intval($linkUse["life_cost"]);
        $displayCell = $CellDAO->getCellById($idCell);
        $displayCellLink = $CellDAO->getLinkById($_POST["idCell"]);
        $contentCost = $CellDAO->getContentById($displayCell["content"]);
        $deltaLife = $deltaLife + intval($contentCost["life_result"]);
        $background_src = $contentCost["background_src"];
        $addShield = intval($contentCost["shield_result"]);
        $currentShield = intval($displayCharacter["shield"]);

        $creditAccount  = $CharacterDAO->getCredit($idAccount);
        $addCredit = intval($contentCost["credit_result"]);
        $currentCredit = intval($creditAccount["credit"]) + $addCredit;
        $CharacterDAO->updateCharacterCredit($idAccount,$currentCredit);



        $currentShield = $currentShield + $addShield;
        if($currentShield < 0)
        $currentShield = 0;
        $CharacterDAO->updateCharacterShield($idAccount,$currentShield);


        if($currentShield > 0 && $deltaLife < 0) //Choix ou utilisation du bouclier ou utilisation de la vie
            {
              $newShield = $currentShield + ($deltaLife*2);
              if($newShield < 0)
              $newShield = 0;
              $CharacterDAO->updateCharacterShield($idAccount,$newShield);
            }
            else
            {

              $newLife = intval($displayCharacter["life"]) + $deltaLife;
              if($newLife > 100)
              {
                $newLife = 100;
              }
              else if($newLife < 0)
              {
                $isDead = true;
                $newLife = 0;
              }
              $CharacterDAO->updateCharacterLife($idAccount,$newLife);
            }

        $CharacterDAO->updateCharacterLocation($idAccount,$idCell);
        $displayCharacter = $CellDAO->getCharacterById($idAccount);
      }
      else
      {
        $displayCell = $CellDAO->getCellById($idCell);
        $displayCellLink = $CellDAO->getLinkById($displayCharacter["location"]);
        $error = "Le lien ou la cellule n'existe pas";
      }
    }
    else
    {
      $displayCell = $CellDAO->getCellById(ID_SPAWN_CELL);
      var_dump($displayCell);
      $CharacterDAO->spawnCharacter($idAccount);
    }
    $credit = $CharacterDAO->getCredit($idAccount);
  }
  else
  {
    $displayCharacter = $CellDAO->getCharacterById($idAccount);
    $displayCell = $CellDAO->getCellById($displayCharacter["location"]);
    $displayCellLink = $CellDAO->getLinkById($displayCharacter["location"]);
    $contentCost = $CellDAO->getContentById($displayCell["content"]);
    $credit = $CharacterDAO->getCredit($idAccount);
    $background_src = $contentCost["background_src"];
  }

  if($error == "null" && $displayCharacter)
  echo json_encode(["result"=>["cell"=>["content"=>$displayCell,"link"=>$displayCellLink,"src"=>$background_src],"character"=>$displayCharacter,"credit"=>$credit,"isDead"=>$isDead]]);
  else if($displayCharacter)
  echo json_encode(["error"=>$error,"legalLink"=>$displayCellLink]);
  else
  echo json_encode(["error"=>"Le compte n'existe pas"]);
  ///////////////////////////////////////////////
  //Verification de l'intégrité du joueur avec la clé
  //Récuperation de la cellule courrante du joueur
  //Verficiation de la présence d'un la cellule adjacente
  //Ajout des modificateurs de la nouvelle cellule
  //Modification de la position du joueur
  //Renvoi de la cellule et l'etat des joueurs
  /////////////////////////////////////////////

}
else
{
  echo json_encode(["ErrorException"=>"InvalidUsage"]);
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
