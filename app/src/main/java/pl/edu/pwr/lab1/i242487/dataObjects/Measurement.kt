package pl.edu.pwr.lab1.i242487.dataObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

data class Measurement(val mass: Double,val height: Double, val bmi: Double,val unitSystem: Int) {

}

class MeasurementDeserializer : JsonDeserializer<Measurement> {

    companion object {
        const val JSON_KEY_MASS = "mass"
        const val JSON_KEY_HEIGHT = "height"
        const val JSON_KEY_BMI = "bmi"
        const val JSON_KEY_UNIT_SYSTEM = "unitSystem"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Measurement {
        json as JsonObject

        val mass = json.get(JSON_KEY_MASS).asDouble
        val height = json.get(JSON_KEY_HEIGHT).asDouble
        val bmi = json.get(JSON_KEY_BMI).asDouble
        val unitSystem = json.get(JSON_KEY_UNIT_SYSTEM).asInt

        return Measurement(mass, height, bmi, unitSystem)
    }

}