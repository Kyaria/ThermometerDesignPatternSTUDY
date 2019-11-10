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
            in 0..99 -> tempList.add(temp)
            else -> {
                println("WEATHER REPORT CORE DUMP")
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

        if(tempList.size == 10){
            var heatingStrat : HeatingStrategy
            //heatingStrat = InstantHeatingStrategy()
            //heatingStrat = SensibleHeatingStrategy()
            heatingStrat = ReasonableHeatingStrategy()

            println("Heizung : ${ if( heatingStrat.needsHeating(tempList)) "ON" else "OFF"}")
        }
    }
}