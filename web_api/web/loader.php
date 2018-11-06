<?php
//Initialisation de chaque DAO
$CardDAO = new CardDAO(DB::getInstance());
$ParamDAO = new ParamDAO(DB::getInstance());
$UserDAO = new UserDAO(DB::getInstance());
$DAO = ["Card"=>$CardDAO,"Param"=>$ParamDAO,"User"=>$UserDAO];
