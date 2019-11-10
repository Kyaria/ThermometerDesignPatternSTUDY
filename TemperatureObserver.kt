/*Grund: Wenn die Änderung eines Objekts die Änderung anderer Objekte verlangt,
* aber nicht bekannt ist, wie viele (und welche) anderen Objekte dies sind.
* PRO: Abstrakte Kopplung zwischen Subjekt und Beobachter, Broadcast-Kommunikation
* CON: Overhead bei Benachrichtigung, Ggf. werden teure Operationen angestoßen, da ein Subjekt nichts über die Kosten einer Operation der Beobachter weiß
* */

//Beobachter

interface TemperatureObserver{
    fun notify(temp : Float)
}

// Konkreter Beobachter

class TemperatureAlert(val seekTemp : Float, val message : String) : TemperatureObserver{
    override fun notify(temp: Float){
        if(seekTemp == temp)
            println(message)
    }
}

// Konkreter Beobachter

class WeatherReport() : TemperatureObserver{
    val tempList = mutableListOf<Float>()

    override fun notify(temp: Float){
        when(tempList.size){
            in 0..98 -> tempList.add(temp)
            else -> {
                println("WEATHER REPORT CORE DUMP")
                tempList.add(temp)
                tempList.forEach { println("\t" + it) }
                tempList.clear()
            }
        }
    }
}

// Konkreter Beobachter + (In dieser Datei sekundaer) Programmkontext des Strategiemusters

class HeatingSystem() : TemperatureObserver{
    var tempList = mutableListOf<Float>()

    override fun notify(temp: Float){

        when(tempList.size){
            in 0..9 -> tempList.add(temp)
            else -> {   // Wenn 10 Elemente gesichert sind soll das erste Element gelöscht werden,
                        // um so Platz für den neuen Wert zu schaffen. Es sind so immer die letzten 10 Werte gesichert
                tempList.removeAt(0)
                tempList.add(temp)
            }
        }

        val heatingStrat1 : HeatingStrategy = ReasonableHeatingStrategy()
        val heatingStrat2 : HeatingStrategy = InstantHeatingStrategy()
        val heatingStrat3 : HeatingStrategy = SensibleHeatingStrategy()

        println("Heizung : ${ if( heatingStrat1.needsHeating(tempList)) "ON" else "OFF"}")
        //println("Heizung : ${ if( heatingStrat2.needsHeating(tempList)) "ON" else "OFF"}")
        //println("Heizung : ${ if( heatingStrat3.needsHeating(tempList)) "ON" else "OFF"}")
    }
}