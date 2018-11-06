<?php
class UserDAO extends DAO {

    public function getOne($id_user)
    {
      //Renvoi un objet card
    }

    public function getAll()
    {
      //Renvoi un un tableau d'objet des cartes d'un utilisateur
    }

    public function update($obj)
    {

    }

    public function insert($obj)
    {

    }

    public function delete($id_user)
    {

    }

    public function checkOneById($id_user)
    {
      return true;
    }

    public function toString()
    {
      return "I'm Alive";
    }

    public function getMoney($id_user)
    {
      $stmt = $this->pdo->prepare("SELECT Money FROM Users WHERE IdUser=?");
      $stmt->execute(array($id_user));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row["Money"];
    }

    public function setMoney($id_user,$value)
    {
      $stmt = $this->pdo->prepare("UPDATE Users SET Money = ? WHERE IdUser=?");
      $stmt->execute(array($value,$id_user));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row["Money"];
    }

    public function getAllMoney()
    {
      $stmt = $this->pdo->prepare("SELECT IdUser,Money FROM Users");
      $stmt->execute();
      foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
          $res[] = [$row["IdUser"]=>$row["Money"]];
      return $res;

    }

    public function setAllMoney($value)
    {
      $stmt = $this->pdo->prepare("UPDATE Users SET Money = ?");
      $stmt->execute(array($value));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row["Money"];
    }


}
