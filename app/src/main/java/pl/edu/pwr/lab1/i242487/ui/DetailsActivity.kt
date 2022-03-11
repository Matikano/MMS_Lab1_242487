package pl.edu.pwr.lab1.i242487.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.pwr.lab1.i242487.R
import pl.edu.pwr.lab1.i242487.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailsBinding

    companion object{
        const val BUNDLE_KEY_BMI = "BMI"
        const val BMI_DEFAULT = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bmi = intent.getDoubleExtra(BUNDLE_KEY_BMI, BMI_DEFAULT)
        binding.tvDetailsBmiValue.text = resources.getString(R.string.tvDetailsBmiValue, bmi)

    }

}