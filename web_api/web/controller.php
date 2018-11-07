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

function getCard($pDAO,$idUser){};

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

function deleteCardByUserid($pDAO,$idUser,$idCards){
  if($idCards != "")
  {
      if($pDAO["Card"]->checkExist($cidCard,$idUser))
      {
        $pDAO["Card"]->delete($idCard,$idUser);
      }
      else
      {
        $error = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
      }

    return $error;
  }
  else
  {
      $pDAO["Card"]->deleteAll($idUser);
  }
}

/******************/
/* FUNCTION OTHER  */
/******************/


function craftOneCard($pDAO,$idUser,$cards)
{
  return EXIT_CODE_OK;
}

function meltCards($pDAO,$idUser,$cards)
{
  return EXIT_CODE_OK;
}

function getQuestion($pDAO)
{
  return EXIT_CODE_OK;
  //Voir API Goub
}

function setAnswer($pDAO)
{
  return EXIT_CODE_OK;
  //Voir API Goub
}

function getInventory($pDAO,$idUser)
{
  return $pDAO["Card"]->getAll($idUser);
}
