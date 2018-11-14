<?php

class game extends TableObject {

    public function getName(){
        return $this->fields['nom'];
    }

    public function isVisible($user){
        return $this->fields['etat'] == SERVER_GAME && $user->getRang() == SKEDULER_USER || $this->fields['etat'] != SERVER_GAME;
    }

    public function getEtat()
    {
        return $this->fields['etat'];
    }

    public function getLink(){
        return isset($this->fields['lien']) && ($this->fields['etat'] == ONLINE_GAME || $this->fields['etat'] == SERVER_GAME) ? $this->fields['lien'] : null;
    }
    
    public function afficheAdmin()
        
    {
        if($this->fields['etat'] != 2)
        {
         echo
        '<tr>
            <td> ' . $this->fields['nom'] . ' </td> 
            <td>
                <form method="post">
                    <input type="hidden" name="id" value="'.  $this->fields['id_game'] .'">
                    <input type="submit"  class="' . ($this->fields['etat'] == 1 ? 'open' : 'close') . '" value="' . ($this->fields['etat'] == 1 ? 'Ouvert' : 'Fermé') . '" name="submit">
                </form> 
            </td> 
        </tr>';
        }

        
    }

}