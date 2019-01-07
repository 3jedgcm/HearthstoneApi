<?php



$sizeX = 15;
$sizeY = 15;
$index = 1;

require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers

$CellDAO->resetMap();
$CellDAO->insertCell("1");
$sizeArray = array();






for ($counter = 1; $counter < $sizeX*$sizeY; $counter++)
{
  $content["id"] = -1;
  while($content["id"] == -1 || $content["id"] == 1 || $content["id"] == 10)
  {
      $content = $CellDAO->getRandomContent();
  }
  $CellDAO->insertCell($content["id"]);
}


for ($counter = 1; $counter < $sizeX+1; $counter++)
{
  if($counter == 1)
  {
    $CellDAO->insertLink(1,($sizeX*($sizeY-1))+1,$sizeX+1,2,$sizeX);
  }
  else if($counter == $sizeX)
  {
    $CellDAO->insertLink($sizeX,($sizeX*($sizeY-1))+$sizeX,$sizeX+$sizeX,1,$sizeX-1);
  }
  else
  {
    $CellDAO->insertLink($counter,($sizeX*($sizeY-1))+$counter,$sizeX+$counter,$counter+1,$counter-1);
  }
}

for ($lineCounter = 1; $lineCounter < $sizeY; $lineCounter++)
{
  for ($counter = 1; $counter < $sizeX+1; $counter++)
  {
    if($counter == 1)
    {
      $CellDAO->insertLink(
      1+($lineCounter*$sizeX),
      (($lineCounter-1)*$sizeX)+1,
      (($lineCounter+1)*$sizeX)+1,
      ($lineCounter*$sizeX)+2,
      $sizeX*($lineCounter+1));
    }
    else if($counter == $sizeX)
    {
      $CellDAO->insertLink(
        ($lineCounter*$sizeX)+$counter,
        (($lineCounter-1)*$sizeX)+$counter,
        (($lineCounter+1)*$sizeX)+$counter,
        1+($lineCounter*$sizeX),
        $counter+($lineCounter*$sizeX)-1);
    }
    else
    {
      $CellDAO->insertLink(
            ($lineCounter*$sizeX)+$counter,
            (($lineCounter-1)*$sizeX)+$counter,
            (($lineCounter+1)*$sizeX)+$counter,
            $counter+($lineCounter*$sizeX)+1,
            $counter+($lineCounter*$sizeX)-1);
    }
  }
}

for ($counter = 1; $counter < $sizeX+1; $counter++)
{
  if($counter == 1)
  {
    $CellDAO->insertLink(($sizeX*($sizeY-1))+$counter,($sizeX*($sizeY-2))+$counter,$counter,($sizeX*($sizeY-1))+$counter+1,$sizeX*$sizeY);
  }
  else if($counter == $sizeX)
  {
    $CellDAO->insertLink($sizeX*$sizeY,($sizeX*($sizeY-2))+$counter,$counter,($sizeX*($sizeY-1))+1,($sizeX*$sizeY)-1);
  }
  else
  {
    $CellDAO->insertLink(($sizeX*($sizeY-1))+$counter,($sizeX*($sizeY-2))+$counter,$counter,($sizeX*($sizeY-1))+$counter+1,($sizeX*($sizeY-1))+$counter-1);
  }
}

$CellDAO->insertSuperCell(10);
