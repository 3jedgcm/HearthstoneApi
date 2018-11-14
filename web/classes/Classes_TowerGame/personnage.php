<?php

class personnage
{
    private $vie;
    private $attaque;
    private $monnaie;
    
    public function __construct()
    {
        $this->vie = 100;
        $this->attaque = 10;
        $this->monnaie = 0;
    }
    
    public function absVie()
    {
        return $this->vie;
    }
    
    public function absAttaque()
    {
        return $this->attaque;
    }
    
    public function absMonnaie()
    {
        return $this->monnaie;
    }
    
    public function rejVie($p_vie)
    {
        $this->vie = $p_vie ;
    }
    
    public function rejAttaque($p_attaque)
    {
        $this->attaque = $p_attaque;
    }
    
    public function rejMonnaie($p_monnaie)
    {
        $this->monnaie = $p_monnaie;
    }
    
    public function getContenu()
    {
        return array("viePersonnage" => $this->vie,"attaquePersonnage" => $this->attaque,"monnaiePersonnage" => $this->monnaie);
    }
    
    
}

?>