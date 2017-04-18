package com.demo.sensors;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class PressureActivity extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pressure);

        tvPressure = (TextView)findViewById(R.id.text_view_pressure);

        // get the sensor manager
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // get the Pressure sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }
    /**** SensorEventListener ****/
    SensorEventListener listener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float pressure = event.values[0];
            String str = "Pressure : %f hPa";
            if(pressure>0) tvPressure.setText(String.format(str,pressure));
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

