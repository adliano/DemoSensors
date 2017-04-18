package com.demo.sensors;

/**
 * Adriano Alves
 * May 12 2016
 * Activity to list all other Activities Related with sensors
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SensorsActivity extends Activity
{
    // string array to set text on a listView
    String[] activitiesNames;
    // Class array to use clicked item on a intent that will call a activity
    Class[] classes;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sensor_activity);

        init();

    }
    /** initiate objects **/
    public void init()
    {
        activitiesNames = getActivitiesNames();
        classes = getAvailableActivities();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_expandable_list_item_1, activitiesNames);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(),classes[position]);
                startActivity(intent);
            }
        };

        ListView listView = (ListView)findViewById(R.id.list_activities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
    }
    /*** String[] getActivitiesNames() ***/
    public String[] getActivitiesNames()
    {
        // Using ActivityInfo you can get a array with all available activitiesNames in the project
        ActivityInfo[] infos = new ActivityInfo[0];

        try
        {
            PackageInfo pgk = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES);

            infos = pgk.activities;

        }
        catch(PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for(ActivityInfo ai : infos)
        {
            // void load current Activity name
            if(!(ai.name).contains(this.getClass().getName()))
            {
                // activity name format comes like com.demo.sensors.Activity
                // used substring to get only the name
                arrayList.add(ai.name.substring(ai.name.lastIndexOf(".")+1));
            }
        }

        return(arrayList.toArray(new String[arrayList.size()]));
    }
    /*** Class[] getAvailableActivities() ***/
    public Class[] getAvailableActivities()
    {
        ArrayList<Class> arrayList = new ArrayList<>();
        ActivityInfo[] infos;

        try
        {
            infos = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES).activities;

            for(ActivityInfo a : infos)
            {
                if(!(a.name).contains(this.getClass().getName()))
                {
                    arrayList.add(Class.forName(a.name));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return(arrayList.toArray(new Class[arrayList.size()]));
    }
}
