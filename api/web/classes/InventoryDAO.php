<?php
class InventoryDAO extends DAO {

    public function getOne($pIdUser,$pIdCard)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory WHERE idUser = ? and idCard = ?");
      $stmt->execute(array($pIdUser,$pIdCard));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return ["inventory"=>$row];
    }

    public function getAllCardByUserId($pDAO,$pIdUser)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory WHERE idUser = ?");
      $stmt->execute(array($pIdUser));
      foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
      {
        $result[] = $row['idCard'];
      }

      return $pDAO["Card"]->getCardsByArrayId($result);


      $result = false;




      return $result;
    }

    public function usersExist($pIdUser,$pIdUser_secondary)
    {
      $stmt = $this->pdo->prepare(
      "SELECT CASE
      WHEN (COUNT (idUser)=0) THEN 1
      WHEN (COUNT (idUser)=2) THEN 0
      WHEN (COUNT (idUser)=1 AND idUser<> :idUser1) THEN 1
      WHEN (COUNT (idUser)=1 AND idUser<> :idUser2) THEN 1
      END AS 'Result'
      FROM Users WHERE EXISTS (SELECT 1 FROM Users WHERE idUser = :idUser1 OR idUser = :idUser2)
      and (idUser = :idUser1 OR idUser = :idUser2)");
      $stmt->execute(array(':idUser1'=>$pIdUser,':idUser2'=>$pIdUser_secondary));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return intval($row["Result"]);
    }

    public function deleteInventory($pIdUser,$pIdUser_secondary,$pCards,$pCards_secondary)
    {
      $stmt = $this->pdo->prepare("DELETE FROM Inventory WHERE (idCard = :idCard and idUser = :idUser)
      or (idCard = :idCard2 and idUser = :idUser2) ");
      $stmt->execute(array('idUser'=>$pIdUser,'idCard'=>$pCards,'idUser2'=>$pIdUser_secondary,'idCard2'=>$pCards_secondary));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return intval($row);
    }

    public function exchange($pIdUser,$pIdUser_secondary,$pCards,$pCards_secondary)
    {
      $resultat = true;
      $stmt = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
      $stmt->execute(array('idUser'=>$pIdUser,'idCard'=>$pCards_secondary));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);

      $stmt2 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
      $stmt2->execute(array('idUser'=>$pIdUser_secondary,'idCard'=>$pCards));
      $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);

     if (!$row && !$row2)
     {
       $resultat = EXIT_CODE_OK;
     }
     else
     {
       $resultat = EXIT_CODE_ERROR_INSERTION_IN_DB;
     }
     return $resultat ;
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
      return $row?EXIT_CODE_OK:EXIT_CODE_INVENTORY_IS_MISSING;
    }

    public function insertTransaction_History($pIdCard1,$pIdCard2,$pIdUser,$pIdUser_secondary,$pType,$pDetails,$pIdInventory, $pPrice,$pDateformat)
    {
      $stmt = $this->pdo->prepare("INSERT INTO Transaction_History(type,details,date_modif,user_origin,user_target,idCard1,idCard2,idInventory,price)
      VALUES(
        :type,
        :details,
        :date_modif,
        :user_origin,
        :user_target,
        :idCard1,
        :idCard2,
        :idInventory,
        :price
       )");
        $stmt->execute(array(
          'type'=>$pType,
          'details'=>$pDetails,
          'date_modif'=>$pDateformat,
          'user_origin'=>$pIdUser,
          'user_target'=>$pIdUser_secondary,
          'idCard1'=>$pIdCard1,
          'idCard2'=>$pIdCard2,
          'idInventory'=>$pIdInventory,
          'price'=>$pPrice ));

      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

}
