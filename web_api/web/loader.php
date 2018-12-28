<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

//Initialisation de chaque DAO

$CardAPI = new CardAPI(HEARTHSTONE_API_URI);
$ParamDAO = new ParamDAO(DB::getInstance());
$UserDAO = new UserDAO(DB::getInstance());
$InventoryDAO = new InventoryDAO(DB::getInstance());
$DAO = ["Card"=>$CardAPI,"Param"=>$ParamDAO,"User"=>$UserDAO,"Inventory"=>$InventoryDAO];
