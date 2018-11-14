<?php
// Classe pour l'accès à la table produit
class ticketDAO extends DAO {

    // Récupération d'un utilistateur dont on donne l'identifiant
       public function useACredit($id_user) {
        $stmt = $this->pdo->prepare("SELECT credit FROM adm_user WHERE id_user=?");
        $stmt->execute(array($id_user));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $creditRestant = $row['credit'];

        if($creditRestant > 0)
        {
            $creditRestant = $creditRestant - 1;
            $stmt = $this->pdo->prepare("UPDATE adm_user SET credit = ? WHERE id_user = ?");
            $stmt->execute(array($creditRestant,$id_user));
            return 0;
        }

        else
        {
            return -1;
        }
    }





    public function getTicketSimple($id_user) {
        $stmt = $this->pdo->prepare("SELECT credit FROM adm_user WHERE id_user=?");
        $stmt->execute(array($id_user));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $creditRestant = $row['credit'];

        if($creditRestant > 0)
        {
            $tirageDuTicket = rand(-1,100000);
            if($tirageDuTicket == 0)
            {
                $newCredit = SIXTICKETGAIN;
                $numGain = 6;
            }
            else if($tirageDuTicket > 0 && $tirageDuTicket < 11 )
            {
                $newCredit = FIVETICKETGAIN;
                $numGain = 5;
            }
            else if($tirageDuTicket > 11 && $tirageDuTicket < 1011 )
            {
                $newCredit = FOURTICKETGAIN;
                $numGain = 4;
            }
            else if($tirageDuTicket > 1011 && $tirageDuTicket < 5011 )
            {
                $newCredit = THREETICKETGAIN;
                $numGain = 3;
            }
            else if($tirageDuTicket > 5011 && $tirageDuTicket < 15011 )
            {
                $newCredit = TWOTICKETGAIN;
                $numGain = 2;
            }
            else if($tirageDuTicket > 15011 && $tirageDuTicket < 35001 )
            {
                $newCredit = ONETICKETGAIN;
                $numGain = 1;
            }
            else
            {
                $numGain = 0;
                $newCredit = 0;
            }

            if($newCredit != 0)
            {
                $creditRestant = $creditRestant + $newCredit;
                $stmt = $this->pdo->prepare("UPDATE adm_user SET credit = ? WHERE id_user = ?");
                $stmt->execute(array($creditRestant,$id_user));
            }
        }
        else
        {
            return -1;
        }

        return array($numGain,$newCredit);
    }




}
