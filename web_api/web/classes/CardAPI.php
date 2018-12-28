<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/auto_load.php"; //Inclusion du chargement de tout les fichiers


class CardAPI {

  public function getOne($id_card)
  {

    $cards = $this->getApiJson();
    foreach($cards as $card)
    {
      if($card["id"] == $id_card)
      {
        return ["cards" => $card];
      }

    }
    return null;
  }

  public function getAll()
  {
    return ["cards" => $this->getApiJson()];
  }

  public function getAllByFilter($typeFilter,$valuefilter)
  {

    return array_filter($this->getApiJson(), function($card) use($typeFilter,$valuefilter)
    {

      return strpos($card[$typeFilter], $valuefilter) !== false;
    });
  }

  public function getRandomCard()
  {
    return $this->getApiJson()[array_rand($this->getApiJson(), 1)];
  }

  public function checkIdCard($pIdCard)
  {
    return $this->getOne($pIdCard) == null;
  }

  private function getApiJson()
  {
    $parse = file_get_contents(HEARTHSTONE_API_URI);
    return json_decode($parse, true);
  }
}
