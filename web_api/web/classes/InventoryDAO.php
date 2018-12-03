<?php
class InventoryDAO extends DAO {

    public function getOne($pIdUser,$pIdCard)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory WHERE idUser = ? and idCard = ?");
      $stmt->execute(array($pIdUser,$pIdCard));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return ["inventory"=>$row];
    }

    public function getAllCardByUserId($pIdUser)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory WHERE idUser = ?");
      $stmt->execute(array($pIdUser));
      foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
      {
        $result[] = [$row['idCard']=>$row];
      }
      return [$pIdUser=>$result];
    }

    public function getAllUserByCardId($pIdCard)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory WHERE idCard = ?");
      $stmt->execute(array($pIdCard));
      foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
      {
        $result[] = [$row['idUser']=>$row];
      }
      return [$pIdCard=>$result];
    }



    public function exchange($pIdUser,$pIdUser_secondary,$pCards,$pCards_secondary)
    {
      // RETURN 0 IF OK RETURN ERROR CODE IF ID USER OR ID CARD DOSNT EXIST


    }


    public function update($obj)
    {
      return true;
    }

    public function insert($pIdCard,$pIdUser)
    {

      $stmt = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
      $stmt->execute(array('idUser'=>$pIdUser,'idCard'=>$pIdCard));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function delete($pIdCard,$pIdUser)
    {
      $stmt = $this->pdo->prepare("DELETE FROM Inventory WHERE idUser = ? and idCard = ?");
      $stmt->execute(array($pIdUser,$pIdCard));

      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function deleteAll($pIdUser)
    {
      $stmt = $this->pdo->prepare("DELETE FROM Inventory WHERE idUser = ?");
      $stmt->execute(array($pIdUser));

      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function checkInventoryExist($pIdCard,$pIdUser)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory WHERE idUser=? and idCard=?");
      $stmt->execute(array($pIdUser,$pIdCard));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row?EXIT_CODE_OK:EXIT_CODE_ERROR_SQL;
    }

}
