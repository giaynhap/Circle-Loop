package noip.noip.giaynhap.scr;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.media.*;
import java.io.*;
import java.io.IOException;
import java.util.List;
import  android.content.pm.ActivityInfo;
public class MainActivity extends AppCompatActivity implements SensorEventListener  {
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;

    public float[] mGravity;
    public float[] mGeomagnetic;
    public view_surface_2d srview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        srview = new view_surface_2d(this);
        setContentView(srview );
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);

        //setContentView(R.layout.activity_main);

    }
    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        srview.game_pause = false;
        srview.snd_background.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        srview.game_pause = true;
        srview.snd_background.pause();
        mSensorManager.unregisterListener(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           if (srview.isGuiIngame==false) System.exit(1);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;


        if (mGravity != null && mGeomagnetic != null) {

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                srview.azimut = orientation[1]*180.0f/(float)Math.PI;

            }
        }

    }


}
