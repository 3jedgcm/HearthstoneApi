<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

//////////////////////////////
// API HEARTHSTONE FUNCTION //
/////////////////////////////
var_dump($DAO);

$response = "http://api.hearthstonejson.com/v1/15590/frFR/cards.json";
//header('Content-type: application/json');
$parse = file_get_contents($response);

$decoded = json_decode($parse, true);
$imguri = "/";
//file_put_contents($imguri,);
//fopen($_SERVER["DOCUMENT_ROOT"] . "/img/test.txt", "r");
$dataCard[] = ["id"=>"null","nameCard"=>"null","description"=>"null","url"=>"null","type"=>"null","attack"=>"null","life"=>"null","life"=>"null"];
$DAO["Card"]->insert($dataCard);
/*
foreach($decoded as $v){

  $id = $v['id'];
  $card[] = ["id"=>"null","nameCard"=>"null","description"=>"null","url"=>"null","type"=>"null","attack"=>"null","life"=>"null","life"=>"null"];
  $card[] = ["id"=>$v['id'],"nameCard"=>$v['name'],"description"=>$v['text'],"url"=>$v['id'],"type"=>$v['rarity'],"attack"=>$v['attack'],"life"=>$v['health'],"life"=>$v['cost']];
  $DAO["Card"]->insert($card);

  //$url= "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/";


  //file_put_contents($_SERVER["DOCUMENT_ROOT"]."/img/".$id.".png", file_get_contents("https://art.hearthstonejson.com/v1/render/latest/frFR/512x/".$id.".png"));
  //echo "Fait pour : ". $v['id'];
  //echo '<br>';
}



//echo json_encode($decoded, JSON_PRETTY_PRINT || JSON_UNESCAPED_SLASHES);
?>
*/
