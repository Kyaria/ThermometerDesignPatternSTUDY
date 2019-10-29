class Thermometer (val mainSensor : Sensor, val observeThis : TemperatureObserver){

    fun measure(n : Int) {
        for(i in 0..n)
            gibZustand(mainSensor.getTemparature())

        // mainSensor.getTemparature() 'setzt' den Zustand und ist der aktuelle Objektzustand
    }

    fun gibZustand(x : Float){
        observeThis.update(x)

        // observeThis.update(x) gibt den Zustand
    }
}