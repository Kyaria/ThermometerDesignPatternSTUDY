import kotlin.math.roundToInt
import kotlin.random.Random

// Ermöglicht das dynamische Hinzufügen zusätzlicher Funktionalität zu einer Komponente

// Abstrakte Komponente

interface Sensor {
    fun getTemparature() : Float
}

// Konkrete Komponenten (Der Kern unserer Programmzwiebel!)

//returns random floats in predefined boundrys

class RandomSensor (val min : Double, val max : Double) : Sensor{
    override fun getTemparature(): Float = Random.nextDouble(min, max).toFloat()
}

//returns floats based on a random float and a random rising/sinking pattern

class UpDownSensor : Sensor{

    private var rndValue = Random.nextDouble(-15.0, 45.0).toFloat()

    override fun getTemparature(): Float = rndValue + Random.nextInt(-1, 1)
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

//logs temps on console

class SensorLogger (val s : Sensor) : Sensor{
    override fun getTemparature(): Float {
        val tmp = s.getTemparature()
        println(tmp)
        return tmp
    }
}

//rounds values to nearest int

class RoundValue (val s : Sensor) : Sensor{
    override fun getTemparature(): Float = s.getTemparature().roundToInt().toFloat()
}

//

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

class SensorLimits (val s : Sensor, val min : Double, val max : Double) : Sensor{
    override fun getTemparature(): Float{
        var tmp = s.getTemparature()

        while (tmp !in min..max){
            tmp = s.getTemparature()
        }

        return tmp
    }
}