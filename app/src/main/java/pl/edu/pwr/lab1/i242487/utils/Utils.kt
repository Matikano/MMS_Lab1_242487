package pl.edu.pwr.lab1.i242487.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.edu.pwr.lab1.i242487.dataObjects.Measurement

class Utils {
    companion object {

        const val EMPTY_STRING = ""

        const val SP_NAME = "bmi"
        const val SP_KEY_MEASUREMENTS = "measurements"

        const val LIST_MAX_LENGTH = 10

        fun <T> addToList(list: MutableList<T>, value:T) {
            if(list.size >= LIST_MAX_LENGTH)
                list.removeAt(0)
            list.add(value)
        }

        fun getMeasurementList(sp: SharedPreferences): MutableList<Measurement> {
            var list: MutableList<Measurement>
            val gson  = Gson()
            val serializedObject = sp.getString(SP_KEY_MEASUREMENTS, gson.toJson(mutableListOf<Measurement>()))
            val type = object: TypeToken<MutableList<Measurement>>(){}.type
            list = gson.fromJson(serializedObject, type)
            return list
        }

        fun <T> setList(spEditor: SharedPreferences.Editor, spKey: String, list: MutableList<T>) {
            val gson = Gson()
            val jsonString = gson.toJson(list)
            spEditor.putString(spKey, jsonString)
            spEditor.commit()
        }
    }

}