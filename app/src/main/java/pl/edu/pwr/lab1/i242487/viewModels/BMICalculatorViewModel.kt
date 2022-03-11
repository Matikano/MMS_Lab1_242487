package pl.edu.pwr.lab1.i242487.viewModels

import androidx.lifecycle.ViewModel
import pl.edu.pwr.lab1.i242487.utils.BMICalculator

class BMICalculatorViewModel : ViewModel() {

    var mass: Double = 0.0
    var height: Double = 0.0

    var bmi: Double = 0.0

    fun calculateBMI(): Double {
        bmi = BMICalculator.calculateBMI(mass, height)

        return bmi
    }

}