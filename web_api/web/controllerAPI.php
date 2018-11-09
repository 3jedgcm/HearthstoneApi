<?php

//////////////////////////////
// API HEARTHSTONE FUNCTION //
/////////////////////////////

$response = "http://api.hearthstonejson.com/v1/15590/frFR/cards.json";
//header('Content-type: application/json');
$parse = file_get_contents($response);

$decoded = json_decode($parse, true);
$imguri = "/";
//file_put_contents($imguri,);
//fopen($_SERVER["DOCUMENT_ROOT"] . "/img/test.txt", "r");

foreach($decoded as $v){

  $id = $v['id'];
  $url= "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/";

  //file_put_contents($_SERVER["DOCUMENT_ROOT"]."/img/".$id.".png", file_get_contents("https://art.hearthstonejson.com/v1/render/latest/frFR/512x/".$id.".png"));
  echo "Fait pour : ". $v['id'];
  echo '<br>';
}



//echo json_encode($decoded, JSON_PRETTY_PRINT || JSON_UNESCAPED_SLASHES);
?>
