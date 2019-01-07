<?php
if(isset($_POST["fileName"])){
    header('Content-Disposition: filename="'. $_POST["fileName"] .'"');
}
else{
    header('Content-Disposition: filename="chemin.kml"');
}

header('Content-type: application/vnd.google-earth.kml+xml');
$my_file = 'file.txt';
$handle = fopen("file/".$_POST["fileName"], 'w') or die('Cannot open file:  '.$my_file);
fwrite($handle, $_POST["content"]);

echo $_POST["content"];
?>
