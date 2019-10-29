fun main(){

    var thermo = Thermometer(SensorLogger(RoundValue(IgnoreDuplicates(UpDownSensor()))), WeatherReport())

    thermo = Thermometer(SensorLogger(RoundValue(IgnoreDuplicates(UpDownSensor()))), HeatingSystem())

    //RandomSensor(10.0, 40.0)
    thermo.measure(10)
}