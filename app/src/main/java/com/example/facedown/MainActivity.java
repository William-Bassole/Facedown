package com.example.facedown;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements AccelerometerDetector.Listener, ProximityDetector.Listener {

    private AccelerometerDetector accelerometerDetector;
    private ProximityDetector proximityDetector;
    private Chronometer timer;
    private TextView statusText;
    private Button startButton;
    private boolean sessionStarted;
    private boolean timerRunning;
    private boolean faceDown;
    private boolean objectNear;
    private long elapsedTime;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometerDetector = new AccelerometerDetector(this, this);
        proximityDetector = new ProximityDetector(this, this);

        timer = findViewById(R.id.timerText);
        statusText = findViewById(R.id.statusText);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(view -> {
            if (sessionStarted) {
                endSession();
            } else {
                startSession();
            }
        });
    }

    private void startSession() {
        if (!accelerometerDetector.isAvailable() || !proximityDetector.isAvailable()) {
            statusText.setText("Sensor unavailable");
            return;
        }

        sessionStarted = true;
        timerRunning = false;
        faceDown = false;
        objectNear = proximityDetector.isObjectNear();
        elapsedTime = 0;

        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
        accelerometerDetector.start();
        proximityDetector.start();

        startButton.setText("End session");
        statusText.setText(R.string.status_ready);
    }

    private void endSession() {
        pauseTimer();
        accelerometerDetector.stop();
        proximityDetector.stop();

        sessionStarted = false;
        faceDown = false;
        objectNear = false;

        startButton.setText(R.string.start_session);
        statusText.setText(R.string.status_ready);
    }

    @Override
    public void onFaceDownChanged(boolean faceDown) {
        this.faceDown = faceDown;
        updateTimer();
    }

    @Override
    public void onProximityChanged(boolean objectNear) {
        this.objectNear = objectNear;
        updateTimer();
    }

    private void updateTimer() {
        if (!sessionStarted) {
            return;
        }

        if (faceDown && objectNear) {
            if (!timerRunning) {
                timer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
                timer.start();
                timerRunning = true;
            }
            statusText.setText(R.string.status_running);
        } else {
            pauseTimer();
            statusText.setText(R.string.status_broken);
        }
    }

    private void pauseTimer() {
        if (timerRunning) {
            elapsedTime = SystemClock.elapsedRealtime() - timer.getBase();
            timer.stop();
            timerRunning = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sessionStarted) {
            accelerometerDetector.start();
            proximityDetector.start();
        }
    }

    @Override
    public void onPause() {
        if (sessionStarted) {
            pauseTimer();
            accelerometerDetector.stop();
            proximityDetector.stop();
            faceDown = false;
        }
        super.onPause();
    }
}
