package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //for preference
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL_full =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;
    private EarthquakeRecyclerAdapter rAdapter;

    private TextView mEmptyStateTextView;
    private List<Earthquake> list;
    RecyclerView earthquakeRecyclerListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);


        //Recycler view
         earthquakeRecyclerListView = (RecyclerView) findViewById(R.id.list);

//        ArrayList<Earthquake> earthquakes = new ArrayList<>(EarthquakeLoader(this,USGS_REQUEST_URL));

//        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);



        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
//        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, earthquakes);

        // Create a new adapter that takes an empty list of earthquakes as input
//        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(mAdapter);
//
//        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // Find the current earthquake that was clicked on
//                Earthquake currentEarthquake = mAdapter.getItem(position);
//
//                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
//
//                // Create a new intent to view the earthquake URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
//
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
//            }
//        });


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }

    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());

        // Create a new loader for the given URL
//        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // Clear the adapter of previous earthquake data
//        list.clear();

        // Hide loading indicator because the data has been loaded
        findViewById(R.id.progressBar).setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
//        mEmptyStateTextView.setText(R.string.no_earthquakes);
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
//        ArrayList<Earthquake> e= new ArrayList<Earthquake>();
        if (earthquakes != null && !earthquakes.isEmpty()) {
//            for (int i=0;i<earthquakes.size();i++){
//
//            }
//            if (list!=null)
//            {list.clear();}
             list=earthquakes;
            finished(list);
//            mAdapter.addAll(earthquakes);

        }
    }

    public void finished(List<Earthquake> earthquakes) {

        earthquakeRecyclerListView.setLayoutManager(new LinearLayoutManager(this));
        rAdapter = new EarthquakeRecyclerAdapter( this,earthquakes);
        earthquakeRecyclerListView.setAdapter(rAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        list.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

