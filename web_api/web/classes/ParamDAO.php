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

    }

    public function update($obj)
    {

    }

    public function insert($obj)
    {

    }

    public function delete($id_param)
    {

    }


}
