package pl.edu.pwr.lab1.i242487.utils

class BMICalculator {

    companion object {
        const val MODE_METRIC = 1
        const val MODE_IMPERIAL = 2

        var MODE = MODE_METRIC

        const val MODE_NAME_METRIC = "metric"
        const val MODE_NAME_IMPERIAL = "imperial"

        const val IMPERIAL_HEIGHT = "in."
        const val IMPERIAL_MASS = "lbs"

        const val METRIC_MASS = "kg"
        const val METRIC_HEIGHT = "cm"

        const val BMI_NORMAL = 24.9

        const val BMI_TO_LOW = 18.5
        private const val IMPERIAL_CONSTANT = 703

        const val VALIDATION_ERROR_MESSAGE = "Please enter value"

        fun calculateBMI(weight : Double, height: Double): Double {
            if(height == 0.0)
                return 0.0

            return when (MODE){
                MODE_METRIC -> calculateMetricBMI(weight, height)
                MODE_IMPERIAL -> calculateImperialBMI(weight, height)
                else -> 0.0
            }
        }

        fun calculateMetricBMI(weight: Double, height: Double): Double{
            var heightInMeters = height / 100
            return weight / (heightInMeters * heightInMeters)
        }


        fun calculateImperialBMI(weight: Double, height: Double): Double{
            return  (weight * IMPERIAL_CONSTANT) / (height * height)
        }

        fun toggleUnitMode() {
            when (MODE) {
                MODE_METRIC -> MODE = MODE_IMPERIAL
                MODE_IMPERIAL -> MODE = MODE_METRIC
            }
        }
    }



}