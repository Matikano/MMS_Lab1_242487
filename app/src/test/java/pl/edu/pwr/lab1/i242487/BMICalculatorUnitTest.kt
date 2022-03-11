package pl.edu.pwr.lab1.i242487

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.edu.pwr.lab1.i242487.utils.BMICalculator

class BMICalculatorUnitTest {


    companion object {
        const val DOUBLE_DELTA_DECIMAL_PRECISION = 0.1
    }

    @Test
    fun calculateMetricBMI_isCorrect() {
        assertEquals(BMICalculator.calculateMetricBMI(100.0, 100.0), 100.0, DOUBLE_DELTA_DECIMAL_PRECISION)
        assertEquals(BMICalculator.calculateMetricBMI(75.0, 175.0), 24.5, DOUBLE_DELTA_DECIMAL_PRECISION)
        assertEquals(BMICalculator.calculateMetricBMI(40.0, 200.0), 10.0, DOUBLE_DELTA_DECIMAL_PRECISION)
    }

    @Test
    fun calculateImperialBMI_isCorrect() {
        assertEquals(BMICalculator.calculateImperialBMI(155.0, 50.0), 43.6, DOUBLE_DELTA_DECIMAL_PRECISION)
        assertEquals(BMICalculator.calculateImperialBMI(100.0, 100.0), 7.0, DOUBLE_DELTA_DECIMAL_PRECISION)
        assertEquals(BMICalculator.calculateImperialBMI(150.0, 75.0), 18.7, DOUBLE_DELTA_DECIMAL_PRECISION)
    }
}