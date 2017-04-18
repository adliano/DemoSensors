package com.demo.sensors;

/**
 * Adriano Alves
 * May 14 2016
 * Activity to work with the Light sensor
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class LightSensorActivity extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvLight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_light_sensor);

        tvLight = (TextView)findViewById(R.id.text_view_light);

        // get the sensor manager
        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        // use the manager to get the light sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }
    /******* SensorEventListener ******/
    SensorEventListener listener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float light = event.values[0];
            String strLight = "Light Sensor : %f lx";
            if(light>0) tvLight.setText(String.format(strLight,light));
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
