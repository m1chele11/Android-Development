package edu.iu.mbarrant.project9

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class ShakeFragment(context: Context, private val onShake: () -> Unit) : SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var acceleration = 0f
    private var currentAcceleration = SensorManager.GRAVITY_EARTH
    private var lastAcceleration = SensorManager.GRAVITY_EARTH

    init {
        registerSensor()
    }

    private fun registerSensor() {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            updateAcceleration(event.values)
            detectShake()
        }
    }

    private fun updateAcceleration(values: FloatArray) {
        val alpha = 0.8f
        lastAcceleration = currentAcceleration
        currentAcceleration =
            alpha * currentAcceleration + (1 - alpha) * values[0] // Use only X-axis for simplicity
        acceleration = currentAcceleration - lastAcceleration

//        Log.d("ShakeDetector", "Acceleration: $acceleration")
    }

//    private fun detectShake() {
//        val shakeThreshold = 5f // Adjust as needed
//        Log.d("ShakeDetector","Processing")
//        if (acceleration > shakeThreshold) { //check the displacement of the X value
//            Log.d("ShakeDetector", "Shake detected!")
//            onShake.invoke()
//        }
//    }

    //new implementation:
    private val shakeThreshold = 5f // Adjust as needed
    private var startX = 0f

    private fun detectShake() {
        //Log.d("ShakeDetector", "Processing ds fun")

        if (acceleration > shakeThreshold) {
            if (startX == 0f) {
                startX = currentAcceleration
            } else {
                val displacement = Math.abs(currentAcceleration - startX)
                Log.d("ShakeDetector", "Shake detected! Displacement: $displacement")

                // Adjust the displacement threshold as needed
                val displacementThreshold = 2f
                if (displacement > displacementThreshold) {
                    Log.d(
                        "ShakeDetector",
                        "Sufficient displacement! Navigating to TakePhotoFragment."
                    )
                    onShake.invoke()
                    startX = 0f // Reset startX for the next shake detection
                }
            }
        }
    }

    fun unregisterSensor() {
        sensorManager.unregisterListener(this)
    }
}