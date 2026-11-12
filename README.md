# FaceDown

A study timer for Android that only runs while your phone is lying face down on the table. Pick the phone up and the session is interrupted.

Built for the course *Séminaire de langages de programmation* (University of Geneva, 2026).

## How it works

FaceDown combines two sensors to make sure the phone is really put down:

- **Accelerometer**: checks that the phone is lying flat, screen facing down, and not moving.
- **Proximity sensor**: checks that the screen is actually against a surface.

The timer starts when both conditions are met, stops as soon as one of them changes, and resumes when everything is back in place.

## Features

- Focus timer based on a Chronometer
- Live status message (ready, in progress, interrupted)
- Works without a proximity sensor (the app falls back to the accelerometer only)
- Calm night blue and turquoise interface, inspired by breathing apps

## Project structure

| File | Role |
| `MainActivity.java` | Session logic: connects both sensors to the screen |
| `AccelerometerDetector.java` | Detects when the phone is face down and still |
| `ProximityDetector.java` | Detects when the screen is covered and notifies MainActivity on each change |
| `res/layout/activity_main.xml` | Home screen layout |
| `res/values/`, `res/drawable/` | Colors, texts, timer bubble and button shapes |

## Run it

1. Open the project in Android Studio.
2. Run it on a device or an emulator (Android Studio's virtual sensors can simulate both sensors).
3. Or install the APK directly on an Android phone.

## Authors

- [William-Bassole](https://github.com/William-Bassole): proximity sensor and user interface
- [JahidAbdel](https://github.com/JahidAbdel): accelerometer and session logic

Built with Java, XML and Android Studio. Developed in parallel on feature branches and merged through pull requests.
