<?php
// Classe abstraite pour l'accès aux données d'une base
abstract class DAO {
    const UNKNOWN_ID = -1; // Identifiant non déterminé
    protected $pdo; // Objet pdo pour l'accès à la table

    // Le constructeur reçoit l'objet PDO contenant la connexion
    public function __construct(PDO $connector)
    {
      $this->pdo = $connector;
    }

}
