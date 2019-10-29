import kotlin.math.roundToInt
import kotlin.random.Random

object RV{ // RND for UpDown Sensor
    var rndValue = Random.nextFloat() * 100
}

object OV{ // Buffer for Ignore Duplicates
    var oldValue = 0f
}

// Komponente
interface Sensor {
    fun getTemparature() : Float
}

// Konkrete Komponenten
class RandomSensor (val min : Double, val max : Double) : Sensor{
    override fun getTemparature(): Float = Random.nextDouble(min, max).toFloat()
}

class UpDownSensor (): Sensor{

    override fun getTemparature(): Float{
        val ranInt = Random.nextInt(1, 5)
        var tmp = RV.rndValue

        if (ranInt >= 4)
            tmp = RV.rndValue + 1
        else if (ranInt == 3)
            tmp = RV.rndValue
        else if (ranInt <= 2)
            tmp = RV.rndValue - 1

        RV.rndValue = tmp
        // Random Temperaturschwankungen

        return tmp
    }
}

// Abstract Decorator
open class decGeneral(val decSensor : Sensor) : Sensor {
    override fun getTemparature(): Float = this.decSensor.getTemparature()
}

// Konkrete Dekorierer
class SensorLogger (s : Sensor) : decGeneral(s){
    override fun getTemparature(): Float {

        val tmp = super.getTemparature()
        println(tmp)
        return tmp
    }
}

class RoundValue (s : Sensor) : decGeneral(s){
    override fun getTemparature(): Float = super.getTemparature().roundToInt().toFloat()
}

class IgnoreDuplicates (s : Sensor) : decGeneral(s){
    override fun getTemparature(): Float{
        var tmp = 0f

        do{
            tmp = super.getTemparature()
        }
        while (tmp.toInt() == OV.oldValue.toInt())

        OV.oldValue = tmp

        return tmp
    }
}

class SensorLimits (s : Sensor, val min : Double, val max : Double) : decGeneral(s){
    override fun getTemparature(): Float{
        var tmp = 0f

        do{
            tmp = super.getTemparature()
        }
        while (tmp in min..max)

        return tmp
    }
}