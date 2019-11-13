/*
abstract class Poster{
    val observerList = mutableListOf<TemperatureObserver>()

    fun register (TMPobserver: TemperatureObserver){
        observerList.add(TMPobserver)
    }

    fun unregister (TMPobserver: TemperatureObserver){
        observerList.remove(TMPobserver)
    }

    fun notifyOnChange(){
        for (TMPobserver in observerList)
            TMPobserver.notify() //aktualisieren fuer alle Beobachter
                                 //Diese sollen dann die jeweiligen Werte ueber die "gibZustand" (-> lastTMP()) Methode holen
    }
}
 */

/* Normalerweise hat man bei dem Observer-Muster eine Oberklasse die die Methoden bereit
* stellt um einem das Leben einfacher zu machen wenn mann mehrere Objekte observieren will
* aber hier ist das nicht noetig, da es nur das Thermometer zu beobachten gilt.
* Zusaetzlich wird hier auch direkt in der "setzeZustand" (-> measure()) Methode der
* neue Zustand direkt an die "benachrichtigen" (-> notifyOnChange(tmp : Float)) Methode
* geschickt und nicht erst in eine Variable gesichert (-> tmp) um dann die
* "benachrichtigen" (-> notifyOnChange()) Methode aufzurufen, durch die dann die
* Beobachter die passende Methoden (hier: -> lastTMP()) fuer die noetigen Werte aufrufen.
* Dies ergibt sich aus der Aufgabenstellung.
* */

// Abstraktes Subjekt + Konkretes Subjekt

class Thermometer (val mainSensor : Sensor){

    private val observerList = mutableListOf<TemperatureObserver>() // Alle Beobachter

    fun register (obs: TemperatureObserver){ // Beobachter anmelden
        observerList.add(obs)
    }

    fun unregister (obs: TemperatureObserver){ // Beobachter abmelden
        observerList.remove(obs)
    }

    fun notify(tmp : Float){ // Benachrichtige alle Beobachter
        for (obs in observerList)
            obs.notified(tmp)
    }

    fun measure(n : Int) { // Setzt Zustand / ist Zustand, Gibt Zustand
        for(i in 1..n)
            notify(mainSensor.getTemparature())
    }

    /*
    var tmp = 0f

    fun measure(n : Int) {
        for(i in 0..n){
            tmp = mainSensor.getTemparature()
            notifyOnChange()
        }
    }

    fun lastTMP() : Float{
        return tmp
    }
    */
}