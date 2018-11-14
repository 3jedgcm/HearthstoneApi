<?php
/**
 * Représentation d'un objet extrait d'une table de base de données
 * Les champs sont supposés créés correctement et définitivement par le constructeur.
 */
class TableObject {

    // Liste des champs et leur valeur
    protected $fields = array();
    public function getFields() { return $this->fields; }

    // Copie des champs reçus (typiquement par un fetch sur la base)
    public function __construct($f) { $this->fields = $f; }

    // Affichage par défaut : champs séparés par ' | '
    public function __tostring() { return implode(" | ", $this->fields); }

    // ------- setter et getter -------
    // Permette d'accéder aux attributs comme s'ils étaient 'public'
    // $obj->attribut = "truc"; 
    // echo $obj->attribut;   
    public function __get($field) {
        if (isset($this->fields[$field]))
            return $this->fields[$field];
        throw new Exception("Invalid field name $field in ". get_class($this));
    }

    public function __set($field, $value) {
        if (isset($this->fields[$field]))
            $this->fields[$field] = $value;
        else 
            throw new Exception("Invalid field name $field in ". get_class($this));
    }

    public function __isset($field) { return isset($this->fields[$field]); }
}

