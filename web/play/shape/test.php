<?php
include_once "../../include/gameAccess.php";
?>
<!DOCTYPE html>
<html>
 <head>
   <script src="https://code.jquery.com/jquery-3.3.1.min.js" > </script>
   <script src="../../src/js/lib/p5.min.js"></script>
   <script src="../../src/js/shapeSketch.js"></script>
   <title>Shape Adventure</title>
 </head>
 <body>
  Déplacez vous une fois pour créé votre compte ! </br>
  Si vous perdez tous vos points de vie, F5 :) </br>
  La liste des salles disponibles : </br>
  - Vide -> Rien  </br>
  - Coeur -> +5 Hp </br>
  - Poison -> -5 Hp </br>
  - Gold -> -5 Crédits </br>
  - Trou noir -> -50 Hp </br>
  - Spawn -> Rien </br>
  - Lave -> Supprime votre armure </br>
  - Eau -> +25 Armures </br>
  - Diamant -> + 1 000 000 de Crédits (Il est unique :)) </br>



 </body>
 <script> let idAccount = <?= $user->getId()?>;</script>
</html>
