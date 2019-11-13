import kotlin.math.roundToInt
import kotlin.random.Random

/*Grund:
* 1/ Dynamisches Hinzufügen (und Entfernen) von Funktionalität zu einzelnen Objekten
* 2/ Vermeidung von Klassenexplosionen wenn viele verschiede Kombinationen von Funktionalität erforderlich sind
* 3/ Vermeidung von unnötiger Vererbung und Codeverdoppelung
*
* PRO: Höhere Flexibilität
* CON: Viele kleine Objekte zur Laufzeit,
*      Reihenfolge der Dekorationen kann Verhalten beeinflussen, eventuell Nebeneffekte,
*      Dekoration und seine Komponenten sind nicht identisch (bei Objektidentitätsvergleich)
*
* Zu beachten:
* 1/ Kann schwer zu durchschauen / warten sein.
* */

interface Sensor {
    fun getTemparature() : Float
}

// Gibt zufällige Werte innerhalb eines Wertebereiches aus.

class RandomSensor (val min : Double, val max : Double) : Sensor{
    override fun getTemparature(): Float = Random.nextDouble(min, max).toFloat()
}

// Gibt realistische Wertschwankungen auf. BSP.: Temperaturen steigen / sinken um 1 Grad oder bleiben gleich.

class UpDownSensor : Sensor{
    private var rndValue = Random.nextDouble(-15.0, 45.0).toFloat()

    override fun getTemparature(): Float{
        rndValue += Random.nextDouble(-1.0, 1.0).toFloat()
        return rndValue
    }
}

/*
// Abstract Decorator

//open class tmpGeneral(val decSensor : Sensor) : Sensor {
    //override fun getTemparature(): Float = decSensor.getTemparature()

    //BSP.:
    // fun getObj(){ println(this) }
    // Muss jz nicht mehr oft neu implementiert werden.
//}

// Konkrete Dekorierer

/*Sollte es mehr als eine zu implementierende Methode geben und
* diese Methode ist gleich in verschiedenen Dekorieren,
* so kann man sich arbeit sparen und setzt vor die konkreten Dekorierer
* einen abstrakten Dekorierer. Dieser definiert dann fuer alle konkreten
* Dekorierer die gleich bleibenden Methoden. Siehe BSP.
* */

//class SensorLogger (s : Sensor) : tmpGeneral(s){
    //override fun getTemparature(): Float {

        //val tmp = super.getTemparature()
        //println(tmp)
        //return tmp
    //}
//}

/*Alternativ: Es ist in Kotlin moeglich dieses Muster auch ohne einen
* abstrakten Dekorierer kurz und knapp zu verwirklichen. Hier wird dem Compiler gesagt die nicht definierten
* Methoden auf der val s ausgefuehrt werden sollen. Bedingung ist das auf der besagten
* Variable auch die benoetigten Methoden implementiert sind.
* */

*/

// Gegebener Wert wird auf der Konsole geloggt.

class SensorLogger (val s : Sensor) : Sensor{
    override fun getTemparature(): Float {
        val tmp = s.getTemparature()
        println(tmp)
        return tmp
    }
}

// Gegebene Werte werden auf den nächsten Integer gerunden.

class RoundValue (val s : Sensor) : Sensor{
    override fun getTemparature(): Float = s.getTemparature().roundToInt().toFloat()
}

// Bei doppeltem Wert wird ein neuer angefragt.

class IgnoreDuplicates (val s : Sensor) : Sensor{
    private var oldValue = 0f

    override fun getTemparature(): Float{
        var tmp = s.getTemparature()

        while (tmp.toInt() == oldValue.toInt()){
            tmp = s.getTemparature()
        }

        oldValue = tmp
        return tmp
    }
}

// Soll alle Werte die nicht innerhalb der gegebenen Grenzen sind neu anfragen.

class SensorLimits (val s : Sensor, val min : Double, val max : Double) : Sensor{
    override fun getTemparature(): Float{
        var tmp = s.getTemparature()

        while (tmp !in min..max){
            tmp = s.getTemparature()
        }

        return tmp
    }
}