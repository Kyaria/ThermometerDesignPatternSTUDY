interface HeatingStrategy{
    fun needsHeating(tempList : List<Float>) : Boolean

    fun desc()
}

class InstantHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean = tempList.last() < 19

    override fun desc() {
        println("Heating house now!")
    }
}

class SensibleHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean{

        for(i in 0..tempList.size)
            if(tempList.get(i) < 20) return true

        return false
    }

    override fun desc() {
        println("Heating sensibly now!")
    }
}

class ReasonableHeatingStrategy : HeatingStrategy{
    override fun needsHeating(tempList: List<Float>): Boolean {

        var triggerVar = false
        var counter = 0

        tempList.forEach{ if(it < 19) counter++}
        tempList.forEach{ if(it < 15) triggerVar = true}

        if (counter >= 5)
            triggerVar = true

        return triggerVar
    }

    override fun desc() {
        println("Heating reasonable now!")
    }
}