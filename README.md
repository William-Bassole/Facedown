# FaceDown

FaceDown is an Android study timer that runs while the phone is placed face down on a surface. If the phone is picked up or turned over, the timer pauses.

This application was developed for the course *Séminaire de langages de programmation* at the University of Geneva.

## How it works

FaceDown uses two sensors:

- **Accelerometer**: checks whether the screen is facing down using the Z axis.
- **Proximity sensor**: checks whether an object or surface is close to the screen.

The timer runs only when both conditions are true. If one of them becomes false, the timer pauses and keeps the elapsed time. It resumes when both conditions become true again.

## Features

- Manual start and end of a focus session
- Face-down detection using the accelerometer
- Surface detection using the proximity sensor
- Timer with pause and resume
- Live session status
- Simple dark interface

## Project structure

| File | Role |
| --- | --- |
| `MainActivity.java` | Manages the session, timer, sensors and interface |
| `AccelerometerDetector.java` | Detects whether the phone is facing down |
| `ProximityDetector.java` | Detects whether an object is close to the screen |
| `res/layout/activity_main.xml` | Defines the main screen |
| `res/values/` | Contains the texts, colors and themes |
| `res/drawable/` | Contains the timer and button backgrounds |

## Requirements

- Android Studio
- Android SDK
- Android 8.0 or later
- A device with an accelerometer and a proximity sensor

## Run the application

1. Open the project in Android Studio.
2. Connect an Android phone.
3. Select the phone as the target device.
4. Run the application.
5. Press **Start session** and place the phone face down on a surface.

## Authors

- [William Bassolé](https://github.com/William-Bassole): proximity sensor and user interface
- [Abdel Jahid](https://github.com/JahidAbdel): accelerometer and session logic

Developed in Java and XML using Android Studio.
