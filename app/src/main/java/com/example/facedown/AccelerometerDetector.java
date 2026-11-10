package com.example.facedown;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerDetector implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Listener listener;
    private boolean faceDown;

    public interface Listener {
        void onFaceDownChanged(boolean faceDown);
    }

    public AccelerometerDetector(Context context, Listener listener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.listener = listener;
    }

    public boolean isAvailable() {
        return accelerometer != null;
    }

    public void start() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
        faceDown = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        
        float z = event.values[2];
        
        boolean newFaceDown = z < -7;
        if (newFaceDown != faceDown) {
            faceDown = newFaceDown;
            listener.onFaceDownChanged(faceDown);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
