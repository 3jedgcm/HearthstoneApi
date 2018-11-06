<?php
/*********************/
/** MONEY FUNCTION ***/
/********************/

function setMoney($pDAO,$idUser,$newValueMoney)
{
  if($idUser == "")
  {
    $resultat["money"] = $pDAO["User"]->setAllMoney(intval($newValueMoney));
  }
  else
  {
    $resultat["money"] = $pDAO["User"]->setMoney($idUser,intval($newValueMoney));
  }
  if($resultat["money"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_MONEY_SETTER;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;

}

function getMoney($pDAO,$idUser)
{
  if($idUser == "")
  {
    $resultat["money"] = $pDAO["User"]->getAllMoney();
  }
  else
  {
    $resultat["money"] = $pDAO["User"]->getMoney($idUser);
  }
  if($resultat["money"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_MONEY_READ;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;

}

/******************/
/* FUNCTION PARAM  */
/******************/

function getParam($pDAO)
{
  $resultat["param"] = $pDAO["Param"]->getAll();

  if($resultat["param"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_USER_READ;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;
}

function setParam($pDAO)
{
  $resultat["param"] = $pDAO["Param"]->getAll();

  if($resultat["user"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_USER_READ;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;
}

/*********************/
/*** FUNCTION USER***/
/*********************/

function getUser($pDAO,$idUser)
{
  if($idUser == "")
  {
    $resultat["user"] = $pDAO["User"]->getAll();
  }
  else
  {
    $resultat["user"] = $pDAO["User"]->getOne($idUser);
  }
  if($resultat["user"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_USER_READ;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;
}

function setUser($pDAO,$idUser)
{
  if($idUser == "")
  {
    $resultat["user"] = $pDAO["User"]->getAll();
  }
  else
  {
    $resultat["user"] = $pDAO["User"]->getOne($idUser);
  }
  if($resultat["user"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_USER_READ;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  return $resultat;
}

/******************/
/* FUNCTION CARDS  */
/******************/

function getOneCardByUserId(){};

function ggetAllCard(){};

function setOneCardByUserId(){};

function exchangeCards($pDAO,$idUser,$idUser_secondary,$cards,$cards_secondary){}

function getRandomCard(){
  $card = getRandomCard();
  if(isset($card))
  {
    if($pDAO["Card"]->insert($card,$idUser))
    {
      return EXIT_CODE_OK;
    }
    else
    {
      return EXIT_CODE_ERROR_INSERTION_IN_DB;
    }
  }
  else
  {
    return EXIT_CODE_RANDOM_CARD_IS_NULL;
  }
};

function deleteOneCardByUserid($pDAO,$idUser,$cards){
  if(isset($cards) && $cards != null)
  {
    $error = EXIT_CODE_OK;
    foreach($cards as $card)
    {
      if($pDAO["Card"]->checkExist($card,$idUser))
      {
        $pDAO["Card"]->delete($card,$idUser);
      }
      else
      {
        $error = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
      }
    }
    return $error;
  }
  else
  {
      return EXIT_CODE_CARDS_MISSING_IN_PARAMETTER;
  }
}

/******************/
/* FUNCTION OTHER  */
/******************/


function fusion($pDAO,$idUser,$cards)
{
  return EXIT_CODE_OK;
}

function forge($pDAO,$idUser,$cards)
{
  return EXIT_CODE_OK;
}

function getAnswer($pDAO)
{
  return EXIT_CODE_OK;
  //Voir API Goub
}
