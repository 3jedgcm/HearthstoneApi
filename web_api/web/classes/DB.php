<?php
// Classe de connexion à une base de données
// Design pattern singleton pour ouvrir qu'une seule connexion
class DB {

   static private $pdo = null; // Le singleton

   // Obenir le singleton
   static function getInstance() {
      if (self::$pdo == null) {
         $dsn = "mysql:host=psqt.myd.infomaniak.com;dbname=psqt_iem;charset=utf8";
         self::$pdo = new PDO($dsn, "psqt_iem", "odSNV7j34Ex0");
      }
      return self::$pdo;
   }
}
