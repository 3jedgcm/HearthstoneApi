<?php
class UserDAO extends DAO {

    public function getOne($id_user)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Users WHERE IdUser=?");
      $stmt->execute(array($id_user));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function getAll()
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Users");
      $stmt->execute(array());
      foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
          $res[] = $row;
      return $res;
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

    public function checkIdUser($id_user)
    {
      $stmt = $this->pdo->prepare("SELECT IdUser FROM Users WHERE IdUser=?");
      $stmt->execute(array($id_user));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row?EXIT_CODE_OK:EXIT_CODE_ERROR_SQL;
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


    public function checkAccountWithBase($pLogin,$pHashPass)
    {
      $pHashPass = md5($pHashPass);
      $stmt = $this->pdo->prepare("SELECT * FROM Users WHERE Login=? and Password=?");
      $stmt->execute(array($pLogin,$pHashPass));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function checkAccountWithGoogle($pKey)
    {
      $pKey = md5($pKey);
      $stmt = $this->pdo->prepare("SELECT * FROM Users WHERE KeyGoogle=?");
      $stmt->execute(array($pKey));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function checkAccountWithFacebook($pKey)
    {
      $pKey = md5($pKey);
      $stmt = $this->pdo->prepare("SELECT * FROM Users WHERE KeyFacebook=?");
      $stmt->execute(array($pKey));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function registerWithBase($pLogin,$pHashPass)
    {

      $pHashPass = md5($pHashPass);
      $stmt = $this->pdo->prepare("INSERT INTO Users(Login,Password) VALUES (?,?)");
      $stmt->execute(array($pLogin,$pHashPass));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function registerWithGoogle($pKey)
    {
      $pHashPass = md5($pKey);
      $stmt = $this->pdo->prepare("INSERT INTO Users(KeyGoogle) VALUES (?)");
      $stmt->execute(array($pHashPass));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function registerWithFacebook($pKey)
    {
      $pHashPass = md5($pKey);
      $stmt = $this->pdo->prepare("INSERT INTO Users(KeyFacebook) VALUES (?)");
      $stmt->execute(array($pHashPass));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }



}
