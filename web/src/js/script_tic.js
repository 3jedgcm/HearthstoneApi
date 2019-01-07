//Const//
const ID_SIZE_X = "size_x";
const ID_SIZE_Y = "size_y";
const ID_RULE_ELEMENT = "turn";
const ID_TABLE = "idTable";
const ID_ERROR = "error";
let isPlayerTurn = true;
let tab = [];
let x = 0;
let y = 0;
let winner = false;
let size_x;
let size_y;
let nbOfPlay = 0;

function nouvellePartie()
{
  nbOfPlay = 0;
  win = false;
  tab = [];
  let isPlayerTurn = true;
  size_x = parseInt(document.getElementById(ID_SIZE_X).value);
  size_y = parseInt(document.getElementById(ID_SIZE_Y).value);
  document.getElementById(ID_RULE_ELEMENT).textContent = isPlayerTurn ? "Au tour du joueur 1 " : "Au tour du joueur 2 " ;
  if(size_x < 25 && size_y < 25 && size_x > 2 && size_y > 2)
  {
    document.getElementById(ID_SIZE_X).disabled = true;
    document.getElementById(ID_SIZE_Y).disabled = true;
    generateBoard(size_x,size_y);
    display(size_x,size_y);
  }
  else
  {
    document.getElementById(ID_ERROR).innerHTML = "Le format est invalide ";
  }

}

function generateBoard(size_x,size_y)
{
  let tabElement = document.getElementById(ID_TABLE);
  let caseCounter = 0;
  let trElement;
  let childHtml;
  let tabTemp;
  tabElement.innerHTML = "";
  for(let counter = 0 ; counter < size_x ; counter++)
  {
    trElement = document.createElement('tr');
    trElement.id = 'line' + counter;
    tabElement.appendChild(trElement);
    tabTemp = [];
    for(let secondCounter = 0 ; secondCounter < size_y ; secondCounter ++)
    {
      childHtml = document.createElement('td');
      childHtml.id = 'case' + caseCounter;
      trElement.appendChild(childHtml);
      caseCounter++;
      tabTemp.push(0);
    }
    tab.push(tabTemp);
  }
}

function display(size_x,size_y)
{
  let n = 0;
  let myElement;
  let v = "b"+n;
  let sizePixel_x = (400/size_x) ;
  let sizePixel_y = (400/size_y) ;
  for(let counter = 0; counter < size_x; counter++)
  {
    for(let secondCounter = 0; secondCounter < size_y; secondCounter++)
    {
      numCase = (size_y*counter)+secondCounter;
      myElement = document.getElementById("case"+numCase);
      switch(tab[counter][secondCounter])
      {
        case 0:
          myElement.innerHTML = "<img src='/src/img/blanc.png' style='width: "+ sizePixel_x +"px; height: "+ sizePixel_y + "px;'  id='b"+numCase+"' onClick='ajoutPoint("+ counter +","+ secondCounter +")'>";
        break;
        case 1:
          myElement.innerHTML = "<img class='img' style='width: "+ sizePixel_x +"px; height: "+ sizePixel_y + "px;' src='/src/img/croix.jpg'>";
        break;
        case 2:
          myElement.innerHTML = "<img class='img' style='width: "+ sizePixel_x +"px; height: "+ sizePixel_y + "px;' src='/src/img/rond.png'>";
        break;
      }
    }
  }
}




function returnLobby()
{
  window.location.href = 'https://coopuniverse.fr/';
}


function ajoutPoint(x,y)
{
  console.log(win);
  if(!win)
  {
  size_x = parseInt(document.getElementById(ID_SIZE_X).value);
  size_y = parseInt(document.getElementById(ID_SIZE_Y).value);
  isPlayerTurn = !isPlayerTurn;
  tab[x][y] = isPlayerTurn ? 1 : 2;
  display(size_x,size_y);
  document.getElementById(ID_RULE_ELEMENT).textContent = isPlayerTurn ? "Au tour du joueur 1 " : "Au tour du joueur 2 " ;
  testGagner(x,y,size_x,size_y);
  }
}



function testGagner(x,y,size_x,size_y)
{
  let virutalizeTab = [[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0]];
  for(let counter = 0 ; counter < virutalizeTab.length ; counter++)
  {
    for(let secondCounter = 0 ; secondCounter < virutalizeTab.length ; secondCounter ++)
    {
      if((x-2+counter) >= 0 &&  (y-2+secondCounter) >= 0 && (x-2+counter) < size_x && (y-2+secondCounter) < size_y)
      {
        virutalizeTab[counter][secondCounter] = tab[x-2+counter][y-2+secondCounter];
      }
    }
  }


  let isPlayerTurnNumber = isPlayerTurn ? 1 : 2;

  if(virutalizeTab[1][1] == isPlayerTurnNumber && virutalizeTab[1][2] == isPlayerTurnNumber && virutalizeTab[1][3] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[2][1] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[2][3] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[3][1] == isPlayerTurnNumber && virutalizeTab[3][2] == isPlayerTurnNumber && virutalizeTab[3][3] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[1][1] == isPlayerTurnNumber && virutalizeTab[2][1] == isPlayerTurnNumber && virutalizeTab[3][1] == isPlayerTurnNumber) {win = true;}
  if(virutalizeTab[1][2] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[3][2] == isPlayerTurnNumber) {win = true;}
  if(virutalizeTab[1][3] == isPlayerTurnNumber && virutalizeTab[2][3] == isPlayerTurnNumber && virutalizeTab[3][3] == isPlayerTurnNumber) {win = true;}
  if(virutalizeTab[1][1] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[3][3] == isPlayerTurnNumber) {win = true;}
  if(virutalizeTab[3][1] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[1][1] == isPlayerTurnNumber) {win = true;}
  if(virutalizeTab[0][0] == isPlayerTurnNumber && virutalizeTab[1][1] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[0][2] == isPlayerTurnNumber && virutalizeTab[1][2] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[0][4] == isPlayerTurnNumber && virutalizeTab[1][3] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[2][0] == isPlayerTurnNumber && virutalizeTab[2][1] == isPlayerTurnNumber && virutalizeTab[2][2] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[2][3] == isPlayerTurnNumber && virutalizeTab[2][4] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[3][1] == isPlayerTurnNumber && virutalizeTab[4][0] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[3][2] == isPlayerTurnNumber && virutalizeTab[4][2] == isPlayerTurnNumber) {win = true}
  if(virutalizeTab[2][2] == isPlayerTurnNumber && virutalizeTab[3][3] == isPlayerTurnNumber && virutalizeTab[4][4] == isPlayerTurnNumber) {win = true}
if(win)
{
  if(isPlayerTurnNumber == 2)
  {

    let myTextElement = document.getElementById(ID_RULE_ELEMENT);
    myTextElement.textContent = "J1 gagnant";
  }
  else {
    let myTextElement = document.getElementById(ID_RULE_ELEMENT);
    myTextElement.textContent = "J2 gagnant";
  }
}

  nbOfPlay++;
  console.log(nbOfPlay);
  if(nbOfPlay >= (size_x*size_y))
  {
    let myTextElement = document.getElementById(ID_RULE_ELEMENT);
    myTextElement.textContent = "Aucun gagnant";
    win = true
  }


}
