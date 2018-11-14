<?php

class etage
{

    private $typeEtage = array();
    private $contenu = array();
    private $porte = array();
    private $indexTypeEtage = 0;
    private $indexContenu = 0;
    private $indexPorte = 0;



    //CONSEQUENCE

    const CONSEQ_RIEN = 0;
    const CONSEQ_GAGNER_PIECE = 1;
    const CONSEQ_ITEM_ALEATOIRE = 2;
    const CONSEQ_ARMURE = 3;
    const CONSEQ_DE_PIPEE = 4;
    const CONSEQ_GAGNER_PORTE = 5;
    const CONSEQ_AMELIORATION = 6;

    //COUT

    const COUT_RIEN = 0;
    const COUT_PIECE = 1;
    const COUT_EMPRISE = 2;
    const COUT_MALCHANCE = 3;
    const COUT_VIE= 4;
    const COUT_ETAGE = 5;

    //REPONSE

    const REP_OUVRIR_UNE_PORTE = array("Ouvrir une porte",0,array(array(self::CONSEQ_GAGNER_PORTE,self::COUT_PIECE)));
    const REP_ACHETER_UN_ITEM = array("Acheter un item aléatoire",1,array(array(self::CONSEQ_ITEM_ALEATOIRE,self::COUT_PIECE)));
    const REP_ATTAQUE_MARCHAND = array("Attaquer le pauvre petit marchand",3,array(array(self::CONSEQ_RIEN,self::COUT_VIE),array(self::CONSEQ_RIEN,self::COUT_VIE),array(self::CONSEQ_GAGNER_PIECE,self::COUT_RIEN)));
    const REP_REFUSER = array("Refusé",4,array(array(self::CONSEQ_RIEN,self::COUT_VIE),array(self::CONSEQ_RIEN,self::COUT_VIE),array(self::CONSEQ_RIEN,self::COUT_ETAGE)));
    const REP_ACCEPTER = array("Accepté",5,array(array(self::CONSEQ_RIEN,self::COUT_PIECE)));
    const REP_OUVRIR_OBJET = array("Ouvrir",6,array(array(self::CONSEQ_ITEM_ALEATOIRE,self::COUT_RIEN)));
    const REP_IMPLORER = array("Implorer le Dieu Wicoce",7,array(array(self::CONSEQ_AMELIORATION,self::COUT_VIE),array(self::CONSEQ_AMELIORATION,self::COUT_RIEN)));
    const REP_VOLER = array("Voler",8,array(array(self::CONSEQ_ARMURE,self::COUT_RIEN),array(self::CONSEQ_ARMURE,self::COUT_EMPRISE)));
    const REP_CASSER = array("Casser",9,array(array(self::CONSEQ_GAGNER_PIECE,self::COUT_RIEN),array(self::CONSEQ_GAGNER_PIECE,self::COUT_EMPRISE)));
    const REP_UTILISER = array("Utiliser",9,array(array(self::CONSEQ_RIEN,self::COUT_RIEN)));
    const REP_ESQUIVE = array("Esquiver :o",10,array(array(self::CONSEQ_RIEN,self::COUT_RIEN),array(self::CONSEQ_RIEN,self::COUT_ETAGE),array(self::CONSEQ_RIEN,self::COUT_ETAGE),array(self::CONSEQ_RIEN,self::COUT_ETAGE)));
    const REP_ATTAQUE = array("Attaquer :D",11,array(array(self::COUT_VIE,self::CONSEQ_GAGNER_PORTE),array(self::COUT_VIE,self::CONSEQ_ITEM_ALEATOIRE)));
    const REP_FUIR = array("Fuir :(",12,array(array(self::CONSEQ_RIEN,self::COUT_ETAGE)));

    const REP_NE_RIEN_FAIRE = array("Ne rien faire",13,array(array(self::CONSEQ_RIEN,self::COUT_RIEN)));

    //CONTENANT

    const COFFRE = array(self::REP_OUVRIR_OBJET,self::REP_NE_RIEN_FAIRE);
    const JARRE = array(self::REP_OUVRIR_OBJET,self::REP_NE_RIEN_FAIRE);
    const ARMOIRE = array(self::REP_OUVRIR_OBJET,self::REP_NE_RIEN_FAIRE);
    const STATUETTE = array(self::REP_IMPLORER,self::REP_NE_RIEN_FAIRE);
    const ARMURE = array(self::REP_VOLER,REP_CASSER,self::REP_NE_RIEN_FAIRE);
    const MIROIR = array(self::REP_UTILISER,self::REP_CASSER,self::REP_NE_RIEN_FAIRE);
    const VIDE = array(self::REP_NE_RIEN_FAIRE);
    const MARCHAND = array(self::REP_ACHETER_UN_ITEM,self::REP_OUVRIR_UNE_PORTE,self::REP_ATTAQUE_MARCHAND,self::REP_NE_RIEN_FAIRE);
    const VOLEUR = array(self::REP_ACCEPTER,self::REP_REFUSER);
    const MONSTRE = array(self::REP_ESQUIVE,self::REP_ATTAQUE,self::REP_FUIR);

    //TYPE SALLE

    const CONTENU_COFFRE = array(self::COFFRE,self::JARRE,self::ARMOIRE,self::STATUETTE,self::ARMURE,self::MIROIR,self::VIDE);
    const CONTENU_NEUTRE = array(self::MARCHAND,self::VOLEUR,self::VIDE);
    const CONTENU_VERSUS = array(self::MONSTRE);

    //PRINCIPAL

    const CONTENU_TYPE = array(self::CONTENU_NEUTRE,self::CONTENU_COFFRE,self::CONTENU_VERSUS);

    const PATTERNE_PORTE = array(array(0,1,0),array(1,0,1),array(1,1,0),array(0,1,1),array(0,0,1),array(1,0,0),array(1,0,0),array(1,1,1));





    public function __construct()
    {
        //GENERATION DE LETAGE //
        //TASK
        //CHOIX DU TYPE ETAGE
        //CHOIX DU CONTENU
        //CHOIX DES PORTES
        //CHOIX DES ACTIONS

        $this->absTypeDeLEtage();
        $this->absContenu();
        $this->absPorte();


    }

    private function absTypeDeLEtage()
    {
        //CHOIX DU TYPE ETAGE
        $key = array_rand(self::CONTENU_TYPE);
        $this->indexTypeEtage = $key;
        $this->typeEtage = self::CONTENU_TYPE[$key];
    }

    private function absContenu()
    {
        //CHOIX DU CONTENU DANS L"ETAGE
        $key = array_rand($this->typeEtage);
        $this->indexContenu = $key;
        $this->contenu = $this->typeEtage[$key];

    }

    private function absPorte()
    {
        $key = array_rand(self::PATTERNE_PORTE);
        $this->indexPorte = $key;
        $this->porte = self::PATTERNE_PORTE[$key];
    }



    public function getActionWithChoice($choice)
    {
        $rep_choisi = $this->contenu[$choice];
        $lesConsequences = $rep_choisi[2];
        $index = array_rand($lesConsequences);

        return $lesConsequences[$index];
    }


    public function getContenu()
    {
        $leContenu = array();

        foreach($this->contenu as $value)
        {
            array_push($leContenu,$value[0]);
        }


        return  array("typeEtage" => $this->indexTypeEtage,"typeContenu" => $this->indexContenu,"patternPorte" => $this->porte,"contenu" => $leContenu);
    }



}

?>
