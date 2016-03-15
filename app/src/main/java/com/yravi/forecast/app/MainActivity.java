package com.yravi.forecast.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        ArrayAdapter<String> mForecastAdapter;
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//
//            // Create some dummy data for the ListView.  Here's a sample weekly forecast
//            String[] data = {
//                    "Mon 6/23 - Sunny - 31/17",
//                    "Tue 6/24 - Foggy - 21/8",
//                    "Wed 6/25 - Cloudy - 22/17",
//                    "Thurs 6/26 - Rainy - 18/11",
//                    "Fri 6/27 - Foggy - 21/10",
//                    "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
//                    "Sun 6/29 - Sunny - 20/7"
//            };
//            List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));
//
//            // Now that we have some dummy forecast data, create an ArrayAdapter.
//            // The ArrayAdapter will take data from a source (like our dummy forecast) and
//            // use it to populate the ListView it's attached to.
//            mForecastAdapter =
//                    new ArrayAdapter<String>(
//                            getActivity(), // The current context (this activity)
//                            R.layout.list_item_forecast, // The name of the layout ID.
//                            R.id.list_item_forecast_textview, // The ID of the textview to populate.
//                            weekForecast);
//
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//
//            // Get a reference to the ListView, and attach this adapter to it.
//            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
//            listView.setAdapter(mForecastAdapter);
//
//            return rootView;
//        }
//    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + location + ", no receiving apps installed!");
        }
    }
}
