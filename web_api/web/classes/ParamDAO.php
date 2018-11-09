<?php
class ParamDAO extends DAO {

    public function getOne($id_param)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM Parameters WHERE IdParameter=?");
      $stmt->execute(array($id_param));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return new Param($row);
    }

    public function getAll()
    {
      //$stmt = $this->pdo->prepare("SELECT * FROM Parameters");
      $stmt = $this->pdo->prepare("SELECT IdParameter,Value FROM Parameters");
      $stmt->execute();
      foreach ($stmt -> fetchAll(PDO::FETCH_ASSOC) as $row)
      {
        $resultat[] = [$row['IdParameter']=>$row['Value']];
      }
      
      return $resultat;

    }

    public function update($param)
    {
      $stmt = $this->pdo->prepare("UPDATE Param SET Value =? WHERE IdParameter =?");
      //UPDATE users SET name=?, surname=?, sex=? WHERE id=?"
      $stmt->execute(array($param['Value'],$param['IdParameter']));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row ;
    }

    public function insert($param)
    {
      $stmt = $this->pdo->prepare("INSERT INTO Param VALUES (?,?)");
      $stmt->execute(array($param['IdParameter'],$param['Value']));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);

      return $row ;

    }

    public function delete($id_param)
    {
      $stmt = $this->pdo->prepare("DELETE FROM Param WHERE IdParameter =?");
      $stmt->execute(array($id_param));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row ; //deleted row
      //  return new Param($row);

      // "DELETE FROM movies WHERE filmID =  :filmID";



    }


}
