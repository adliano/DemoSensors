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

public class MagneticSensorActivity extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvMagneticX, tvMagneticY,tvMagneticZ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_magnetic_sensor);

        tvMagneticX = (TextView)findViewById(R.id.text_view_magnetic_X);
        tvMagneticY = (TextView)findViewById(R.id.text_view_magnetic_Y);
        tvMagneticZ = (TextView)findViewById(R.id.text_view_magnetic_Z);

        // get sensor manager
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // get Magnetic sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    /**** SensorEventListener ****/
    SensorEventListener listener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float magneticX = event.values[0];
            float magneticY = event.values[1];
            float magneticZ = event.values[2];

            String strX = "Axis X : %f μT";
            String strY = "Axis Y : %f μT";
            String strZ = "Axis Z : %f μT";

            if(magneticX>0) tvMagneticX.setText(String.format(strX,magneticX));
            if(magneticY>0) tvMagneticY.setText(String.format(strY,magneticY));
            if(magneticZ>0) tvMagneticZ.setText(String.format(strZ,magneticZ));

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
