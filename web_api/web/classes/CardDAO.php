<?php
class CardDAO extends DAO {

    public function getOne($id_card,$id_user)
    {
      //Renvoi un objet card
      return true;
    }

    public function getAll($id_user)
    {
      //Renvoi un un tableau d'objet des cartes d'un utilisateur
      return true;
    }

    public function update($obj)
    {
      return true;
    }

    public function insert($pCard)
    {
      echo $pCard;
      $stmt = $this->pdo->prepare("INSERT INTO Cards VALUES (?,?,?,?,?,?,?,?)");
      $stmt->execute(array($card['id'],$card['nameCard'],$card['description'],$card['url'],$card['type'],$card['attack'],$card['life'],$card['cost']));
      $row = $stmt->fetch(PDO::FETCH_ASSOC);
      return $row;
    }

    public function delete($id_card,$id_user)
    {
      return true;
    }


    public function checkExist($id_card,$id_user)
    {
      return true;
    }

}
