<?php
// Classe de connexion à une base de données
// Design pattern singleton pour ouvrir qu'une seule connexion
class MaBD {

   static private $pdo = null; // Le singleton

   // Obenir le singleton
   static function getInstance() {
      if (self::$pdo == null) {
         $dsn = "mysql:host=psqt.myd.infomaniak.com;dbname=psqt_iem;charset=utf8";
         self::$pdo = new PDO($dsn, "psqt_iem", "FFkYfb4Wu0Do");
      }
      return self::$pdo;
   }
}
