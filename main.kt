fun main(){
    val thermo1 = Thermometer(SensorLogger(RoundValue(UpDownSensor())))
    val thermo2 = Thermometer(SensorLogger(RoundValue(IgnoreDuplicates(RandomSensor(10.0, 30.0)))))

    thermo1.register(HeatingSystem())
    thermo2.register(TemperatureAlert(32f, "\nKoerpertemperatur.\n"))

    println("\nThermo 1")
    thermo1.measure(10)

    println("\nThermo 2")
    thermo2.measure(10)
}