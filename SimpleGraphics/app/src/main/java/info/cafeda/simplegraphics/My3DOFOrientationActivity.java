package info.cafeda.simplegraphics;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import info.cafeda.simplegraphics.geoData.BasicSense;
import info.cafeda.simplegraphics.graphics.DrawableGeometry;
import info.cafeda.simplegraphics.graphics.Vector3;
import info.cafeda.simplegraphics.myGL.MyGLSurfaceView;

public class My3DOFOrientationActivity extends Activity {

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private SensorEventListener gyroscopeListener;
    private MyGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Test on physical device
        //Check gyroscope sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope == null) {
            Toast.makeText(this, "This device does not have gyroscope sensor!", Toast.LENGTH_SHORT).show();
            finish();
        }
        Log.d("SENSOR","Check End");
        // set orientation of phone
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Set up sense
        DrawableGeometry[] objectsToDraw;
        Vector3 eye = new Vector3(0.0f, 2.0f, 0.0f);
        Vector3 centre = new Vector3(0, 0, -5.0f);
        Vector3 up = new Vector3(0, 1, 0);
        BasicSense sense = new BasicSense();
        objectsToDraw = sense.objectsToDraw;
        mGLView = new MyGLSurfaceView(this, objectsToDraw, eye, centre, up);
        //Disable touch enable rotate
        mGLView.setRotate();

        //add gyroscope listener, tut: https://www.youtube.com/watch?v=8Veyw4e1MX0
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mGLView.onRotateEvent(event.values[0],event.values[1],event.values[2]);
                Log.d("SENSOR","Change");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        setContentView(mGLView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeListener);
    }
}
