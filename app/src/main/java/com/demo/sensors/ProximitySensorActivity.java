package com.demo.sensors;

/**
 * Adriano Alves
 * May 15 2016
 * Activity to handle Proximity Sensor
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.hardware.*;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

public class ProximitySensorActivity extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvProximityValue, tvProximityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_proximity_sensor);

        tvProximityValue = (TextView)findViewById(R.id.text_view_proximity_value);
        tvProximityStatus = (TextView)findViewById(R.id.text_view_proximity_status);

        // get Sensor manager
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // get Proximity sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }
    /**** SensorEventListener ****/
    SensorEventListener listener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float proximity = event.values[0];
            String str = "Proximity : %f cm";
            // proximity sensor will return max value or 0
            // max value means far and 0 means close
            float maxRange = sensor.getMaximumRange();

            if(proximity >= 0) tvProximityValue.setText(String.format(str,proximity));
            if(proximity >= maxRange) tvProximityStatus.setText("Far");
            else tvProximityStatus.setText("Close");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }
    };
    /*** onResume ***/
    @Override
    protected void onResume()
    {
        super.onResume();
        manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_UI);
    }
    /*** onPause ***/
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
