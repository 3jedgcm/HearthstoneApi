<?php
// Classe de connexion à une base de données
// S'inspire du pattern singleton pour n'ouvrir qu'une seule connexion
// Utilisation :
//    $bd = MaBD::getInstance(); // $bd est un objet PDO
class MaBDSIG {

   static private $pdo = null; // Le singleton

   // Obenir le singleton
   static function getInstance() {
      if (self::$pdo == null) {
         $dsn = "mysql:host=h2mysql26;dbname=psqt_sig;charset=utf8";
         self::$pdo = new PDO($dsn, "psqt_root", "vf4MOKuXSrqWnbychD");
      }
      return self::$pdo;
   }
}
