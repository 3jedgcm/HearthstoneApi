<?php
/*********************/
/** MONEY FUNCTION ***/
/********************/

function setMoney($pDAO,$idUser,$newValueMoney) //100%
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["error"] = $pDAO["User"]->checkIdUser($idUser);
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

function getMoney($pDAO,$idUser) ////100%
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["money"] = $pDAO["User"]->getAllMoney();
  }
  else
  {
    $resultat["error"] = $pDAO["User"]->checkIdUser($idUser);
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

function getParam($pDAO) //100%
{
  $resultat["param"] = $pDAO["Param"]->getAll();

  if($resultat["param"] == -1)
  {
    $resultat["error"] = EXIT_CODE_ERROR_SQL;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_OK;
  }

  return $resultat;
};


/*********************/
/*** FUNCTION USER***/
/*********************/

function getUser($pDAO,$idUser) //100%
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idUser == "")
  {
    $resultat["user"] = $pDAO["User"]->getAll();
  }
  else
  {
    $resultat["error"] = $pDAO["User"]->checkIdUser($idUser);
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
    $resultat["error"] = $pDAO["User"]->checkIdUser($idUser);
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

function getCard($pDAO,$idCard) //100%
{
  $resultat["error"] = EXIT_CODE_OK;
  if($idCard == "")
  {
    $resultat["card"] = $pDAO["Card"]->getAll();
  }
  else
  {
    $resultat["error"] = $pDAO["Card"]->checkIdCard($idCard);
    if($resultat["error"] == 0)
    {
      $resultat["card"] = $pDAO["Card"]->getOne($idCard);
    }
    else
    {
      $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;
    }
    if($resultat["card"] == false)
    {
      $resultat["error"] = EXIT_CODE_ERROR_SQL;
    }
  }

  return $resultat;
};

function setCardInInventoryByUserId($pDAO,$idUser,$idCard)
{
  $resultat["error"] = EXIT_CODE_OK;

  $resultat["error"] = $pDAO["Inventory"]->insert($idCard,$idUser);

  if($resultat["error"])
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_ERROR_INSERTION_IN_DB;
  }
  return $resultat;

};

function exchangeCard($pDAO,$idUser,$idUser_secondary,$cards,$cards_secondary)
{
  $resultat["error"] = EXIT_CODE_NO_IMPLEMENTED_FUNCTION;

  return $resultat;
};

function getRandomCard($pDAO) //100%
{
  $resultat["error"] = EXIT_CODE_OK;
    $resultat["randomCard"] = $pDAO["Card"]->getRandomCard($idUser);
    if($resultat["randomCard"] == -1)
    {
      $resultat["error"] = EXIT_CODE_ERROR_SQL;
    }
  return $resultat;
};


function deleteCardByUserid($pDAO,$idUser,$idCards) //OK
{
  $resultat["error"] = $pDAO["User"]->checkIdUser($idUser);
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

/********************/
/* FUNCTION CONNECT */
/********************/

function connect($pDAO,$pLogin,$pPass,$pKey,$pMode)
{
  $resultat["error"] = EXIT_CODE_OK;
  if($pMode == "facebook")
  {
    $resultat["connect"] = $pDAO["User"]->checkAccountWithFacebook($pKey);
  }
  else if($pMode == "google")
  {
    $resultat["connect"] = $pDAO["User"]->checkAccountWithGoogle($pKey);
  }
  else if($pMode == "")
  {
    $resultat["connect"] = $pDAO["User"]->checkAccountWithBase($pLogin,$pPass);
  }
  else
  {
    $resultat["connect"] = false;
    $resultat["error"] = EXIT_CODE_TOO_LONG_URI;
  }
  return $resultat;
}

function register($pDAO,$pLogin,$pPass,$pKey,$pMode)
{
  $resultat["error"] = EXIT_CODE_OK;
  if($pMode == "facebook")
  {
    $checking = $pDAO["User"]->checkAccountWithFacebook($pKey);
    if($checking == true)
    {
      $resultat["error"] = EXIT_CODE_FACEBOOK_ACCOUNT_EXIST;
    }
    else
    {
      $resultat["connect"] = $pDAO["User"]->registerWithFacebook($pKey);
    }
  }
  else if($pMode == "google")
  {
    $checking = $pDAO["User"]->checkAccountWithGoogle($pKey);
    if($checking == true)
    {
      $resultat["error"] = EXIT_CODE_GOOGLE_ACCOUNT_EXIST;
    }
    else
    {
      $resultat["connect"] = $pDAO["User"]->registerWithGoogle($pKey);
    }
  }
  else if($pMode == "")
  {
    $checking = $pDAO["User"]->checkAccountWithBase($pLogin,$pPass);
    
    if($checking == true)
    {
      $resultat["error"] = EXIT_CODE_LOGIN_EXIST;
    }
    else
    {
      $resultat["connect"] = $pDAO["User"]->registerWithBase($pLogin,$pPass);
    }
  }
  else
  {
    $resultat["connect"] = false;
    $resultat["error"] = EXIT_CODE_TOO_LONG_URI;
  }
  return $resultat;
}

function linkAccount($pDAO,$pLogin,$pPass,$pKey,$pMode)
{
  $resultat["error"] = EXIT_CODE_OK;
  if($pMode == "facebook")
  {
    $resultat["connect"] = $pDAO["User"]->checkAccountWithFacebook($pKey);
  }
  else if($pMode == "google")
  {
    $resultat["connect"] = $pDAO["User"]->checkAccountWithGoogle($pKey);
  }
  else if($pMode == "")
  {
    $resultat["connect"] = $pDAO["User"]->checkAccountWithBase($pLogin,$pPass);
  }
  else
  {
    $resultat["connect"] = false;
    $resultat["error"] = EXIT_CODE_TOO_LONG_URI;
  }
  return $resultat;
}
