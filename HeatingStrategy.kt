/*Das Strategiemuster erlaubt unterschiedliche Vorgehensweisen (Algorithmen) unabhaenig
* vom Programmkontext.
* Es kapselt die Algorithmen und macht sie austauschbar.
*
* Wenn man fuer jede moegliche Algorithmuskombination eine Unterklasse erzeugen wuerde, wuerde das zu einer Klassenexplosion fuehren.
*
* PRO: Def. einer Fam. von Algo., Alternative zur Unterklassenbildung
* CON: Programmkontext muss Strategien kennen, Overhead, mehr Objekte zur Laufzeit*/

//Abstrakte Strategie

interface HeatingStrategy{
    fun needsHeating(tempList : List<Float>) : Boolean
}

//Konkrete Strategie

class InstantHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean = tempList.last() < 19
}

//Konkrete Strategie

class SensibleHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean{

        for(i in 0..tempList.size)
            if(tempList.get(i) < 20) return true

        return false
    }
}

//Konkrete Strategie

class ReasonableHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean {

        var triggerVar = false
        var counter = 0

        tempList.forEach { if(it < 19) counter++ }
        tempList.forEach { if(it < 15) triggerVar = true }

        if (counter >= 5)
            triggerVar = true

        return triggerVar
    }
}