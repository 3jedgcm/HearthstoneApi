<?php class User {

  var $IdUser;
  var $Password;
  var $Name;
  var $Money;

function __construct($IdUser,$Password,$Name,$Money)
{
  $this->IdUser=$IdUser;
  $this->Password=$Password;
  $this->Name=$Name;
  $this->Money=$Money;

  }
function get_IdUser ()
 {
   return $this->$IdUser;

  }

  function get_NameUser ()
   {
     return $this->$Name;

    }



  }


?>
