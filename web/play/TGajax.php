<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";
session_start();
header('content-type:application/json');
$user = $_SESSION["user"];
$id_user = $user->getId();
$choix = $_POST["choix"];

if(isset($_POST["firewall"])) //VERIFICATION SI L'APPELLE AJAX SE FAIT PAR UN SITE INTERNE
{
    $towerGameDAO = new towerGameDAO(MaBD::getInstance());
    $myTowerGame = $towerGameDAO->getOneTowerGame($user->getId());
    $contenu = array();

    //CAS SI AUCUNE PARTIE A DEJA ETAIT CREE
    if($myTowerGame == null)
    {
        $class = new towerGame();
        $monObjetBlobizer = serialize($class);
        $towerGameDAO->setOne($user->getId(),$monObjetBlobizer);
    }
    else
    {   

        $class = unserialize($myTowerGame['fichierBLOB']);
        if($choix != -1)
        {
            if($myTowerGame["state"] == 0)
            {
                $class->setActionUserChoice($choix);
                $contenu = $class->getStageContent();
                $towerGameDAO->updateState($id_user,1);
                 array_push($contenu,0);
            }
            else
            {
                $class->setDoorUserChoice($choix);
                $towerGameDAO->updateState($id_user,0);
                $class->createNewEtage();
                $contenu = $class->getStageContent();
                array_push($contenu,1);
            }
            $blobizer = serialize($class);
            $towerGameDAO->updateOne($id_user,$blobizer);
        }
        else
        {
            if($myTowerGame["state"] == 0)
            {
                $contenu = $class->getStageContent();
                 array_push($contenu,'0');
            }
            else
            {
                $contenu = $class->getStageContent();
                array_push($contenu,'0');
            } 
           
        }
    }
    
    $jsoner = json_encode($contenu);
    echo $jsoner;

}
else
{
    $contenu = array();
    $contenu["bonjour"] = "LE FIREWALL MERDE";
    $jsoner = json_encode($contenu);
    echo $jsoner;

    //echo "ERROR 0: NO FIREWALL";  
}



?>