/*Das Strategiemuster erlaubt unterschiedliche Vorgehensweisen (Algorithmen) unabhaenig
* vom Programmkontext.
* Es kapselt die Algorithmen und macht sie austauschbar.
*
* Grund:
* 1/ Verschiedene Klassen, die sich nur in einem Verhalten unterscheiden .
* 2/ Vermeidung von Klassenexplosionen, wenn für jeden Algorithmus eine neue Unterklasse erzeugt werden müsste.
* 3/ Vermeidung von Codeduplizierung, wenn mehrere Klassen den gleichen Algorithmus brauchen.
* 4/ Vermeidung von unflexiblen Klassen die alle Algorithmen implementieren.
* 5/ Einfache Konfigurierbarkeit:
*   -> Je nach Situation müssen Algorithmen ausgetauscht oder neue ergänztwerden.
* 6/ Vermeidung komplexer Mehrfachverzweigung,  die von einem bestimmten Zustand abhängen.
*
*
* PRO: Def. einer Fam. von Algo., Alternative zur Unterklassenbildung
* CON: Programmkontext muss Strategien kennen, Overhead, mehr Objekte zur Laufzeit
*
* Zu beachten:
* 1/ Bei Vererbung lässt sich Verhalten nur zur Kompilierzeit festlegen,
* und kann nicht zur Laufzeit konfiguriert werden
* 2/ Änderungen an der Oberklasse haben Auswirkungen auf alle Unterklassen,
* dies führt zu teils unerwünschten Abhängigkeiten
* */

//Abstrakte Strategie

interface HeatingStrategy{
    fun needsHeating(tempList : List<Float>) : Boolean
}

//Konkrete Strategie

// Es wird geheizt wenn der letzte Wert kleiner als 19 ist.

class InstantHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean = tempList.last() < 19
}

// Von den letzten 10 Werten soll min. einer unter 20 sein damit geheizt wird.

class SensibleHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean = tempList.any { x : Float -> if (x < 20) true else false }
}

// Es wird geheizt wenn a/ min. 5 Werte unter 19 sind oder wenn b/ min. 1 Wert unter 15 ist.

class ReasonableHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean =
        tempList.fold(0f, { acc : Float, curr : Float -> if(curr < 19) acc + 1 else acc }) >= 5 ||
            tempList.any{ x : Float -> if(x < 15) true else false }
}