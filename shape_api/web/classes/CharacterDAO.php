<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers
class CharacterDAO extends DAO {

  public function getOne()
  {
    $stmt = $this->pdo->prepare("SELECT * FROM shp_character WHERE id_account = ?");
    $stmt->execute(array($id_card));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return ["card"=>$row];
  }

  public function insert()
  {

  }

  public function update()
  {

  }

  public function delete()
  {

  }

  public function updateCharacterLife($pIdAccount,$pValue)
  {
    $stmt = $this->pdo->prepare("UPDATE shp_character SET life = ? WHERE id_account=?");
    $stmt->execute(array($pValue,$pIdAccount));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return $row;
  }

  public function updateCharacterShield($pIdAccount,$pValue)
  {
    $stmt = $this->pdo->prepare("UPDATE shp_character SET shield = ? WHERE id_account=?");
    $stmt->execute(array($pValue,$pIdAccount));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return $row;
  }

  public function updateCharacterCredit($pIdAccount,$pValue)
  {
    $stmt = $this->pdo->prepare("UPDATE adm_user SET credit = ? WHERE id_user=?");
    $stmt->execute(array(strval($pValue),$pIdAccount));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return $row;
  }

  public function updateCharacterLocation($pIdAccount,$pValue)
  {
    $stmt = $this->pdo->prepare("UPDATE shp_character SET location = ? WHERE id_account=?");
    $stmt->execute(array($pValue,$pIdAccount));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return $row;
  }

  public function spawnCharacter($pIdAccount)
  {
    $stmt = $this->pdo->prepare("INSERT INTO shp_character(id_account,life,shield,location) VALUES (:id_account,:life,:shield,:id_location)");
    $stmt->execute(array('id_account'=>$pIdAccount,'life'=>SPAWN_LIFE,'shield'=>"0",'id_location'=>ID_SPAWN_CELL));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return $row;
  }


  public function getCredit($pIdAccount)
  {
    $stmt = $this->pdo->prepare("SELECT credit FROM adm_user WHERE id_user = ?");
    $stmt->execute(array($pIdAccount));
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    return $row;
  }

}
