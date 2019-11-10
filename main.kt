fun main(){
    val thermo1 = Thermometer(SensorLogger(RoundValue(IgnoreDuplicates(UpDownSensor()))))
    val thermo2 = Thermometer(SensorLogger(RoundValue(UpDownSensor())))

    thermo1.register(HeatingSystem())
    thermo2.register(TemperatureAlert(32f, "\nKoerpertemperatur.\n"))

    //RandomSensor(10.0, 40.0)
    println("\nThermo 1")
    thermo1.measure(10)

    println("\nThermo 2")
    thermo2.measure(10)
}