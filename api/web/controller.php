<?php
/*********************/
/** MONEY FUNCTION ***/
/********************/

function setMoney($pDAO,$idUser,$newValueMoney) //OK
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["error"] = checkIdUser($pDAO,$idUser);
    if($resultat["error"] == 0)
    {
      $resultat["money"] = $pDAO["User"]->setAllMoney(intval($newValueMoney));
    }
    else
    {
      $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
    }
  }
  else
  {
    $resultat["money"] = $pDAO["User"]->setMoney($idUser,intval($newValueMoney));
  }
  if($resultat["money"] == -1)
  {
    $resultat["error"] = EXIT_CODE_ERROR_SQL;
  }
  return $resultat;

};

function getMoney($pDAO,$idUser) //OK
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["money"] = $pDAO["User"]->getAllMoney();
  }
  else
  {
    $resultat["error"] = checkIdUser($pDAO,$idUser);
    if($resultat["error"] == 0)
    {
      $resultat["money"] = $pDAO["User"]->getMoney($idUser);
    }
    else
    {
      $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
    }

  }
  if($resultat["money"] == -1)
  {
    $resultat["error"] = EXIT_CODE_ERROR_SQL;
  }
  return $resultat;

};

/******************/
/* FUNCTION PARAM  */
/******************/

function getParam($pDAO) //OK
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;

  $resultat["param"] = $pDAO["Param"]->getAll();

  if($resultat["param"] == -1)
  {
    $resultat["error"] = EXIT_CODE_ERROR_SQL;
  }
  return $resultat;
};


/*********************/
/*** FUNCTION USER***/
/*********************/

function getUser($pDAO,$idUser) //OK
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["user"] = $pDAO["User"]->getAll();
  }
  else
  {
    $resultat["error"] = checkIdUser($pDAO,$idUser);
    if($resultat["error"] == 0)
    {
      $resultat["user"] = $pDAO["User"]->getOne($idUser);
    }
    else
    {
      $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
    }
  }
  if($resultat["user"] == -1)
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_USER_READ;
  }
  return $resultat;
};


function setUser($pDAO,$idUser) //OK
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["user"] = $pDAO["User"]->getAll();
  }
  else
  {
    $resultat["error"] = checkIdUser($pDAO,$idUser);
    if($resultat["error"] == 0)
    {
      $resultat["user"] = $pDAO["User"]->getOne($idUser);
    }
    else
    {
      $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
    }
  }
  if($resultat["user"] == -1)
  {
    $resultat["error"] = EXIT_CODE_ERROR_SQL;
  }
  return $resultat;
};

/******************/
/* FUNCTION CARDS  */
/******************/

function getCard($pDAO,$idUser) //OK
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["card"] = $pDAO["Card"]->getAll();
  }
  else
  {
    $resultat["error"] = checkIdUser($pDAO,$idUser);
    if($resultat["error"] == 0)
    {
      $resultat["card"] = $pDAO["Card"]->getOne($idUser);
    }
    else
    {
      $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
    }
    if($resultat["card"] == -1)
    {
      $resultat["error"] = EXIT_CODE_ERROR_SQL;
    }
  }

  return $resultat;
};

function setCardByUserId($pDAO,$idUser,$cards)
{
  $resultat["error"] = EXIT_CODE_OK;
  return $resultat;
};

function exchangeCards($pDAO,$idUser,$idUser_secondary,$cards,$cards_secondary)
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  return $resultat;
};

function getRandomCard($pDAO,$idUser) //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_OK;
  if(isset($card))
  {
    if($pDAO["Card"]->insert($card,$idUser))
    {
      return EXIT_CODE_OK;
    }
    else
    {
      return EXIT_CODE_ERROR_SQL;
    }
  }
  else
  {
    return EXIT_CODE_RANDOM_CARD_IS_NULL;
  }
  return $resultat;
};


function deleteCardByUserid($pDAO,$idUser,$idCards) //OK
{
  $resultat["error"] = checkIdUser($pDAO,$idUser);
  if($resultat["error"] == 0)
  {
    if($idCards != "")
    {
      if($pDAO["Card"]->checkExist($cidCard,$idUser))
      {
        $pDAO["Card"]->delete($idCard,$idUser);
      }
      else
      {
        $resultat["error"] = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
      }
    }
    else
    {
      $pDAO["Card"]->deleteAll($idUser);
    }
  }
  else
  {
    $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
  }
  return $resultat;
}

/******************/
/* FUNCTION OTHER  */
/******************/


function craftOneCard($pDAO,$idUser,$cards)  //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  return $resultat;
}

function meltCards($pDAO,$idUser,$cards)   //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  return $resultat;
}

function getQuestion($pDAO)  //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  return $resultat;
  //Voir API Goub
}

function setAnswer($pDAO,$idUser,$numAnswer,$idQuestion)  //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  return $resultat;
  //Voir API Goub
}

function getInventory($pDAO,$idUser)  //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  return $resultat;
  return $pDAO["Card"]->getAll($idUser);
}

/******************/
/* FUNCTION CONNECT  */
/******************/

function connect($pDAO,$login,$pass)  //Non Fonctionnel
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;
  $resultat["account"] = $pDAO["User"]->checkAccount($login,$pass);
  return $resultat;

}
