package com.example.facedown;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Detects whether something is covering the screen, using the proximity sensor.
 */
public class ProximityDetector implements SensorEventListener {

    private static final String TAG = "ProximityDetector";
    private SensorManager sensorManager;
    private Sensor proximitySensor;      // null if the phone has no proximity sensor
    private boolean objectNear = false;  // last decision: is the screen covered?
    private float lastDistance = -1;     // last measured distance in cm (-1 = no measure yet)

    public ProximityDetector(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    // True if the phone has a proximity sensor
    public boolean isAvailable() {
        return proximitySensor != null;
    }

    // Starts listening to the sensor (call in onResume)
    public void start() {
        if (!isAvailable()) return;
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Stops listening to save battery (call in onPause)
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    // Called by Android each time the measured distance changes
    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        lastDistance = distance;
        objectNear = isNear(distance);
        Log.d(TAG, "distance = " + distance + " cm, near = " + objectNear);
    }

    // Required by SensorEventListener, not needed here
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Most sensors only return 0 (near) or their maximum range (far)
    private boolean isNear(float distance) {
        return distance < proximitySensor.getMaximumRange();
    }

    // Used by the timer logic: is the screen covered right now?
    public boolean isObjectNear() {
        return objectNear;
    }

    public float getLastDistance() {
        return lastDistance;
    }
}