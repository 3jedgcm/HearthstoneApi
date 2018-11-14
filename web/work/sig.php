<?php
include_once "../include/logged.php";

$title = "SIG the Game";
$background = "url('../src/img/background_sig.svg')";
$style = "<link href='/src/css/ticket.css' rel='stylesheet' type='text/css'>";


$lesGamesDAO = new gameDAO(MaBD::getInstance());
$Game = $lesGamesDAO->getOne('Sig The Game :)');
if ($Game->getEtat() == 0) {
    header('Location: /');
}

$SIGDAO = new SIGDAO(MaBDSIG::getInstance());
$dataBaseArc = $SIGDAO->getAll("GEO_ARC");
$dataBasePoint = $SIGDAO->getAll("GEO_POINT");
$dataBaseVersion = $SIGDAO->getAll("GEO_VERSION");




ob_start(); ?>
<div class="ui centered grid one column container segment custom-noSelect">
    <div class="center aligned column">
      <canvas id="myCanvas" width="800" height="800" style="border:1px solid #000000;background:WHITE;"></canvas>

      <div id="displayPos"> </div>

    </div>
</div>
<?php $content = ob_get_clean();

/*Script*/
ob_start(); ?>

<script>
  let jsonBaseArc = <?php echo json_encode($dataBaseArc); ?> ;
  let jsonBasePoint = <?php echo json_encode($dataBasePoint); ?> ;
  let jsonBaseVersion = <?php echo json_encode($dataBaseVersion); ?> ;

  let listPoint = [];
  for (var pointId in jsonBasePoint)
  {
    listPoint.push(jsonBasePoint[pointId].GEO_POI_ID);
  }

  let listArc = [];

  let latitudeP1;
  let latitudeP2;
  let longitudeP1;
  let longitudeP2;
  let distance;
  let res;
  let res2;
  let psommet1;
  let psommet2;
  for (var pointId in jsonBaseArc)
  {

    for (var pointSecondId in jsonBasePoint)
    {
      if(jsonBaseArc[pointId].GEO_ARC_DEB == jsonBasePoint[pointSecondId].GEO_POI_ID)
      {
        latitudeP1 = jsonBasePoint[pointSecondId].GEO_POI_LATITUDE;
        psommet1 = jsonBasePoint[pointSecondId].GEO_POI_ID;
        longitudeP1 = jsonBasePoint[pointSecondId].GEO_POI_LONGITUDE;
      }

      if(jsonBaseArc[pointId].GEO_ARC_FIN == jsonBasePoint[pointSecondId].GEO_POI_ID)
      {
        latitudeP2 = jsonBasePoint[pointSecondId].GEO_POI_LATITUDE;
        psommet2 = jsonBasePoint[pointSecondId].GEO_POI_ID;
        longitudeP2 = jsonBasePoint[pointSecondId].GEO_POI_LONGITUDE;
      }
    }
    res = deg2Lambert(latitudeP1,longitudeP1);
    res2 = deg2Lambert(latitudeP2,longitudeP2);

    distance = Math.sqrt(Math.pow(res[0] - res2[0],2) + Math.pow(res[1] - res2[1],2));
    listArc.push([jsonBaseArc[pointId].GEO_ARC_DEB,jsonBaseArc[pointId].GEO_ARC_FIN,distance]);

  }
  let listVectAdja = [];
  let arcVectAdja = [];
  for (var pointId in listPoint)
{
  arcVectAdja = [];
  for (var pointSecondId in listArc)
  {
    if(listArc[pointSecondId][0] == listPoint[pointId])
    {
      arcVectAdja.push([listArc[pointSecondId][1],listArc[pointSecondId][2]]);
    }
  }
  listVectAdja.push([listPoint[pointId],arcVectAdja]);
}
for (var pointId in listVectAdja)
{
   //document.getElementById("displayPos").innerHTML = document.getElementById("displayPos").innerHTML  + '<br>' + listVectAdja[pointId] ;
}




    //console.log([res,res2,distance,latitudeP1,longitudeP1,latitudeP2,longitudeP2,psommet1,psommet2]);




function deg2Lambert(lat,lon)
{
  lat = lat*3600;
  lon = lon*3600;
  let pi = 3.141592653589793238462643;
  let n = 0.7289686274
  let C = 11745793.39
  let e = 0.08248325676
  let Xs = 600000
  let Ys = 8199695.768
  let gamma0 = (3600*2)+(60*20)+14.025
  gamma0 = gamma0/(180*3600)*pi
  lat = lat/(180*3600)*pi
  lon = lon/(180*3600)*pi
  let L = 0.5*Math.log((1+Math.sin(lat))/(1-Math.sin(lat)))-e/2*Math.log((1+e*Math.sin(lat))/(1-e*Math.sin(lat)))
  let R = C*Math.exp(-n*L)
  gamma = n*(lon-gamma0)
  let Lx = Xs+(R*Math.sin(gamma));
  let Ly = Ys-(R*Math.cos(gamma));
  return [Lx,Ly];
}


  let minX = jsonBasePoint[0].GEO_POI_LATITUDE;
  let maxX = jsonBasePoint[0].GEO_POI_LATITUDE;
  for (var pointFirstId in jsonBasePoint)
  {
    if(minX > jsonBasePoint[pointFirstId].GEO_POI_LATITUDE)
    {
      minX = jsonBasePoint[pointFirstId].GEO_POI_LATITUDE;
    }
    if(maxX < jsonBasePoint[pointFirstId].GEO_POI_LATITUDE)
    {
    maxX = jsonBasePoint[pointFirstId].GEO_POI_LATITUDE;
    }
  }

  let minY = jsonBasePoint[0].GEO_POI_LONGITUDE;
  let maxY = jsonBasePoint[0].GEO_POI_LONGITUDE;
  for (var pointFirstId in jsonBasePoint)
  {
    if(minY > jsonBasePoint[pointFirstId].GEO_POI_LONGITUDE)
    {
      minY = jsonBasePoint[pointFirstId].GEO_POI_LONGITUDE;
    }
    if(maxY < jsonBasePoint[pointFirstId].GEO_POI_LONGITUDE)
    {
    maxY = jsonBasePoint[pointFirstId].GEO_POI_LONGITUDE;
    }
  }
  minX,minY,maxX,maxY

  const difX = maxX -  minX;
  const difY = maxY - minY;



  var c = document.getElementById("myCanvas");
  var ctx = c.getContext("2d");
  for (var pointFirstId in jsonBasePoint)
  {
    ctx.fillStyle = 'green';
    //console.log(maxX,jsonBasePoint[pointFirstId].GEO_POI_LATITUDE,maxX - jsonBasePoint[pointFirstId].GEO_POI_LATITUDE);
    let x = ((maxX - jsonBasePoint[pointFirstId].GEO_POI_LATITUDE)*800)/difX;
    let y = ((maxY - jsonBasePoint[pointFirstId].GEO_POI_LONGITUDE)*800)/difY;
    ctx.moveTo(x,y);
    ctx.font = "11px Arial";
    ctx.fill();
    ctx.arc(x, y, 2, 0, 2 * Math.PI);
    ctx.fillText(jsonBasePoint[pointFirstId].GEO_POI_ID,x+5,y+5);

  }
    ctx.stroke();




</script>





<?php

$script = ob_get_clean();
include_once('../include/template.php'); ?>
