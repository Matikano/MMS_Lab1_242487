package pl.edu.pwr.lab1.i242487.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.pwr.lab1.i242487.adapters.MeasurementListAdapter
import pl.edu.pwr.lab1.i242487.utils.Utils
import pl.edu.pwr.lab1.i242487.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(Utils.SP_NAME, Context.MODE_PRIVATE)

        binding.rvMeasurements.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    private fun setAdapter() {
        val measurementList =
            Utils.getMeasurementList(sharedPreferences)
        binding.rvMeasurements.adapter = MeasurementListAdapter(this, measurementList)
    }


}