let Tableau = class {
	constructor(ligne,colonne){
		this.ligne = ligne;
		this.colonne = colonne;
	}
	nbLigne(){
		return this.ligne;
	}
	nbColonne(){
		return this.colonne;
	}
	nbCellule(){
		return this.ligne * this.colonne;
	}
}

let tableauInit;
let tableau = document.getElementById('tableau');
initTableau();

document.getElementById("btnRejouer").addEventListener("click", rejouer);
let textVictoire = document.getElementById("textVictoire");
let jouerJoueur1 = true;
let jouerJoueur2 = false;
let classeJoueur1 = "cercle";
let classeJoueur2 = "croix";
let symbolejoueur1 = "O";
let symboleJoueur2 = "X";
let j1 = document.getElementsByClassName(classeJoueur1);
let j2 = document.getElementsByClassName(classeJoueur2);
let celluleRestante;
let nomJoueur1;
let nomJoueur2;
let scoreJoueur1;
let scoreJoueur2;
let nbPartie = 0;

initJoueur1();
initJoueur2();
scoreJoueur1 = 0;
scoreJoueur2 = 0;
setScoreJoueur1();
setScoreJoueur2();
textVictoire.innerText = "Tours de " + nomJoueur1;


function addRowsAndCells(){
	for(let i=0;i<tableauInit.nbLigne();i++){
		let ligneTab = tableau.insertRow(-1);
		for(let j=0;j<tableauInit.nbColonne();j++){
			let colonneTab = ligneTab.insertCell(j);
			colonneTab.className ="";
		}

	}

}
function rejouer() {
    resetChamp();
    addRowsAndCells();

    tableau.addEventListener("click", ajoutElement);
	if(jouerJoueur1 == true){
		 textVictoire.innerText = "Tour de " + nomJoueur1;
	} else {
		textVictoire.innerText = "Tour de " + nomJoueur2;
	}

    nbPartie++;
	celluleRestante = tableauInit.nbCellule();
    //jouerJoueur1 = true;
    //jouerJoueur2 = false;

}

function resetChamp() {
    for(let i=0;i<tableauInit.nbLigne();i++){
	    tableau.deleteRow(-1);
	}
}

function start() {

	if(tableauInit.nbLigne() > 1 && tableauInit.nbLigne()!==""
		&& tableauInit.nbColonne() > 1 && tableauInit.nbColonne()!==""){
		celluleRestante = tableauInit.nbCellule();
		tableau.addEventListener("click", ajoutElement);
		addRowsAndCells();
		return true;
	}else {

		window.alert('Veuillez choisir une nombre de ligne et colonne supérieur à 1 Merci .');
		initTableau();
		return false;
	}

}

function initJoueur1() {
    nomJoueur1 = window.prompt("Nom joueur1");
    document.getElementById("nomJoueur1").textContent = nomJoueur1;
}

function initJoueur2() {
    nomJoueur2 = window.prompt("Nom joueur2");
    document.getElementById("nomJoueur2").textContent = nomJoueur2;
}

function setScoreJoueur1() {
    document.getElementById("pointJoueur1").textContent = scoreJoueur1;
}

function setScoreJoueur2() {
    document.getElementById("pointJoueur2").textContent = scoreJoueur2;
}
function initTableau(){
	let ligne;
	let colonne;
	ligne = window.prompt("Nombre de ligne");
	colonne = window.prompt("Nombre de colonne");
	tableauInit = new Tableau(ligne,colonne);
}


function isEmpty(e) {

    if (e.className === "") {
        return true;
    }
    return false;
}

function victoireLigne(classe){

	for(let i =0;i<tableauInit.nbLigne();i++){
		let trouve =0;
    	for(let j = 0 ;j<tableauInit.nbColonne();j++){
        	if (tableau.rows[i].cells[j].className === classe){
            	trouve++;
            	if(trouve == tableauInit.nbColonne()){
        			return true;
        		}

        	}

    	}

	}
	return false;
}

function victoireColonne(classe){

	for(let i =0;i<tableauInit.nbColonne();i++){
		let trouve =0;
    	for(let j = 0 ;j<tableauInit.nbLigne();j++){
        	if (tableau.rows[j].cells[i].className === classe){
            	trouve++;
            	if(trouve == tableauInit.nbLigne()){
        			return true;
        		}

        	}

    	}

	}
	return false;
}

function victoireDiago1(classe){

	if(tableauInit.nbLigne() === tableauInit.nbColonne()){
		let trouve =0;
		for(let i =0;i<tableauInit.nbLigne();i++){
			if(tableau.rows[i].cells[i].className == classe){
				trouve++;
				if(trouve == tableauInit.nbLigne()){
					return true;
				}
			}
    	}
	}

	return false;
}

function victoireDiago2(classe){

	if(tableauInit.nbLigne() === tableauInit.nbColonne()){
		let j = tableauInit.nbColonne() - 1;
		let trouve = 0;
		for(let i = 0 ;i< tableauInit.nbLigne();i++){
			if(tableau.rows[i].cells[j].className == classe){
				trouve++;
				if(trouve == tableauInit.nbColonne()){
					return true;
				}
			}
			j--;
    	}

	}

	return false;
}



function conditionDeVictoire(classe) {

    return (victoireColonne(classe) ||
    	victoireLigne(classe) 		||
    	victoireDiago1(classe) 		||
    	victoireDiago2(classe))
}


function rechercheGagnant(classe) {
    if (conditionDeVictoire(classe)) {
        return true;

    }
    return false;
}
function matchNul() {
    for(let i=0;i<tableauInit.nbLigne();i++){
        for(let j=0;j<tableauInit.nbColonne();j++){
            if(tableau.rows[i].cells[j].className === ""){
                return true;
            }
        }
    }
    return false;
}


function ajoutElement(event) {

    let cellule = event.target;

    if (jouerJoueur1 === true) {

        if (isEmpty(cellule)) {
            cellule.className = classeJoueur1;
            cellule.textContent = symbolejoueur1;
			celluleRestante--;
            if (rechercheGagnant(classeJoueur1)) {

                textVictoire.innerText = "Victoire de " + nomJoueur1;
                scoreJoueur1++;
                setScoreJoueur1();
                setScoreJoueur2();
                tableau.removeEventListener("click", ajoutElement);
				jouerJoueur1 = false;
				jouerJoueur2 = true;
			}else if(celluleRestante===0){
				textVictoire.innerText = "Match null ";
			} else {
				jouerJoueur1 = false;
				jouerJoueur2 = true;
				textVictoire.innerText = "Tour de " + nomJoueur2;

			}

        }

    }
    if (jouerJoueur2 === true) {

        if (isEmpty(cellule)) {
            cellule.className = classeJoueur2;
            cellule.textContent = symboleJoueur2;
			celluleRestante--;
            if (rechercheGagnant(classeJoueur2)) {

                textVictoire.innerText = "Victoire de " + nomJoueur2;
                scoreJoueur2++;
                setScoreJoueur2();
                setScoreJoueur1();

                tableau.removeEventListener("click", ajoutElement);
				jouerJoueur2 = false;
				jouerJoueur1 = true;
            }else if(celluleRestante===0){
				textVictoire.innerText = "Match null ";
			}else {
				jouerJoueur2 = false;
				jouerJoueur1 = true;
				textVictoire.innerText = "Tour de " + nomJoueur1;
			}


        }

    }


}
function returnLobby()
{
  window.location.href = 'https://coopuniverse.fr/';
}


	start();
