var img;
var imgMask;
var task = "null";
var onEnter = true;
var dirEnter = "null"
var x = 235;
var y = 235;
var vit = 2;

var idCurrentCell = "1"
var idUpCell = "1";
var idDownCell = "1";
var idRightCell = "1";
var idLeftCell = "1";
var background_src = "tile_spawn";
var lastCell = "";
var idKey = "osef"
var isDead = false;
var life = "0";
var shield = "0";
var credit = "0";






function preload() {

  imgCharacter = loadImage("../../src/img/shape/caractere.png");
  sendData("","start");
}

function setup() {

  imgTile = loadImage("../../src/img/shape/"+ background_src + ".png");
  createCanvas(500, 500);


}

function draw() {



  if(onEnter == true && dirEnter == "UP")
  {
    y = y + vit;
  }
  if(onEnter == true && dirEnter == "LEFT")
  {
    x = x + vit;
  }
  if(onEnter == true && dirEnter == "RIGHT")
  {
    x = x - vit;
  }
  if(onEnter == true && dirEnter == "DOWN")
  {
    y = y - vit;
  }
  if(y == 235 && x == 235 && onEnter == true)
  {
    onEnter = false;
  }

  if(onEnter == false && task == "UP" && !isDead)
  {
    if(y < 0)
    {
      x = 235;
      y = 501;
      dirEnter = "DOWN"
      onEnter = true;
      task = "null"
      sendData(idUpCell);
    }
    y = y - vit;
  }
  if(onEnter == false && task == "LEFT" && !isDead)
  {
    if(x < 0)
    {
      x = 501;
      y = 235;
      dirEnter = "RIGHT"
      onEnter = true;
      task = "null"
      sendData(idLeftCell);
    }
    x = x - vit;
  }
  if(onEnter == false && task == "RIGHT" && !isDead)
  {
    if(x > 501)
    {
      x = 1;
      y = 235;
      dirEnter = "LEFT"
      onEnter = true;
      task = "null"
      sendData(idRightCell);
    }
    x = x + vit;
  }
  if(onEnter == false && task == "DOWN" && !isDead)
  {
    if(y > 501)
    {
      dirEnter = "UP"
      x = 235;
      y = 1;
      onEnter = true;
      task = "null"
      sendData(idDownCell);
    }
    y = y + vit;

  }

  background(imgTile);
  image(imgCharacter, x,y,50,50);
  if(isDead)
  {
    textSize(35);
    fill(75)
    stroke(0);
    rect(0,100, 500, 100);
    fill(255);
    text("Vous êtes mort !", 100, 165);
  }


  textSize(25);
  fill(75)
  stroke(0);
  rect(0,450, 500, 500);
  fill(255);
  text("Vie: ", 5, 485);
  text(life+"/100", 55, 485);
  text("Armure: ", 175, 485);
  text(shield, 265, 485);
  text("Crédit: ", 360, 485);
  text(credit, 435, 485);

  if (mouseIsPressed && task == "null" && onEnter == false && !isDead){

    if(mouseX > 195 && mouseX < 320 && mouseY < 150 && mouseY > 0 )
    task = "UP";
    if(mouseX > 0 && mouseX < 150 && mouseY > 195 && mouseY < 320 )
    task = "LEFT";
    if(mouseX > 380 && mouseX < 500 && mouseY < 320 && mouseY > 195 )
    task = "RIGHT";
    if(mouseX > 195 && mouseX < 320 && mouseY > 380 && mouseY < 500 )
    task = "DOWN";
  }
}


function sendData(idCell,isStart)
{
  if(isStart == "start")
  {
    $.ajax({
      url: 'https://api.shape.coopuniverse.fr',
      data: {"idKey":idKey,"idAccount":idAccount},
      type: 'POST',
      crossDomain: true,
      dataType: 'json',
      success: function(data)
      {
        console.log(data.result.cell.src);
        background_src = data.result.cell.src
        idUpCell = data.result.cell.link[0];
        idDownCell = data.result.cell.link[1];
        idRightCell = data.result.cell.link[2];
        idLeftCell = data.result.cell.link[3];
        setup();
      },
      error: function() { alert('Failed!'); },
    });

  }
  else
  {
    $.ajax({
      url: 'https://api.shape.coopuniverse.fr',
      data: {"idCell":idCell,"idKey":idKey,"idAccount":idAccount},
      type: 'POST',
      crossDomain: true,
      dataType: 'json',
      success: function(data) {

        if(data.error == undefined)
        {
          lastCell = idCell;
          background_src = data.result.cell.src
          idUpCell = data.result.cell.link[0];
          idDownCell = data.result.cell.link[1];
          idRightCell = data.result.cell.link[2];
          idLeftCell = data.result.cell.link[3];

          life  = data.result.character.life
          shield  = data.result.character.shield
          credit  = data.result.credit.credit
          if(data.result.isDead)
          {
            isDead = true;
          }



          console.log(data);
          setup();
        }
        else
        {
          console.log(data.error);
          $.ajax({
            url: 'https://api.shape.coopuniverse.fr',
            data: {"idKey":idKey,"idAccount":idAccount},
            type: 'POST',
            crossDomain: true,
            dataType: 'json',
            success: function(data)
            {
              idUpCell = data.result.cell.link[0];
              idDownCell = data.result.cell.link[1];
              idLeftCell = data.result.cell.link[2];
              idRightCell = data.result.cell.link[3];
              console.log(data);
              setup();
            },
            error: function() { alert('Failed!'); },
          });
        }
      },
      error: function() { alert('Votre compte a bien été créé!'); },
    });
  }

}
