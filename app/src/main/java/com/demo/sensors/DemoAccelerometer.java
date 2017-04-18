package com.demo.sensors;

/**
 * Adriano Alves
 * May 13 2016
 * Activity to work with Accelerometer Sensor
 *
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
import android.widget.Toast;


public class DemoAccelerometer extends Activity
{
    SensorManager manager;
    Sensor sensor;
    TextView tvX, tvY, tvZ, tvStatus, tvFace;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_demo_accelerometer);

        // 1) get a SensorManager
        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        // 2) get the Sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // initiate the TextViews
        tvX = (TextView)findViewById(R.id.text_view_x_axis);
        tvY = (TextView)findViewById(R.id.text_view_y_axis);
        tvZ = (TextView)findViewById(R.id.text_view_z_axis);
        tvStatus = (TextView)findViewById(R.id.tv_status_accelerometer);
        tvFace = (TextView)findViewById(R.id.tv_face_up_down);
    }
    /****** SensorEventListener ******/
    // 3) Create a SensorEventListener
    SensorEventListener listener = new SensorEventListener()
    {
        float msToSecond = 1.0f/1000000000.0f;
        float tempTimeStamp;
        float[] angle;

        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String strX = "Axis X : %f";
            String strY = "Axis Y : %f";
            String strZ = "Axis Z : %f";
            // show values of the axis
            //if(x>0) tvX.setText(String.format(strX,x));
            //if(y>0) tvY.setText(String.format(strY,y));
            //if(z>0) tvZ.setText(String.format(strZ,z));

            // show angles
            if(tempTimeStamp !=0)
            {
                float temp = (event.timestamp - tempTimeStamp) * msToSecond;
                //tvX.setText(String.format(strX,(x*temp)));
                //tvY.setText(String.format(strY,(y*temp)));
                //tvZ.setText(String.format(strZ,(z*temp)));
                tvX.setText(String.format(strX,x));
                tvY.setText(String.format(strY,y));
                tvZ.setText(String.format(strZ,z));

                String strStatus = "";
                if(x >= 9) strStatus = "Landscape";
                if(x <= -9) strStatus = "Reverse Landscape";
                if(y >= 9) strStatus = "Portrait";
                if(y <= -9) strStatus = "Reverse Portrait";
                tvStatus.setText(strStatus);

                String strFace = "";
                if(z >= 7) strFace = "Face UP";
                if(z <= -7) strFace = "Face Down";
                tvFace.setText(strFace);
            }

            tempTimeStamp = event.timestamp;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }
        // 4) register the SensorListener onResume()
        // 5) unregister the SensorListener onPause()
    };
    /**  onResume **/
    @Override
    protected void onResume()
    {
        super.onResume();
        // last argument its the rate you give as an argument
        if(sensor != null)
        {
            manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_UI);
        }
        else mkToast("Accelerometer Sensor not Available");
    }
    /** onPause **/
    @Override
    protected void onPause()
    {
        super.onPause();
        if(sensor != null)
        {
            manager.unregisterListener(listener);
        }
    }
    /*** Button Back To SensorsActivity ***/
    public void goBack(View v)
    {
        startActivity(new Intent(getBaseContext(),SensorsActivity.class));
    }
    /****** ******/
    public void mkToast(String msg)
    {
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
}
