<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

//Initialisation de chaque DAO

$CardDAO = new CardDAO(DB::getInstance());
$ParamDAO = new ParamDAO(DB::getInstance());
$UserDAO = new UserDAO(DB::getInstance());
$InventoryDAO = new InventoryDAO(DB::getInstance());
$DAO = ["Card"=>$CardDAO,"Param"=>$ParamDAO,"User"=>$UserDAO,"Inventory"=>$InventoryDAO];
