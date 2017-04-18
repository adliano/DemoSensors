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
import android.widget.Toast;

public class TemperatureSensorActivity extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teperature_sensor);

        tvTemp = (TextView)findViewById(R.id.text_view_temperature);

        // get sensor manager
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // get temperature sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(sensor != null) manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        else Toast.makeText(getBaseContext(),"Temperature Sensor not Available!",
                Toast.LENGTH_LONG).show();
    }

    /**** SensorEventListener ****/
    SensorEventListener listener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float temperature = event.values[0];
            String str = "Temperature : %f °C";
            if(temperature>0)tvTemp.setText(String.format(str,temperature));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    };
    /*** Button Back To SensorsActivity ***/
    public void goBack(View v)
    {
        startActivity(new Intent(getBaseContext(),SensorsActivity.class));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(sensor != null) manager.unregisterListener(listener);
    }
}
