package pl.edu.pwr.lab1.i242487.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import pl.edu.pwr.lab1.i242487.R
import pl.edu.pwr.lab1.i242487.dataObjects.Measurement
import pl.edu.pwr.lab1.i242487.utils.BMICalculator

class MeasurementListAdapter(private val context: Context, private val list: MutableList<Measurement>):
    RecyclerView.Adapter<MeasurementListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.measurement_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = list[position]

        var massUnit: String = ""
        var heightUnit: String = ""

        when (measurement.unitSystem){
            BMICalculator.MODE_IMPERIAL ->{
                massUnit = BMICalculator.IMPERIAL_MASS
                heightUnit = BMICalculator.IMPERIAL_HEIGHT
            }
            BMICalculator.MODE_METRIC -> {
                massUnit = BMICalculator.METRIC_MASS
                heightUnit = BMICalculator.METRIC_HEIGHT
            }
        }

        holder.tvMass?.text = context.getString(R.string.li_tv_mass, measurement.mass, massUnit)
        holder.tvHeight?.text = context.getString(R.string.li_tv_height, measurement.height, heightUnit)
        holder.tvBMI?.text = context.getString(R.string.li_tv_bmi, measurement.bmi)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        var tvMass: TextView? = null
        var tvHeight: TextView? = null
        var tvBMI: TextView? = null
        init {
            tvMass = itemView?.findViewById(R.id.tvMass)
            tvHeight = itemView?.findViewById(R.id.tvHeight)
            tvBMI = itemView?.findViewById(R.id.tvBmi)
        }
    }
}