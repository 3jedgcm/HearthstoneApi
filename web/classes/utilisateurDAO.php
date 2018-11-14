<?php

// Classe pour l'accès à la table produit
class utilisateurDAO extends DAO {

    // Récupération d'un utilistateur dont on donne l'identifiant
    public function getOne($ref) {
        $stmt = $this->pdo->prepare("SELECT * FROM adm_user WHERE id_user=?");
        $stmt->execute(array($ref));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        return new utilisateur($row);
    }
    
    public function rafraichir($ref) {
        $stmt = $this->pdo->prepare("SELECT * FROM adm_user WHERE login=?");
        $stmt->execute(array($ref));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        return new utilisateur($row);
    }

    // Récupération de tous les utilisateurs
    public function getAll() {
        $res = array();
        $stmt = $this->pdo->query("SELECT * FROM adm_user ORDER BY id_user");
        foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row)
            $res[] = new utilisateur($row);
        return $res;
    }

     public function checkAuthentification($login) {
        $stmt = $this->pdo->prepare("SELECT * FROM adm_user WHERE login=?");
        $stmt->execute(array($login));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($row)
            return new utilisateur($row);
        else
            return null;
    }

    public function update($obj) {
       $stmt =  $this->pdo->prepare("INSERT INTO adm_user(login,mdp,rang,code_recup) VALUES (?,?,'Base',?)");
       $res = $stmt->execute(array($obj->login,$obj->mdp,$obj->code));
       return $res;
    }
    
    public function insert($obj) {   
       $stmt =  $this->pdo->prepare("INSERT INTO adm_user(login,mdp,rang,code_recuperation) VALUES (:login,:mdp,:rang,:code_recuperation)");
       $res = $stmt->execute(array('login' => $obj->login,
                                   'mdp' => $obj->mdp,
                                   'code_recuperation' => $obj->code_recuperation,
                                   'rang' => 'base'
                                  ));
    }

    // Effacement de l'objet $obj (DELETE)
    public function delete($id) {
        $stmt = $this->pdo->prepare("DELETE FROM adm_user WHERE id_user=?");
        $res = $stmt->execute(array($id));
        return $res;
    }

     public function checkLogin($login) {
        $stmt = $this->pdo->prepare("SELECT * FROM adm_user WHERE login=?");
        $stmt->execute(array($login));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($row)
            return new utilisateur($row);
        else
            return null;
    }
    
    public function checkRecup($login) {
    
        $stmt = $this->pdo->prepare("SELECT * FROM adm_user WHERE login=?");
        $stmt->execute(array($login));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        if ($row)
        {
            $user = new utilisateur($row);
            return $user->getCodeRecuperation();
        }
        else
        {
            return null;
        }
    }
        
     public function updateMdp($newMdp,$login) {
        $stmt = $this->pdo->prepare("UPDATE adm_user SET mdp = :mdp WHERE login = :login ");
        $res = $stmt->execute(array('mdp' => $newMdp,'login' => $login));
    }
    
    public function updateLogin($login,$id) {
        $stmt = $this->pdo->prepare("UPDATE adm_user SET login = :login WHERE id_user = :id ");
        $res = $stmt->execute(array('id' => $id,'login' => $login));
    }
    
    public function updateRecup($recup,$id) {
        $recup  = password_hash($recup,PASSWORD_BCRYPT);
        $stmt = $this->pdo->prepare("UPDATE adm_user SET code_recuperation = :code_recuperation WHERE id_user = :id ");
        $res = $stmt->execute(array('code_recuperation' => $recup,'id' => $id));
    }
    
    public function updateAdminMdp($mdp,$id) {
        $mdp  = password_hash($mdp,PASSWORD_BCRYPT);
        $stmt = $this->pdo->prepare("UPDATE adm_user SET mdp = :mdp WHERE id_user = :id ");
        $res = $stmt->execute(array('mdp' => $mdp,'id' => $id));
    }
    
    public function updateAdminCredit($credit,$id) {
        $stmt = $this->pdo->prepare("UPDATE adm_user SET credit = :credit WHERE id_user = :id ");
        $res = $stmt->execute(array('credit' => $credit,'id' => $id));
    }
    
    
    
}

