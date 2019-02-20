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

function setUser($pDAO,$idUser) //100%
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

function getCard($pDAO,$idCard,$typeFilter,$valueFilter)  //100%
{
  $resultat["error"] = EXIT_CODE_OK;

  if($idCard == "")
  {
    if($typeFilter == "")
    $resultat["card"] = $pDAO["Card"]->getAll();
    else
    $resultat["card"] = $pDAO["Card"]->getAllByFilter($typeFilter,$valueFilter);
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

function setCardInInventoryByUserId($pDAO,$idUser,$idCard)  //100%
{

  $resultat["error"] = EXIT_CODE_OK;

  $resultat["error"] = $pDAO["Inventory"]->insert($idCard,$idUser);

  if($resultat["error"] == false)
  {
    $resultat["error"] = EXIT_CODE_OK;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_ERROR_INSERTION_IN_DB;
  }
  return $resultat;

};

function exchangeCard($pDAO,$idUser,$idUser_secondary,$idCard,$idCard_secondary)  //100%
{

  $flagUsersCardsExists = false;
  $flagUsersCardsInInventory = false;
  $resultatUsers = $pDAO["Inventory"]->usersExist($idUser,$idUser_secondary);
  if($resultatUsers)
  {
    return $resultat["error"] = EXIT_CODE_INCORRECT_ID_USER;

  }
  $resultatCardsOne = $pDAO["Card"]->checkIdCard($idCard);
  $resultatCardsTwo = $pDAO["Card"]->checkIdCard($idCard_secondary);
  if(!$resultatCardsOne)
  {
    $resultat["error"] = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
    return $resultat;
  }
  if(!$resultatCardsTwo)
  {
    $resultat["error"] = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
    return $resultat;
  }
  $resultatCardsPlayer = $pDAO["Inventory"]->checkInventoryExist($idCard,$idUser); //Check InventoryidUserOne
  if($resultatCardsPlayer)
  {
    $resultat["error"] = EXIT_CODE_INVENTORY_IS_MISSING;
    return $resultat;
  }
  $resultatCardsPlayerSecondary = $pDAO["Inventory"]->checkInventoryExist($idCard_secondary,$idUser_secondary); //Check InventoryidUserTwo

  if($resultatCardsPlayerSecondary)
  {
    $resultat["error"] = EXIT_CODE_INVENTORY_IS_MISSING;
    return $resultat;
  }
  $resultatDeleteCards = $pDAO["Inventory"]-> deleteInventory($idUser,$idUser_secondary,$idCard,$idCard_secondary);
  if($resultatDeleteCards)
  {
    $resultat["error"] = EXIT_CODE_ERROR_SQL;
    return $resultat;
  }
  if ($pDAO["Inventory"]->exchange($idUser,$idUser_secondary,$idCard,$idCard_secondary) <> EXIT_CODE_OK)
  {
    $resultat["error"] = EXIT_CODE_EXCHANGE_NOT_WORKING;
    return $resultat;
  }

  $idInventory=0;
  $type ="DELETE_REC";
  $details= "";
  $price = 0;
  $date= getdate();
  $pDateformat = $date[mday]."/".$date[mon]."/".$date[year]."/".$date[hours].":".$date[minutes] ;
  $resultatInsertLog1 = $pDAO["Inventory"]-> insertTransaction_History($idCard,$idCard_secondary,$idUser,$idUser_secondary,$type,$details, $idInventory, $price ,$pDateformat);
  $type = "INSERT_REC" ;
  $resultatInsertLog2 = $pDAO["Inventory"]-> insertTransaction_History($idCard,$idCard_secondary,$idUser,$idUser_secondary,$type,$details, $idInventory, $price ,$pDateformat);
  $resultat["error"] = EXIT_CODE_OK;
  return $resultat;
}

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

function deleteCardByUserid($pDAO,$idUser,$idCards)  //100%
{
  $resultat["error"] = $pDAO["User"]->checkIdUser($idUser);
  if($resultat["error"] == 0)
  {
    if($idCards != "")
    {
      if($pDAO["Card"]->checkIdCard($idCards) == EXIT_CODE_OK)
      {
        if($pDAO["Inventory"]->checkInventoryExist($idCards,$idUser) == EXIT_CODE_OK)
        {
          $pDAO["Inventory"]->delete($idCards,$idUser);
        }
        else
        {
          $resultat["error"] = EXIT_CODE_INVENTORY_IS_MISSING;
        }
      }
      else
      {
        $resultat["error"] = EXIT_CODE_CARDS_IS_MISSING_IN_DB;
      }
    }
    else
    {
      $pDAO["Inventory"]->deleteAll($idUser);
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

function craftOneCard($pDAO,$idUser,$idCardOne,$idCardTwo,$idCardThree) //100%
{
  $isExistOne = $pDAO["Inventory"]->checkInventoryExist($idCardOne,$idUser);
  $isExistTwo = $pDAO["Inventory"]->checkInventoryExist($idCardTwo,$idUser);
  $isExistThree = $pDAO["Inventory"]->checkInventoryExist($idCardThree,$idUser);
  if($isExistOne == EXIT_CODE_OK && $isExistTwo == EXIT_CODE_OK && $isExistThree == EXIT_CODE_OK)
  {
    $pDAO["Inventory"]->delete($idCardOne,$idUser);
    $pDAO["Inventory"]->delete($idCardTwo,$idUser);
    $pDAO["Inventory"]->delete($idCardThree,$idUser);
    $newCard = $pDAO["Card"]->getRandomCard();
    $pDAO["Inventory"]->insert($newCard["id"],$idUser);
    $resultat["error"] = EXIT_CODE_OK;
    $resultat["result"] = $newCard;
    return $resultat;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_INVENTORY_IS_MISSING;
    return $resultat;
  }
}

function meltCard($pDAO,$idUser,$idCard) //100%
{
  $isExist = $pDAO["Inventory"]->checkInventoryExist($idCard,$idUser);
  if($isExist == EXIT_CODE_OK)
  {
    $pDAO["Inventory"]->delete($idCard,$idUser);
    if(rand(0,1))
    {
      $newCard = $pDAO["Card"]->getRandomCard();
      $pDAO["Inventory"]->insert($newCard["id"],$idUser);
      $resultat["result"] = $newCard["name"];
    }
    else
    {
      $resultat["result"] = false;
    }
    $resultat["error"] = EXIT_CODE_OK;
    return $resultat;
  }
  else
  {
    $resultat["error"] = EXIT_CODE_INVENTORY_IS_MISSING;
    return $resultat;
  }
}

function getQuestion($pDAO)  //100%
{
  $listCountry = json_decode(file_get_contents(CITY_API_URI),true);
  $indexCountry = array_rand($listCountry,1);
  $questionCountry = $listCountry[$indexCountry];

  $listCity = json_decode(file_get_contents(CITY_API_URI . $listCountry[$indexCountry]["code"] . END_CITY_API_URI),true);

  $listCity = array_filter($listCity,function($city){
    return $city["population"] < MIN_POPULATION;
  });
  $index = array_rand($listCity,1);
  $firstChoice = $listCity[$index];
  $numGoodAnswer = $listCountry[$indexCountry]["code"];
  $listCountryWithout = array_filter($listCountry,function($country){
    return $country["code"] != $numGoodAnswer;
  });
  $listCountryWithout = array_filter($listCountryWithout,function($country){
    return $country["code"] != "976"; //ENLEVER MAYOTTE
  });


  $selectCountry = array_rand($listCountryWithout,1);
  $listCity = json_decode(file_get_contents(CITY_API_URI . $listCountryWithout[$selectCountry]["code"] . END_CITY_API_URI),true);
  $listCity = array_filter($listCity,function($city){
    return $city["population"] > MIN_POPULATION;
  });

  if(count($listCity) == 1)
  {

    $secondChoice = array_pop($listCity);
  }
  elseif(count($listCity) == 0)
  {
    var_dump("ALERRTTT");
    var_dump($listCountryWithout[$selectCountry]);
  }
  else
  {
    $index = array_rand($listCity,1);
    $secondChoice = $listCity[$index];
  }
  $selectCountry = array_rand($listCountryWithout,1);
  $listCity = json_decode(file_get_contents(CITY_API_URI . $listCountryWithout[$selectCountry]["code"] . END_CITY_API_URI),true);
  $listCity = array_filter($listCity,function($city){
    return $city["population"] > MIN_POPULATION;
  });

  if(count($listCity) == 1)
  {
    $thridChoice = array_pop($listCity);
  }
  elseif(count($listCity) == 0)
  {
    var_dump("ALERRTTT");
    var_dump($listCountryWithout[$selectCountry]);
  }
  else
  {
    $index = array_rand($listCity,1);
    $thridChoice = $listCity[$index];
  }
  $selectCountry = array_rand($listCountryWithout,1);
  $listCity = json_decode(file_get_contents(CITY_API_URI . $listCountryWithout[$selectCountry]["code"] . END_CITY_API_URI),true);
  $listCity = array_filter($listCity,function($city){
    return $city["population"] > MIN_POPULATION;
  });
  if(count($listCity) == 1)
  {
    $fourthChoice = array_pop($listCity);
  }
  elseif(count($listCity) == 0)
  {
    var_dump("ALERRTTT");
    var_dump($listCountryWithout[$selectCountry]);
  }
  else
  {
    $index = array_rand($listCity,1);
    $fourthChoice = $listCity[$index];
  }
  $answer = [$firstChoice["nom"],$secondChoice["nom"],$thridChoice["nom"],$fourthChoice["nom"]];
  shuffle($answer);
  $question = "Quel est la ville qui vient du département de " . $questionCountry["nom"] . " ?";
  $goodReponse = sha1($firstChoice["nom"] . "OUIGO");
  $result = [$question,$answer,$goodReponse];
  $resultat["error"] = EXIT_CODE_OK;
  $resultat["result"] = $result;
  return $resultat;
}

function setAnswer($pDAO,$idUser,$hashAnswer,$question)  //Non Fonctionnel
{

  if(sha1($question . "OUIGO") == $hashAnswer)
  {
    $resultat["resultat"] = true;
  }
  else
  {
    $resultat["resultat"] = false;
  }
  $resultat["error"] = EXIT_CODE_OK;
  return $resultat;
}

/**********************/
/* FUNCTION INVENTORY */
/**********************/

function getInventory($pDAO,$pIdUser) //100%
{
  $resultat["error"] = EXIT_CODE_OK;
  $resultat["inventory"]=  $pDAO["Inventory"]->getAllCardByUserId($pDAO,$pIdUser);
  return $resultat;
}

/********************/
/* FUNCTION CONNECT */
/********************/

function connect($pDAO,$pLogin,$pPass,$pKey,$pMode) //100%
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

function register($pDAO,$pLogin,$pPass,$pKey,$pMode) //100%
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
