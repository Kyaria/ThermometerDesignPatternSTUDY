fun main(){
    val thermo1 = Thermometer(SensorLogger(RoundValue(UpDownSensor())))
    val thermo2 = Thermometer(SensorLogger(RoundValue(IgnoreDuplicates(RandomSensor(10.0, 30.0)))))
    val thermo3 = Thermometer(SensorLogger(RoundValue(SensorLimits(UpDownSensor(),15.0, 40.0))))

    thermo1.register(HeatingSystem())
    thermo2.register(TemperatureAlert(32f, "\nKoerpertemperatur.\n"))
    thermo3.register(WeatherReport())

    val heatObserver = HeatingSystem()

    thermo3.register(heatObserver)
    thermo3.unregister(heatObserver)

    println("\nThermo 1")
    thermo1.measure(10)

    println("\nThermo 2")
    thermo2.measure(10)

    println("\nThermo 3")
    thermo3.measure(100)
}