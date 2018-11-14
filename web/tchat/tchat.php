<!doctype html>

<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";

?>


<html lang="fr">
    <head>
        <meta charset="utf-8">
        <title>Titre de la page</title>
        <link rel="stylesheet" href="style.css">
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" > </script>
        <script src="script.js"> </script>
    </head>
    <body>
        <section>
            <iframe src="./frameTchat.php" height="500" width="400"></iframe>
            <div class="form">
                <label for=message>Message</label> <textarea style="resize: none;" id="message" name="message" rows=1 required></textarea>
                <button id="button" name="button" type="submit" onclick="sendMessage()">Send a message</button>
                
            </div>
        </section>
    </body>

</html>
