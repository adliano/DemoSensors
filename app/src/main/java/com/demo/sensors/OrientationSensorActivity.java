package com.demo.sensors;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrientationSensorActivity extends Activity implements SensorEventListener
{
    SensorManager manager;
    Sensor sensor;
    ImageView imageView;
    TextView tv;

    /*** onCreate ***/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_orientation_sensor);

        imageView = (ImageView)findViewById(R.id.image_view_orientation);

        // get the sensor manager
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // get the orientation sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //http://stackoverflow.com/questions/20339942/android-get-device-angle-by-using-getorientation-function
    }
    /*** onResume ***/
    @Override
    protected void onResume()
    {
        super.onResume();
        if(sensor != null)
        {
            manager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else mkToast("Orientation Sensor Not Available");
    }

    /*** Button Back To SensorsActivity ***/
    public void goBack(View v)
    {
        startActivity(new Intent(getBaseContext(),SensorsActivity.class));
    }
    /******* mkToast *********/
    public void mkToast(String msg)
    {
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
    /******* onPause *******/
    @Override
    protected void onPause()
    {
        super.onPause();
        if(sensor != null) manager.unregisterListener(this);
    }
    /******** onSensorChanged ***********/
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float azimuth = event.values[0];
        // rotate the image
        Matrix matrix = new Matrix();
        // get the size of the image to set rotation point at center of image
        int x = imageView.getDrawable().getBounds().width();
        int y = imageView.getDrawable().getBounds().height();
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        matrix.postRotate(-azimuth,(float)x/2,(float)y/2);
        imageView.setImageMatrix(matrix);

       // tv.setText(String.valueOf(azimuth));
        Log.d("##########",String.valueOf(azimuth));
    }
    /********* onAccuracyChanged **********/
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
