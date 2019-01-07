<?php
class CellDAO extends DAO {
    public function getCharacterById($pIdAccount)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM shp_character WHERE id_account = ?");
      $stmt->execute(array($pIdAccount));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function getCellById($pIdCell)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM shp_cell WHERE id = ?");
      $stmt->execute(array($pIdCell));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function getContentById($pIdContent)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM shp_content WHERE id = ?");
      $stmt->execute(array($pIdContent));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function getLinkById($pIdPrincipalCell)
    {
      $stmt = $this->pdo->prepare("SELECT * FROM shp_link WHERE id_principal_cell = ?");
      $stmt->execute(array($pIdPrincipalCell));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      $res[] = $row["id_up_cell"];
      $res[] = $row["id_down_cell"];
      $res[] = $row["id_left_cell"];
      $res[] = $row["id_right_cell"];
      return $res;
    }



    public function testLink($pIdPrincipalCell,$pIdSecondCell)
    {
      $stmt = $this->pdo->prepare("SELECT life_cost
        FROM shp_link WHERE id_principal_cell = ? and (id_up_cell = ? or id_down_cell = ? or id_left_cell = ? or id_right_cell = ?)");
      $stmt->execute(array($pIdPrincipalCell,$pIdSecondCell,$pIdSecondCell,$pIdSecondCell,$pIdSecondCell));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function resetMap()
    {
      $stmt = $this->pdo->prepare("TRUNCATE shp_link");
      $stmt->execute();
      $stmt2 = $this->pdo->prepare("TRUNCATE shp_character");
      $stmt2->execute();
      $stmt3 = $this->pdo->prepare("DELETE FROM shp_cell");
      $stmt3->execute();
      $stmt4 = $this->pdo->prepare("ALTER TABLE shp_cell AUTO_INCREMENT = 1");
      $stmt4->execute();
    }
    public function getRandomContent()
    {
      $stmt = $this->pdo->prepare("SELECT id FROM shp_content ORDER BY RAND() LIMIT 1");
      $stmt->execute();
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function insertSuperCell($idContent)
    {
      $stmt = $this->pdo->prepare("UPDATE shp_cell SET content = ? WHERE content <> 1 ORDER BY RAND() LIMIT 1");
      $stmt->execute(array($idContent));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);

      return $row;
    }

    public function insertCell($idContent)
    {
      $stmt = $this->pdo->prepare("INSERT INTO shp_cell(content) VALUES (?)");
      $stmt->execute(array($idContent));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function insertLink($idPrincipalCell,$idUpCell,$idDownCell,$idRightCell,$idLeftCell)
    {

      $stmt = $this->pdo->prepare("INSERT INTO shp_link(id_principal_cell,id_up_cell,id_down_cell,id_left_cell,id_right_cell,life_cost) VALUES (?,?,?,?,?,?)");
      $stmt->execute(array($idPrincipalCell,$idUpCell,$idDownCell,$idLeftCell,$idRightCell,"-5"));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }






}
