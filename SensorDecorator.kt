import kotlin.math.roundToInt
import kotlin.random.Random

// Abstrakte Komponente

interface Sensor {
    fun getTemparature() : Float
}

// Konkrete Komponenten (Der Kern unserer Programmzwiebel!)

class RandomSensor (val min : Double, val max : Double) : Sensor{
    override fun getTemparature(): Float = Random.nextDouble(min, max).toFloat()
}

class UpDownSensor : Sensor{

    private var rndValue = Random.nextFloat() * 100

    override fun getTemparature(): Float{
        val ranInt = Random.nextInt(1, 5)

        if (ranInt >= 4)
            rndValue += 1f
        else if (ranInt <= 2)
            rndValue -= 1f

        return rndValue
    }
}

// Abstract Decorator

open class tmpGeneral(val decSensor : Sensor) : Sensor {
    override fun getTemparature(): Float = decSensor.getTemparature()

    //BSP.:
    // fun getObj(){ println(this) }
    // Muss jz nicht mehr oft neu implementiert werden.
}

// Konkrete Dekorierer

/*Sollte es mehr als eine zu implementierende Methode geben und
* diese Methode ist gleich in verschiedenen Dekorieren,
* so kann man sich arbeit sparen und setzt vor die konkreten Dekorierer
* einen abstrakten Dekorierer. Dieser definiert dann fuer alle konkreten
* Dekorierer die gleich bleibenden Methoden. Siehe BSP.
* */

class SensorLogger (s : Sensor) : tmpGeneral(s){
    override fun getTemparature(): Float {

        val tmp = super.getTemparature()
        println(tmp)
        return tmp
    }
}

/*Alternativ: Es ist in Kotlin moeglich dieses Muster auch ohne einen
* abstrakten Dekorierer kurz und knapp zu verwirklichen. Hier wird dem Compiler gesagt die nicht definierten
* Methoden auf der val s ausgefuehrt werden sollen. Bedingung ist das auf der besagten
* Variable auch die benoetigten Methoden implementiert sind.*/

class RoundValue (val s : Sensor) : Sensor by s{
    override fun getTemparature(): Float = s.getTemparature().roundToInt().toFloat()
}

class IgnoreDuplicates (s : Sensor) : tmpGeneral(s){

    private var oldValue = 0f

    override fun getTemparature(): Float{
        var tmp = 0f

        do{
            tmp = super.getTemparature()
        }
        while (tmp.toInt() == oldValue.toInt())

        oldValue = tmp

        return tmp
    }
}

class SensorLimits (s : Sensor, val min : Double, val max : Double) : tmpGeneral(s){
    override fun getTemparature(): Float{
        var tmp = 0f

        do{
            tmp = super.getTemparature()
        }
        while (tmp !in min..max)

        return tmp
    }
}