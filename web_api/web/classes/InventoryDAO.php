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

    public function getAllCard()
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Inventory");
      $stmt->execute();
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


// TODO after tests : copy to UserDAO
public function usersExist($pIdUser,$pIdUser_secondary) //,$pCards,$pCards_secondary)
{
  // check 1: Users
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
  //$stmt->bindParam(':idUser1',$pIdUser);
  //$stmt->bindParam(':idUser2',$pIdUser_secondary));

  }

// TODO after tests : copy to CardDAO
  public function cardsExist($pCards,$pCards_secondary)
  {
    // check 2: Cards
      $stmt2 = $this->pdo->prepare(
      "SELECT CASE
    WHEN (COUNT (id)=0) THEN 1
    WHEN (COUNT (id)=2) THEN 0
    WHEN (COUNT (id)=1 AND id <> :idCard1) THEN 1
    WHEN (COUNT (id)=1 AND id <> :idCard2) THEN 1
    END AS 'Result'
    FROM Cards WHERE EXISTS ( SELECT 1 FROM Cards WHERE id = :idCard1 OR id = :idCard2)
    and (id = :idCard1 OR id = :idCard2)");

    $stmt2->execute(array('idCard1'=>$pCards,'idCard2'=>$pCards_secondary));
    $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
    //var_dump("check cards::",$row2);
    //TEST EBE
    //return $row2 == "OK"?EXIT_CODE_OK:EXIT_CODE_ERROR_SQL;
    return intval($row["Result"]);
    }

    // function delete tuples (désimbrication--interleaving of function exchange)
    public function deleteInventory($pIdUser,$pIdUser_secondary,$pCards,$pCards_secondary)
    {

      //delete 1
      $stmt = $this->pdo->prepare("DELETE FROM Inventory WHERE (idCard = :idCard and idUser = :idUser)
      or (idCard = :idCard2 and idUser = :idUser2) ");
      $stmt->execute(array('idUser'=>$pIdUser,'idCard'=>$pCards,'idUser2'=>$pIdUser_secondary,'idCard2'=>$pCards_secondary));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);


      return intval($row);

    }


    // Second function cards exchange in Inventory => only two Inserts  , the rest of checks made in controllerDAO
    public function exchange($pIdUser,$pIdUser_secondary,$pCards,$pCards_secondary)
    {
      $resultat = true;
      // Insert 1
      $stmt = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
      $stmt->execute(array('idUser'=>$pIdUser,'idCard'=>$pCards_secondary));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      //return ["insert Inventory"=>$row5];
     var_dump("resultat exchange 1",$row);
      // Insert 2
      $stmt2 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
      $stmt2->execute(array('idUser'=>$pIdUser_secondary,'idCard'=>$pCards));
      $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
      var_dump("resultat exchange 2 :",$row2);


     if (!$row && !$row2){
       $resultat = EXIT_CODE_OK; //false ; // 0 => result correct   <>0 incorrect
     }
     else
     {
        $resultat = EXIT_CODE_ERROR_INSERTION_IN_DB;
     }
     return $resultat ;

    }


// READY FOR TESTS
/*
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
END AS 'Result'
FROM Users WHERE EXISTS ( SELECT 1 FROM Users WHERE idUser = :idUser1 OR idUser = :idUser2)
and (idUser = :idUser1 OR idUser = :idUser2)");

<<<<<<< Updated upstream
$stmt->execute(array(':idUser1'=>$pIdUser,':idUser2'=>$pIdUser_secondary));
$rowUsr = $stmt->fetch(PDO::FETCH_ASSOC);
var_dump(["check1"=>$rowUsr]);
=======
$stmt->execute(array('idUser1'=>$pIdUser,'idUser2'=>$pIdUser_secondary));
$row = $stmt->fetch(PDO::FETCH_ASSOC);
>>>>>>> Stashed changes
//return ["check users"=>$row];
//$stmt->bindParam(':idUser1',$pIdUser);
//$stmt->bindParam(':idUser2',$pIdUser_secondary));

// check 2: Cards
  $stmt2 = $this->pdo->prepare(
  "SELECT CASE
WHEN (COUNT (id)=0) THEN 'ERR1_NO_IDCARDS'
WHEN (COUNT (id)=2) THEN 'OK'
WHEN (COUNT (id)=1 AND id <> :idCard1) THEN 'ERR2_NO_IDCARD1'
WHEN (COUNT (id)=1 AND id <> :idCard2) THEN 'ERR3_NO_IDCARD2'
<<<<<<< Updated upstream
END AS 'Result'
FROM Cards WHERE EXISTS ( SELECT 1 FROM Cards WHERE id = :idCard1 OR id = :idCard2)
and (id = :idCard1 OR id = :idCard2)");

$stmt2->execute(array(':idCard1'=>$pCards,':idCard2'=>$pCards_secondary));
$rowCard = $stmt2->fetch(PDO::FETCH_ASSOC);
var_dump(["check2"=>$rowCard]);
=======
END
FROM Users WHERE EXISTS ( SELECT 1 FROM Cards WHERE id = :idCard1 OR idCard = :idCard2) and (id= :idCard1 OR id= :idCard2)");

$stmt2->execute(array('idCard1'=>$pCards,'idCard2'=>$pCards_secondary));
$row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
>>>>>>> Stashed changes
//return ["check cards"=>$row2];


//check 3:    //TODO verif si le signe egalité en PHP correct(?)
if ($rowUsr["Result"] == "OK" and $rowCard["Result"] == "OK" ) // if Users and Cards Exists in General mysql_list_tables
{
<<<<<<< Updated upstream
    $stmt3 = $this->pdo->prepare("DELETE FROM Inventory WHERE idCard = :idCard and idUser = :idUser");
    $stmt3->execute(array(':idUser'=>$pIdUser,':idCard'=>$pCards));
    $row3 = $stmt3->fetch(PDO::FETCH_ASSOC);
    var_dump(["delete1"=>$row3]);

    $stmt4 = $this->pdo->prepare("DELETE FROM Inventory WHERE idCard = :idCard and idUser = :idUser");
    $stmt4->execute(array(':idUser'=>$pIdUser_secondary,':idCard'=>$pCards_secondary));
    $row4 = $stmt4->fetch(PDO::FETCH_ASSOC);
    var_dump(["delete2"=>$row4]);
=======
    $stmt3 = $this->pdo->prepare("DELETE FROM Inventory WHERE (idCard = :idCard and idUser = :idUser)");
    $stmt3->execute(array('idUser'=>$pIdUser,'idCard'=>$pCards));
    $row3 = $stmt3->fetch(PDO::FETCH_ASSOC);


    $stmt4 = $this->pdo->prepare("DELETE FROM Inventory WHERE (idCard =:idCard and idUser= :idUser)");
    $stmt4->execute(array('idUser'=>$pIdUser_secondary,'idCard'=>$pCards_secondary));
    $row4 = $stmt4->fetch(PDO::FETCH_ASSOC);

>>>>>>> Stashed changes
    // Insert 1
    $stmt5 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
    $stmt5->execute(array(':idUser'=>$pIdUser,':idCard'=>$pCards_secondary));
    $row5 = $stmt5->fetch(PDO::FETCH_ASSOC);
    //return ["insert Inventory"=>$row5];
<<<<<<< Updated upstream
    var_dump(["insert1"=>$row5]);
    // Insert 2
    $stmt6 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
    $stmt6->execute(array(':idUser'=>$pIdUser_secondary,':idCard'=>$pCards));
    $row6 = $stmt6->fetch(PDO::FETCH_ASSOC);
    var_dump(["insert2"=>$row6]);
=======

    // Insert 2
    $stmt6 = $this->pdo->prepare("INSERT INTO Inventory(idUser,idCard) VALUES (:idUser,:idCard)");
    $stmt6->execute(array('idUser'=>$pIdUser_secondary,'idCard'=>$pCards));
    $row6 = $stmt6->fetch(PDO::FETCH_ASSOC);
>>>>>>> Stashed changes
    //return ["insert Inventory"=>$row6];

    if ($row5 == false and $row6 == false ) {
       $resultat["error"] = EXIT_CODE_OK ;
    }
    else {
       $resultat["error"] = EXIT_CODE_ERROR_SQL;
    }


 }
 elseif ($rowUsr["Result"] !== "OK") {
  $resultat["error"] =  EXIT_CODE_UNKNOW_ID_USER;
 }

  elseif ($rowCard["Result"] !== "OK") {
   $resultat["error"] =  EXIT_CODE_UNKNOW_CARDS;

 }

$resultat = ["check users"=>$rowUsr,"check cards"=>$rowCard,"delete Inventory"=>$row3,"delete Inventory2"=>$row4,"insert Inventory"=>$row5,"insert Inventory2"=>$row6];
 var_dump($resultat);
//TODO  to correct for function in controllerDAO

 return $resultat["error"];
 }
*/

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
      //var_dump( "resultat check InventoryExist:* ",$row);
      return $row?EXIT_CODE_OK:EXIT_CODE_ERROR_SQL;
    }

////$pDate,
    public function insertTransaction_History($pIdCard1,$pIdCard2,$pIdUser,$pIdUser_secondary,$pType,$pDetails,$pIdInventory, $pPrice,$pDateformat)
    {
      //var_dump('CURRENT_DATE',CURRENT_DATE());
    //  var_dump('CURRENT_DATE',getdate());


   /*
      [seconds] => 40
      [minutes] => 58
      [hours]   => 21
      [mday]    => 17
      [wday]    => 2
      [mon]     => 6
      [year]    => 2003
      [yday]    => 167
      [weekday] => Tuesday
      [month]   => June
      [0]       => 1055901520 */




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
      //$stmt->execute(array('idUser'=>$pIdUser,'idCard'=>$pIdCard));
        $stmt->execute(array(
          'type'=>$pType,
          'details'=>$pDetails,
          'date_modif'=>$pDateformat,//$dateformat,    //':date'=> getdate(), //$pDate, //TODO getdate()
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
