/*Das Strategiemuster erlaubt unterschiedliche Vorgehensweisen (Algorithmen) unabhaenig
* vom Programmkontext.
* Es kapselt die Algorithmen und macht sie austauschbar.
*
* Wenn man fuer jede moegliche Algorithmuskombination eine Unterklasse erzeugen wuerde, wuerde das zu einer Klassenexplosion fuehren.
*
* PRO: Def. einer Fam. von Algo., Alternative zur Unterklassenbildung
* CON: Programmkontext muss Strategien kennen, Overhead, mehr Objekte zur Laufzeit*/

// Beim Strategiemuster lagert man das veraenderliche Verhalten (ALGO / STRAT) in ein weiteres Objekt aus und delegiert die Arbeit an dieses Objekt

//Abstrakte Strategie

interface HeatingStrategy{
    fun needsHeating(tempList : List<Float>) : Boolean
}

//Konkrete Strategie
//heating starts when last temp smaller than 19

class InstantHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean = tempList.last() < 19
}

//Konkrete Strategie
//

class SensibleHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean = tempList.any { x : Float -> if (x < 20) true else false }
}

//Konkrete Strategie
//true when the 5 last readings where beneath 19 degrees or when 1 reading was beneath 15 degrees

class ReasonableHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean =
        tempList.fold(0f, { acc : Float, curr : Float -> if(curr < 19) acc + 1 else acc }) >= 5 ||
            tempList.any{ x : Float -> if(x < 15) true else false }
}