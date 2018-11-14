<?php
//
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";
// Contrôleur


session_start();
$error = "";
$lesGamesDAO = new gameDAO(MaBD::getInstance());
$Game = $lesGamesDAO->getOne('Tower Game');

if(!isset($_SESSION["user"]) || $Game->getEtat() == 0)
{
    header('Location: ./logout');
}
$user = $_SESSION["user"];


$towerGameDAO = new towerGameDAO(MaBD::getInstance());

$data = 5;



?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="shortcut icon" type="image/ico" href="img/favicon.gif" />
        <link rel="stylesheet" type="text/css" href="style.css" />
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" > </script>
    </head>
    <body>
        <h3>Tower Game</h3>

        <div id="erreur"> Aucune erreur détéctée </div>
        <div id="etage"></div>
        <div id="personnage"></div>
        <div id="ennonce"></div>
        <div id="mesBoutons"></div>
        <script>
            var enonceDoors = ["Quelle porte choisis tu ?","Une petite porte ?","Et si tu prennais une porte ?","Degarpille d'ici vite !"];
            function sendAction(num)
            {
                $( "#ennonce" ).text(enonceDoors[Math.floor(Math.random()*enonceDoors.length)]);
                $('#mesBoutons').empty();
                console.log("Choix du bouton Action " + num);
                $.ajax(
                    {
                        url: "TGajax.php",
                        type: "POST",
                        dataType: "json",
                        data: {"firewall":1, "choix": num},
                        success: function(data){
                            console.log(data);
                            affichageText(data);
                            addButtonDoors(data);
                        },
                        error : function(resultat, statut, erreur){
                            $('#premier').text() 
                        },
                        complete : function(resultat, statut){}
                    });                    
            }

            function sendDoors(num)
            {


                $('#mesBoutons').empty();
                console.log("Choix du bouton Porte " + num);
                $.ajax(
                    {
                        url: "TGajax.php",
                        type: "POST",
                        dataType: "json",
                        data: {"firewall":1, "choix": num},
                        success: function(data)
                        {
                            console.log(data);
                            affichageText(data);
                            var action = getActionEnonce(data[0].typeEtage,data[0].typeContenu);
                            $( "#ennonce" ).text(action);
                            addButtonAction(data);
                        },
                        error : function(resultat, statut, erreur)
                        {
                            $('#premier').text() 
                        },
                        complete : function(resultat, statut)
                        {

                        }
                    });                    
            }

            $(document).ready(function() {
                console.log("RECUPERATION DU JEU");
                $.ajax(
                    {
                        url: "TGajax.php",
                        type: "POST",
                        dataType: "json",
                        data: {"firewall":1, "choix": -1},
                        success: function(data)
                        {
                            console.log(data);
                            if(data[3] == "1")
                                {
                                     affichageText(data);
                                    var action = getActionEnonce(data[0].typeEtage,data[0].typeContenu);
                                    $( "#ennonce" ).text(action);
                                    addButtonAction(data);  
                                }
                            else
                                {
                                    affichageText(data);
                                    addButtonDoors(data);
                                }
                            

                        },
                        error : function(resultat, statut, erreur)
                        {
                            $('#premier').text() 
                        },
                        complete : function(resultat, statut){}
                    });                            
            });


            function affichageText(data)
            {
                var personnageVie = data[1].viePersonnage;
                var attaquePersonnage = data[1].attaquePersonnage;
                var monnaiePersonnage = data[1].monnaiePersonnage;
                var personnage = 'Personnage : Vie='+personnageVie+' Attaque='+attaquePersonnage+' Monnaie='+ monnaiePersonnage + ''; 
                var etage = 'Etage: '+data[2]+'/100';
                $("#etage").text(etage);
                $("#personnage").text(personnage);
            }

            function addButtonDoors(data)
            {
                var lesPortes = data[0].patternPorte;
                var numDoors = 0;

                lesPortes.forEach(function(element)
                                  {
                    if(element == 1)
                    {
                        var r= $('<input type="button"  value="Porte n°' + numDoors + '" onclick="sendDoors(' + numDoors + ')" />');
                        $("#mesBoutons").append(r);
                        numDoors++;
                    }

                    
                });
                var r= $('<input type="button"  value="Retour" onclick="sendDoors('+ 99 + ')" />');
                $("#mesBoutons").append(r);
            }

            function addButtonAction(data)
            {
                var lesActions = data[0].contenu;
                var numChoice = 0;
                lesActions.forEach
                (
                    function(element)
                    {
                        var r= $('<input type="button"  value="' + element + '" onclick="sendAction(' + numChoice + ')" />');
                        $("#mesBoutons").append(r);
                        numChoice++;
                    }
                );
            }




            function getActionEnonce(typeEtage,contenuEtage)
            {
                var enonceAction;
                switch(typeEtage)
                {
                    case 0:
                        {
                            switch(contenuEtage)
                            {
                                case 0:
                                    {
                                        return enonceAction = ["Un marchand est ici ? Que fait tu avec lui ?"];
                                        break;
                                    }
                                case 1:
                                    {
                                        return enonceAction = ["Attention! Le voleur est là ?"];
                                        break;
                                    }
                                case 2:
                                    {
                                        return enonceAction = ["Aaaah bah il y a rien ici !"];
                                        break;
                                    }
                            }
                            break;
                        }
                    case 1:
                        {
                            switch(contenuEtage)
                            {
                                case 0:
                                    {
                                        return enonceAction = ["Oooooh un jolie coffre! Attention il n'est pas a toi :p"];
                                        break;
                                    }
                                case 1:
                                    {
                                        return enonceAction = ["Une jarre ? Mouais je metterais pas la main dedans !"];
                                        break;
                                    }
                                case 2:
                                    {
                                        return enonceAction = ["Bon une armoire crade et moche, rien ne coute de l'ouvire ? Sauf si il y a un fantome dedans :p"];
                                        break;
                                    }
                                case 3:
                                    {
                                        return enonceAction = ["Bon une statuette ..."];
                                        break;
                                    }
                                case 4:
                                    {
                                        return enonceAction = ["Quelle jolie armure! Et si on la mettait?"];
                                        break;
                                    }
                                case 5:
                                    {
                                        return enonceAction = ["Ohhh miroire jolie miroire qui est le plus beau .... derfliw ?"];
                                        break;
                                    }
                                case 5:
                                    {
                                        return enonceAction = ["Ici aussi il y a rien !"];
                                        break;
                                    }
                            }
                            break;
                        }
                    case 2:
                        {
                            return enonceAction = ["Un mechant monstre est ici ? Montre qui est le plus fort ? ... mauviette "];
                            break;
                        }
                }
            }
        </script>
        <a href="../" >Retour</a>
    </body>
</html>