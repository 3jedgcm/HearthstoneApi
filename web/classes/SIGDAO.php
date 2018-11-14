<?php

// Classe pour l'accès à la table produit
class SIGDAO extends DAO {

  public function getAll($table)
  {
    $res = array();
    if($table == 'GEO_ARC')
    $stmt = $this->pdo->prepare("SELECT * FROM GEO_ARC ");
    else if($table == 'GEO_POINT')
    $stmt = $this->pdo->prepare("SELECT * FROM GEO_POINT ");
    else if ($table == 'GEO_VERSION')
    $stmt = $this->pdo->prepare("SELECT * FROM 'GEO_VERSION ");


    $stmt->execute();
    foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
        $res[] = $row;


    return $res;
  }

}
