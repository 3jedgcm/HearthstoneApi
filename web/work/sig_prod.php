<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto-load/classes_autoload.php";

$title = "SIG the Game";
$background = "url('../src/img/background_sig.svg')";
$style = "<link href='/src/css/ticket.css' rel='stylesheet' type='text/css'>";
$footerText = "2018 - Clément Merlet";

$SIGDAO = new SIGDAO(MaBDSIG::getInstance());
$dataBaseArc = $SIGDAO->getAll("GEO_ARC");
$dataBasePoint = $SIGDAO->getAll("GEO_POINT");
$dataBaseVersion = $SIGDAO->getAll("GEO_VERSION");

ob_start(); ?>
<input type="button" value="Download Kml File" onClick="generate()"/>
<select id="first-select"></select>
<select id="second-select"></select>
<br>
/*<iframe id="iframeTest" src="https://www.doogal.co.uk/KmlViewer.php?url=https://coopuniverse.fr/work/file/testfile.kml" width="90%" height="90%"></iframe>
<div>Tout droit de la carte <a href="http://Doogal.co.uk">Doogal.co.uk</a> & Google </div>
<?php $content = ob_get_clean();

/*Script*/
ob_start(); ?>

<script>
let firstSelect = document.getElementById("first-select");
let secondSelect = document.getElementById("second-select");


let jsonBaseArc = <?php echo json_encode($dataBaseArc); ?> ;
let jsonBasePoint = <?php echo json_encode($dataBasePoint); ?> ;
let jsonBaseVersion = <?php echo json_encode($dataBaseVersion); ?> ;

for (var pointId in jsonBasePoint)
{
  var x = document.getElementById("first-select");
  var option = document.createElement("option");
  option.text = jsonBasePoint[pointId].GEO_POI_ID;
  x.add(option);
  var y = document.getElementById("second-select");
  var option = document.createElement("option");
  option.text = jsonBasePoint[pointId].GEO_POI_ID;
  y.add(option);
}


let listPoint = [];
let latitudeP1;
let latitudeP2;
let longitudeP1;
let longitudeP2;
let distance;
let res;
let res2;
let psommet1;
let psommet2;
let listDistanceArc = new Map();
let listVectAdja = new Map();
let arcVectAdja = [];
let C = new Map();
let d = new Map();
let fi = new Map();
let F = Array();
let couleur = new Map();
let distanceD = new Map();
let antecedant = new Map();
let ListSommetAccessible = Array();
let coordinates = Array()


function visitPage()
{
     window.location='https://coopuniverse.fr/work/testfile.kml';
 }

function generate()
{
  getListPoint()
  getDistance()
  arcVecAdjac()
  algoDistance()
  dijkstra()
   imprimerChemin(parseInt(document.getElementById("first-select").value),parseInt(document.getElementById("second-select").value),antecedant,coordinates)
   generateKml()
   console.log(antecedant)
}
function getListPoint()
{
  for (var pointId in jsonBasePoint)
  {
    listPoint.push(jsonBasePoint[pointId].GEO_POI_ID);
  }
}
function getDistance()
{
  for (var indexArc in jsonBaseArc)
  {
    for (var indexPoint in jsonBasePoint)
    {
      if(jsonBasePoint[indexPoint].GEO_POI_ID == jsonBaseArc[indexArc].GEO_ARC_DEB)
      {
        latitudeP1 = jsonBasePoint[indexPoint].GEO_POI_LATITUDE;
        psommet1 = jsonBasePoint[indexPoint].GEO_POI_ID;
        longitudeP1 = jsonBasePoint[indexPoint].GEO_POI_LONGITUDE;
      }
    }

    for (var indexPoint in jsonBasePoint)
    {
      if(jsonBasePoint[indexPoint].GEO_POI_ID == jsonBaseArc[indexArc].GEO_ARC_FIN)
      {
        latitudeP2 = jsonBasePoint[indexPoint].GEO_POI_LATITUDE;
        psommet2 = jsonBasePoint[indexPoint].GEO_POI_ID;
        longitudeP2 = jsonBasePoint[indexPoint].GEO_POI_LONGITUDE;
      }
    }
    res = deg2Lambert(latitudeP1,longitudeP1);
    res2 = deg2Lambert(latitudeP2,longitudeP2);
    distance = Math.sqrt(Math.pow(res[0] - res2[0],2) + Math.pow(res[1] - res2[1],2));
    listDistanceArc.set(jsonBaseArc[indexArc].GEO_ARC_DEB+","+jsonBaseArc[indexArc].GEO_ARC_FIN,distance);
    listDistanceArc.set(jsonBaseArc[indexArc].GEO_ARC_FIN+","+jsonBaseArc[indexArc].GEO_ARC_DEB,distance);
  }
}
function algoDistance()
{
  listPoint.forEach(function(element){
    C.set(Number(element),"blanc");
    d.set(Number(element),-1);
    fi.set(Number(element),-1);
  });
  d.set(parseInt(document.getElementById("first-select").value),0);
  fi.set(0,-1);
  F.push(parseInt(document.getElementById("first-select").value));
  let u;
  let finalPointTab = Array();
  while(F.length > 0)
  {
    u = Number(F.shift())
    finalPointTab = listVectAdja.get(u);
    finalPointTab.forEach(function(element){
      if(C.get(Number(element)) == "blanc")
      {
        C.set(Number(element),"gris");
        d.set(Number(element),d.get(u) + Number(listDistanceArc.get(u+","+element)))
        fi.set(Number(element),u);
        F.push(Number(element))
      }
    });
    C.set(u,"noir")
  }
}
function dijkstra()
{
  listPoint.forEach(function(element){
    couleur.set(Number(element),"blanc");
    distanceD.set(Number(element),Number.MAX_VALUE);
    antecedant.set(Number(element),null);
  });
  let arrivee = parseInt(document.getElementById("second-select").value)
  distanceD.set(parseInt(document.getElementById("first-select").value),0);
  couleur.set(parseInt(document.getElementById("first-select").value),"gris")
  ListSommetAccessible.push(parseInt(document.getElementById("first-select").value));

  let distanceElement
  let sommetAdjac
  while(ListSommetAccessible.length > 0)
  {
   u = getMin(ListSommetAccessible,distanceD)
   ListSommetAccessible = deleteByValue(ListSommetAccessible,u)
   sommetAdjac = getAdjacent(couleur,listVectAdja,ListSommetAccessible,u);
   sommetAdjac.forEach(function(element){

     if(couleur.get(Number(element)) != "noir")
     {
       distanceElement = d.get(u) + Number(listDistanceArc.get(u+","+element))
       if(distanceD.get(Number(element))> distanceElement)
       {
         couleur.set(Number(element),"gris")
         distanceD.set(Number(element),distanceElement)
         antecedant.set(Number(element),u)
         if(!isExist(ListSommetAccessible,Number(element)))
         {
           ListSommetAccessible.push(Number(element));
         }
       }
     }
   });
   couleur.set(u,"noir")
  }
}
function deleteByValue(array,value)
{
  newArray = Array()
  for(let index in array)
  {
    if(value != array[index])
    newArray.push(array[index]);
  }
  return newArray;
}
function isExist(array,value)
{
  newArray = Array()
  for(let index in array)
  {
    if(value == array[index])
    return true
  }
  return false
}
function getMin(f,distance)
{
  let min = Number.MAX_VALUE;
  let elementMin = f[0]
  f.forEach(function(element){
    if(distance.get(Number(element)) < min)
    {
      min = distance.get(Number(element))
      elementMin = element
    }
  })
  return elementMin;
}
function getAdjacent(couleur,listVectAdjacent,array,sommet)
{

  let adjTab = listVectAdjacent.get(Number(sommet))
  adjTab.forEach(function(element){
    if(couleur.get(Number(element)) != "noir")
  array.push(Number(element))
  })
  return array
}
function imprimerChemin(s,v,antecedant,coordinates)
{

  if(v == s)
  {
    for (var indexPoint in jsonBasePoint)
    {
      if(jsonBasePoint[indexPoint].GEO_POI_ID == v)
      {
        coordinates.push(""+jsonBasePoint[indexPoint].GEO_POI_LONGITUDE+","+jsonBasePoint[indexPoint].GEO_POI_LATITUDE+",0")

      }
    }
    console.log(s)
  }
  else
  {
    if(antecedant.get(v) == null)
    {
      console.log("Il existe aucun chemin de "+ s + " à "+ v)
    }
    else
    {
      for (var indexPoint in jsonBasePoint)
      {
        if(jsonBasePoint[indexPoint].GEO_POI_ID == v)
        {
          coordinates.push(""+jsonBasePoint[indexPoint].GEO_POI_LONGITUDE+","+jsonBasePoint[indexPoint].GEO_POI_LATITUDE+",0")

        }
      }
      imprimerChemin(s,antecedant.get(v),antecedant,coordinates)
      console.log(v)
    }
  }
}
function generateKml()
{
  let cordo = ""
  for (var index in coordinates)
  {
    cordo = cordo + '\n' + coordinates[index]
  }
  let kmlText = "<\?xml version='1.0' encoding='UTF-8'?>\n<kml xmlns='http://earth.google.com/kml/2.0'>\n<Document>\n<Placemark>\n<LineString>\n<coordinates>\n" + cordo + "</coordinates>\n</LineString>\n</Placemark>\n</Document>\n</kml>"
  let url = 'toolsFilesSig.php';

  let fileName = Math.random().toString(36).substring(7) + ".kml";

  let form = document.createElement("form");
  form.setAttribute("method","post");
  form.setAttribute("target", "_blank");
  form.setAttribute("action", url);
  form.setAttribute("id","kmlgenerator");

  let hiddenField = document.createElement("textarea");
  hiddenField.setAttribute("name", "content");
  hiddenField.appendChild(document.createTextNode(kmlText));

  let hiddenField1 = document.createElement("input");
  hiddenField1.setAttribute("name", "fileName");
  hiddenField1.setAttribute("value",fileName);

  form.appendChild(hiddenField);
  form.appendChild(hiddenField1);

  document.body.appendChild(form);

  form.submit();

  document.body.removeChild(form);

  var site = "https://www.doogal.co.uk/KmlViewer.php?url=https://coopuniverse.fr/work/file/"+fileName;
  document.getElementById('iframeTest').src = site;
}
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
function arcVecAdjac()
{
  for (var departPoint in listPoint)
  {
    arcVectAdja = [];
    for (var finalPoint in jsonBaseArc)
    {

      if(listPoint[departPoint] == jsonBaseArc[finalPoint].GEO_ARC_DEB)
      {
        arcVectAdja.push(jsonBaseArc[finalPoint].GEO_ARC_FIN);
      }
      if(listPoint[departPoint] == jsonBaseArc[finalPoint].GEO_ARC_FIN)
      {
        arcVectAdja.push(jsonBaseArc[finalPoint].GEO_ARC_DEB);
      }
    }
    listVectAdja.set(Number(listPoint[departPoint]),arcVectAdja);
  }
}
</script>





<?php

$script = ob_get_clean();
include_once('../include/template.php'); ?>
