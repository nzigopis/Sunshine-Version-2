package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */

public class ForecastFragment extends Fragment
{

    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat=40.6400629&lon=22.9444191&mode=json&units=metric&cnt=7");
        }
        catch (Exception ex) {}

        FetchWeatherTask weatherTask = new FetchWeatherTask(new IWeatherForecastDataListener() {
            @Override
            public void Update(String forecast) {
                // Get a reference to the ListView, and attach this adapter to it.
                ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
                listView.setAdapter(mForecastAdapter);

                List<String> weekForecast = new ArrayList<String>(); //Arrays.asList(data)

                // Now that we have some dummy forecast data, create an ArrayAdapter.
                // The ArrayAdapter will take data from a source (like our dummy forecast) and
                // use it to populate the ListView it's attached to.
                mForecastAdapter =
                        new ArrayAdapter<String>(
                                getActivity(), // The current context (this activity)
                                R.layout.list_item_forecast, // The name of the layout ID.
                                R.id.list_item_forecast_textview, // The ID of the textview to populate.
                                weekForecast);

            }
        });

        weatherTask.execute(url);


        return rootView;
    }
}