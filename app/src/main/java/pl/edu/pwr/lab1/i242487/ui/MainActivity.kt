package pl.edu.pwr.lab1.i242487.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import pl.edu.pwr.lab1.i242487.*
import pl.edu.pwr.lab1.i242487.dataObjects.Measurement
import pl.edu.pwr.lab1.i242487.databinding.ActivityMainBinding
import pl.edu.pwr.lab1.i242487.utils.BMICalculator
import pl.edu.pwr.lab1.i242487.utils.Utils
import pl.edu.pwr.lab1.i242487.viewModels.BMICalculatorViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BMICalculatorViewModel

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor


    private fun Editable.toDouble() =
        if(toString() == "")
            0.0
        else toString().toDouble()

    private fun AppCompatActivity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity.kt", "onCreate() called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(BMICalculatorViewModel::class.java)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(Utils.SP_NAME, Context.MODE_PRIVATE)
        spEditor = sharedPreferences.edit()
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity.kt", "onStart() called")

        setFocusListeners()
        setHintTexts()

        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setViewsValues()

        Log.d("MainActivity.kt", "onResume() called")
    }

    private fun setViewsValues() {

        val massString = if(viewModel.mass == 0.0) Utils.EMPTY_STRING else viewModel.mass.toString()
        val heightString = if(viewModel.height == 0.0) Utils.EMPTY_STRING else viewModel.height.toString()

        binding.tietMass.setText(massString)
        binding.tietHeight.setText(heightString)

        calculateAndSetBMIValue()
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity.kt", "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity.kt", "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity.kt", "onDestroy() called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity.kt", "onRestart() called")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.info -> startInfoActivity()
            R.id.mode -> {
                BMICalculator.toggleUnitMode()
                setHintTexts()
                showModeChangedToast()
                calculateAndSetBMIValue()
            }
            R.id.history -> startHistoryActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showModeChangedToast() {
        when(BMICalculator.MODE) {
            BMICalculator.MODE_METRIC ->
                Toast.makeText(
                    this,
                    resources.getString(R.string.mode_changed, BMICalculator.MODE_NAME_METRIC),
                    Toast.LENGTH_SHORT
                ).show()

            BMICalculator.MODE_IMPERIAL ->
                Toast.makeText(
                    this,
                    resources.getString(R.string.mode_changed, BMICalculator.MODE_NAME_IMPERIAL),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun startInfoActivity() {
        startActivity(Intent(this@MainActivity, InfoActivity::class.java))
    }

    private fun startHistoryActivity() {
        startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
    }

    private fun onCalculateBtnClicked(){
        hideKeyboard()

        if(validateFields()){
            calculateAndSetBMIValue()

            val mass = viewModel.mass
            val height = viewModel.height
            val bmi = viewModel.bmi

            val measurementList =
                Utils.getMeasurementList(sharedPreferences)

            val measurement = Measurement(mass, height, bmi, BMICalculator.MODE)

            Utils.addToList(measurementList, measurement)
            Utils.setList(spEditor, Utils.SP_KEY_MEASUREMENTS, measurementList)
        }
    }

    private fun setClickListeners() {
        //Button listener
        binding.btnCalculate.setOnClickListener {
            onCalculateBtnClicked()
        }


        //BMI TextView listener
        binding.tvBmiValue.setOnClickListener{
            startActivity(
                Intent(this@MainActivity, DetailsActivity::class.java)
                    .putExtra(DetailsActivity.BUNDLE_KEY_BMI, viewModel.bmi)
            )
        }
    }

    private fun setHintTexts(){
        when (BMICalculator.MODE) {
            BMICalculator.MODE_IMPERIAL -> {
                binding.tilMass.hint = resources.getString(
                    R.string.tiet_mass_hint,
                    BMICalculator.IMPERIAL_MASS
                )
                binding.tilHeight.hint = resources.getString(
                    R.string.tiet_height_hint,
                    BMICalculator.IMPERIAL_HEIGHT
                )
            }

            BMICalculator.MODE_METRIC -> {
                binding.tilMass.hint = resources.getString(
                    R.string.tiet_mass_hint,
                    BMICalculator.METRIC_MASS
                )
                binding.tilHeight.hint = resources.getString(
                    R.string.tiet_height_hint,
                    BMICalculator.METRIC_HEIGHT
                )
            }
        }
    }

    private fun calculateAndSetBMIValue(){
        val mass = binding.tietMass.text!!.toDouble()
        val height = binding.tietHeight.text!!.toDouble()

        viewModel.mass = mass
        viewModel.height = height

        val bmi = viewModel.calculateBMI()

        binding.tvBmiValue.text = resources.getString(R.string.bmi_text, bmi)

        when {
            bmi < BMICalculator.BMI_TO_LOW -> binding.tvBmiValue.setTextColor(resources.getColor(
                R.color.bmi_to_low
            ))
            bmi <= BMICalculator.BMI_NORMAL -> binding.tvBmiValue.setTextColor(resources.getColor(
                R.color.bmi_normal
            ))
            else -> binding.tvBmiValue.setTextColor(resources.getColor(R.color.bmi_overweight))
        }
    }

    private fun setFocusListeners() {
        binding.tietMass.setOnFocusChangeListener { _, focused ->
            if(!focused)
                validateMass()
        }

        binding.tietHeight.setOnFocusChangeListener { _, focused ->
            if(!focused)
                validateHeight()
        }
    }

    private fun validateMass(): Boolean {
        val valid = binding.tietMass.text.toString() != ""

       when {
           valid -> binding.tilMass.helperText = null
           else -> binding.tilMass.helperText = BMICalculator.VALIDATION_ERROR_MESSAGE
       }

        return valid
    }

    private fun validateHeight(): Boolean {
        val valid = binding.tietHeight.text.toString() != ""

        when {
            valid -> binding.tilHeight.helperText = null
            else -> binding.tilHeight.helperText = BMICalculator.VALIDATION_ERROR_MESSAGE
        }

        return valid
    }

    private fun validateFields(): Boolean {
        return validateHeight() && validateMass()
    }

}