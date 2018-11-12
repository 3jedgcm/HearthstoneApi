<?php
class ParamDAO extends DAO {
    public function getAll()
    {
      $stmt = $this->pdo->prepare("SELECT IdParameter,Value FROM Parameters");
      $stmt->execute();
      foreach ($stmt -> fetchAll(PDO::FETCH_ASSOC) as $row)
      {
        $resultat[] = [$row['IdParameter']=>$row['Value']];
      }
      return $resultat;
    }
}
