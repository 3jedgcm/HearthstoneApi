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

    public function insert($card,$id_user)
    {
      return true;
    }

    public function delete($id_card,$id_user)
    {
      return true;
    }

    public function deleteAll($id_user)
    {
      return true;
    }


    public function checkExist($id_card,$id_user)
    {
      return true;
    }

}