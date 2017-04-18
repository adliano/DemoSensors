package com.demo.sensors;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListSensorsActivity extends Activity
{
    SensorManager sensorManager;
    List<Sensor> sensorsList;
    ListView listView;
    Button btHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_sensors);

        init();
    }
    /** initiate objects **/
    public void init()
    {
        // best place to initiate sensors are at onResume callBack
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // by using Sensor.TYPE_ALL will return a List with all sensors available
        sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // i will use a ListView to display the sensors available
        listView = (ListView)findViewById(R.id.list_view_sensors);
        // create the ArrayAdapter using the list of sensors
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_expandable_list_item_1,getSensorsNames(sensorsList));
        // set the adapter to the listView
        listView.setAdapter(adapter);
    }
    /*** Button Back To SensorsActivity ***/
    public void goBack(View v)
    {
        startActivity(new Intent(getBaseContext(),SensorsActivity.class));
    }
    /**** String[] getSensorsNames ***/
    @TargetApi(23)
    public String[] getSensorsNames(List<Sensor> list)
    {
        String[] array = new String[list.size()];
        int i=0;
        for(Sensor sensor  : list)
        {
            String temp = sensor.getStringType();
            array[i] = temp;
            i++;
            //array[i] = sensor.getName();
        }
        return  array;
    }
}
