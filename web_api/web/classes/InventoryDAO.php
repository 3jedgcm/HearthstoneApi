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



// READY FOR TESTS
public function exchange($pIdUser,$pIdUser_secondary,$pCards,$pCards_secondary)
{
   // RETURN 0 IF OK RETURN ERROR CODE IF ID USER OR ID CARD DOSNT EXIST
      // TODO On peut ajouter la table log/historique  de ‘transactions-exchages’ entré users  =>log de delete/insert


// check 1: Users
$stmt = $this->pdo->prepare(
    "SELECT CASE
WHEN (COUNT (idUser)=0) THEN 'ERR1_NO_IDUSERS'
WHEN (COUNT (idUser)=2) THEN 'OK'
WHEN (COUNT (idUser)=1 AND idUser<> :idUser1) THEN 'ERR2_NO_IDUSER1'
WHEN (COUNT (idUser)=1 AND idUser<> :idUser2) THEN 'ERR3_NO_IDUSER2'
END
FROM Users WHERE EXISTS ( SELECT 1 FROM Users WHERE idUser = :idUser1 OR idUser = :idUser2)
and (idUser= :idUser1 OR idUser= :idUser2)");

$stmt->execute(array('idUser1'=>$pIdUser,'idUser2'=>$pIdUser_secondary));
$row = $stmt->fetch(PDO::FETCH_ASSOC);
return ["check users"=>$row];
//$stmt->bindParam(':idUser1',$pIdUser);
//$stmt->bindParam(':idUser2',$pIdUser_secondary));

// check 2: Cards
  $stmt2 = $this->pdo->prepare(
  "SELECT CASE
WHEN (COUNT (id)=0) THEN 'ERR1_NO_IDCARDS'
WHEN (COUNT (id)=2) THEN 'OK'
WHEN (COUNT (id)=1 AND id <> :idCard1) THEN 'ERR2_NO_IDCARD1'
WHEN (COUNT (id)=1 AND id <> :idCard2) THEN 'ERR3_NO_IDCARD2'
END
FROM Users WHERE EXISTS ( SELECT 1 FROM Cards WHERE id = :idCard1 OR idCard = :idCard2)
and (id= :idCard1 OR id= :idCard2)");

$stmt2->execute(array('idCard1'=>$pCards,'idCard2'=>$pCards_secondary));
$row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
return ["check cards"=>$row2];


//check 3:
if ($row="OK" and $row2="OK" ) // if Users and Cards Exists in General mysql_list_tables
{
      $stmt3 = $this->pdo->prepare("DELETE FROM Inventory WHERE and (idCard= :idCard and idUser= :idUser)");
      $stmt3->execute(array(':idUser'=>$pIdUser,':idCard'=>$pCards));
      $row3 = $stmt3->fetch(PDO::FETCH_ASSOC);
      return ["deleted Inventory"=>$row3] ;



      $stmt4 = $this->pdo->prepare(
      "DELETE FROM Inventory WHERE
      and (idCard= :idCard and idUser= :idUser)");
      $stmt4->execute(array(':idUser'=>$pIdUser_secondary,':idCard'=>$pCards_secondary));
      $row4 = $stmt4->fetch(PDO::FETCH_ASSOC);
      return ["deleted Inventory"=>$row4] ;




    // Insert 1
    $stmt5 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
    $stmt5->execute(array('idUser'=>$pIdUser,'idCard'=>$pCards_secondary));
    $row5 = $stmt5->fetch(PDO::FETCH_ASSOC);
    return ["insert Inventory"=>$row5];

    // Insert 2
    $stmt6 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
    $stmt6->execute(array('idUser'=>$pIdUser_secondary,'idCard'=>$pIdCard));
    $row6 = $stmt6->fetch(PDO::FETCH_ASSOC);
    return ["insert Inventory"=>$row6];

 }

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
