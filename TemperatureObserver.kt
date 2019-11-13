/*Grund: Wenn die Änderung eines Objekts die Änderung anderer Objekte verlangt,
* aber nicht bekannt ist, wie viele (und welche) anderen Objekte dies sind.
* PRO: Abstrakte Kopplung zwischen Subjekt und Beobachter, Broadcast-Kommunikation
* CON: Overhead bei Benachrichtigung, Ggf. werden teure Operationen angestoßen, da ein Subjekt nichts über die Kosten einer Operation der Beobachter weiß
* */

//generelles beobachter interface
// keine std. implementierung benoetigt

interface TemperatureObserver{
    fun notified(temp : Float)
}

// konkreter beobachter
// print message when temperatur gleich der gesuchten

class TemperatureAlert(val seekTemp : Float, val message : String) : TemperatureObserver{
    override fun notified(temp: Float){
        if(seekTemp == temp)
            println(message)
    }
}

// konkreter beobachter
// save 100 temps, print them all out, repeat

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

// konkreter beobachter
// save 10 last temps, turn heating based on those temps and the choosen strategy on or off
// (In dieser Datei sekundaer) Programmkontext des Strategiemusters

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