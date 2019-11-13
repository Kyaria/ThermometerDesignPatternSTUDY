/*Grund:
* 1/ Wenn die Änderung eines Objekts die Änderung anderer Objekte verlangt,
* aber nicht bekannt ist, wie viele (und welche) anderen Objekte dies sind.
* 2/ Es besteht eine Abhängigkeit zwischen Objekten, diese sollen aber unabhängig verändert werden können
* 3/ Der Benachrichtigungsmechanismussoll abstrahiertwerden
*
* PRO: Abstrakte Kopplung zwischen Subjekt und Beobachter, Broadcast-Kommunikation
* CON: Overhead bei Benachrichtigung, Ggf. werden teure Operationen angestoßen
*
* Zu bedenken:
* 1/ Falls ein Beobachter mehrere Subjekte beobachtet, muss die Benachrichtigung klarstellen,
* welches Subjekt sich geänert hat
* 2/ Subjektzustand sollte sich nicht ändern bevor Beobachter den Zustand nach Benachrichtigung abgefragt haben
* */

// Abstraktes Beobachter Interface

interface TemperatureObserver{
    fun notified(temp : Float)
}

// Konkreter Beobachter
// Wenn gesuchter Wert gefunden wird soll die mitgegebene Nachricht ausgegeben werden.

class TemperatureAlert(val seekTemp : Float, val message : String) : TemperatureObserver{
    override fun notified(temp: Float){
        if(seekTemp == temp)
            println(message)
    }
}

// Konkreter Beobachter
// Speichert 100 Werte, gibt sie dann aus und wiederholt diesen Vorgang

class WeatherReport() : TemperatureObserver{
    private val tempList = mutableListOf<Float>()

    override fun notified(temp: Float){
        when(tempList.size){
            in 0..98 -> tempList.add(temp)
            else -> {
                tempList.add(temp)

                println("WEATHER REPORT CORE DUMP")
                println(tempList)

                tempList.clear()
            }
        }
    }
}

// Konkreter Beobachter / Programmkontext des Strategiemusters
// Speichert immer die letzten 10 Werte und führt dann eine Heizstrategie mit diesen Werten aus.

class HeatingSystem() : TemperatureObserver{
    private var tempList = mutableListOf<Float>()

    override fun notified(temp: Float){
        when(tempList.size){
            in 0..9 -> tempList.add(temp)
            else -> {
                tempList.removeAt(0)
                tempList.add(temp)
            }
        }

        var heatingStrat : HeatingStrategy = ReasonableHeatingStrategy()
        //heatingStrat = InstantHeatingStrategy()
        //heatingStrat = SensibleHeatingStrategy()

        println("Heizung : ${ if( heatingStrat.needsHeating(tempList)) "ON" else "OFF"}\n")
        //println("Heizung : ${ if( heatingStrat.needsHeating(tempList)) "ON" else "OFF"}\n")
        //println("Heizung : ${ if( heatingStrat.needsHeating(tempList)) "ON" else "OFF"}\n")
    }
}