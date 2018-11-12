<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php";
// Classe de connexion à une base de données
// Design pattern singleton pour ouvrir qu'une seule connexion
class DB {

   static private $pdo = null; // Le singleton

   // Obenir le singleton
   static function getInstance()
   {
      if (self::$pdo == null)
      {
         $dsn = "mysql:host=".DB_HOST.";dbname=".DB_NAME.";charset=utf8";
         self::$pdo = new PDO($dsn,"".DB_USER."","".DB_PASS."");
      }
      return self::$pdo;
   }
}
