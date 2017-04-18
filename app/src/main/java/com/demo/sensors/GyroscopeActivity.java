package com.demo.sensors;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class GyroscopeActivity extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvX,tvY,tvZ;

    /** onCreate **/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gyroscope);

        tvX = (TextView)findViewById(R.id.text_view_gyroscope_X);
        tvY = (TextView)findViewById(R.id.text_view_gyroscope_Y);
        tvZ = (TextView)findViewById(R.id.text_view_gyroscope_Z);

        // get the sensor manager
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // get the Gyroscope sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }
    /*** SensorEventListener ***/
    SensorEventListener listener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String strX = "Axis X : %f";
            String strY = "Axis Y : %f";
            String strZ = "Axis Z : %f";

            if(x>0) tvX.setText(String.format(strX,x));
            if(y>0) tvY.setText(String.format(strY,y));
            if(z>0) tvZ.setText(String.format(strZ,z));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }
    };

    /** onResume **/
    @Override
    protected void onResume()
    {
        super.onResume();
        manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_UI);
    }
    /** onPause **/
    @Override
    protected void onPause()
    {
        super.onPause();
        manager.unregisterListener(listener);
    }

    /*** Button Back To SensorsActivity ***/
    public void goBack(View v)
    {
        startActivity(new Intent(getBaseContext(),SensorsActivity.class));
    }
}
