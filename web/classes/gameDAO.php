<?php

// Classe pour l'accès à la table produit
class gameDAO extends DAO {

    public function getOne($nom)
    {
        $stmt = $this->pdo->prepare("SELECT * FROM lob_game WHERE nom=?");
        $stmt->execute(array($nom));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        return new game($row);
    }

    // Récupération de tous les jeux
    public function getAll()
    {
        $res = array();
        $stmt = $this->pdo->query("SELECT * FROM lob_game ORDER BY id_game");
        foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
            $res[] = new game($row);
        return $res;
    }

    public function getVisible($user)
    {
        $all = $this->getAll();
        return array_filter($all, function ($game) use ($user)
        {
            return $game->isVisible($user);
        });
    }

    public function update($obj)
    {
       $stmt =  $this->pdo->prepare("INSERT INTO adm_user(login,mdp,rang,code_recup) VALUES (?,?,'Base',?)");
       $res = $stmt->execute(array($obj->login,$obj->mdp,$obj->code));
       return $res;
    }

    public function insert($obj)
    {
       $stmt =  $this->pdo->prepare("INSERT INTO adm_user(login,mdp,rang,code_recuperation) VALUES (:login,:mdp,:rang,:code_recuperation)");
       $res = $stmt->execute(array('login' => $obj->login,
                                   'mdp' => $obj->mdp,
                                   'code_recuperation' => $obj->code_recuperation,
                                   'rang' => 'base'
                                  ));
    }

        public function delete($obj)
        {
        $stmt = $this->pdo->prepare("DELETE FROM adm_user WHERE id_user=?");
        $res = $stmt->execute(array($obj->ref_pdt));
        return $res;
    }


    public function changeEtatGame($id)
    {
        $stmt = $this->pdo->prepare("select etat from lob_game where id_game=?");
        $res = $stmt->execute(array($id));
        $value  = $stmt->fetch(PDO::FETCH_ASSOC);
        $val = $value['etat'];
        $val = intval($val);
        switch ($val){
            case ONLINE_GAME:
                $val = OFFLINE_GAME;
                break;
            case OFFLINE_GAME:
                $val =ONLINE_GAME;
                break;
        }

        $stmt = $this->pdo->prepare("UPDATE lob_game SET etat=? WHERE id_game=?");
        $res = $stmt->execute(array($val,$id));
    }

}
