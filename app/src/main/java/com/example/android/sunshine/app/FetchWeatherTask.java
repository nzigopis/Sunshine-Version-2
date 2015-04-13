package com.example.android.sunshine.app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nzigopis on 29/03/2015.
 */
public class FetchWeatherTask extends AsyncTask<Double, Integer, String>
{
    IWeatherForecastDataListener _listener;

    public FetchWeatherTask(IWeatherForecastDataListener listener) {
        _listener = listener;
    }

    @Override
    protected void onPostExecute(String result) {
        if (_listener != null && result != null)
            _listener.Update(result);
    }

    @Override
    protected String doInBackground(Double... params)
    {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        if (params == null || params.length == 0)
            return forecastJsonStr;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
<<<<<<< HEAD
            // lat=40.6400629, lon=22.9444191
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + params[0] +
                    "&lon=" + params[1] + "&mode=json&units=metric&cnt=7");
=======
            URL url = params[0];
>>>>>>> cdee05a33e986d1abb73f245cedd072546bd260d

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("ForecastFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ForecastFragment", "Error closing stream", e);
                }
            }
        }
        return forecastJsonStr;
    }

}
