<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";

session_start();

$lesGamesDAO = new gameDAO(MaBD::getInstance());
$Game = $lesGamesDAO->getOne('Le potagé 2.0');
if(!isset($_SESSION["user"]) || $Game->getEtat() == 0)
{
    header('Location: ../logout');
}

$lesUtilisateurDAO = new utilisateurDAO(MaBD::getInstance());
$user = $_SESSION["user"];
$_SESSION["user"] = $lesUtilisateurDAO->getOne($user->getId());
$user = $_SESSION["user"];


$taille = 50;
?>

<!DOCTYPE html>
<html>
    <head>
        <?php 
        require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/meta_autoload.php";
        ?>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" > </script>
        <link href='/src/css/style.css' rel='stylesheet' type='text/css'>
    </head>
    <body id="background_potager">

        <center> <table>
            <tr> 
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_1" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td> 
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_2" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td> 
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_3" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_4" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_5" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_1" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_6" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_7" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_8" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td>
                <td> <img src="../src/img/dirt-576619_1280.png" alt="" id="dirt_1_9" height="<?php echo $taille; ?>" width="<?php echo $taille; ?>"> </td> 
            </tr>




            </table> </center>
        <center>
            <div class="brocolis"> <img src="../src/img/brocolis.png" alt=""  height="42" width="42"> </div>
            <img src="../src/img/carotte.png" alt="" id="carotte" height="42" width="42">
            <img src="../src/img/navet.png" alt="" id="navet" height="42" width="42">
            <img src="../src/img/radis.png" alt="" id="radis" height="42" width="42">

        </center>   



    </body>
</html>



<script>
    $(document).ready(function() {
      
        $(".brocolis").click(function(){
           console.log("coucou");
        });
    });
</script>