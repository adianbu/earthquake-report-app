package com.example.earthquake;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list, parent, false);
        }

        final Earthquake earthquake = getItem(position);

        String location = earthquake.getLocation();
        int index = location.indexOf(" of ");
        String _loc;
        String _place;
        if(index!=-1) {
            _loc= location.substring(0, index+4);
            _place= location.substring(index + 4, location.length());
        }
        else{
            _loc = "Near";
            _place = location;
        }

        String magnitude = earthquake.getMagnitude();
        float _mag = Float.parseFloat(magnitude);
        // Extract the value for the key called "mag"
//        double magnitude = properties.getDouble("mag");

        TextView mag = (TextView) listItemView.findViewById(R.id.magnitude);
        mag.setText(earthquake.getMagnitude());
        // setting color acc to the magnitude

        // this will return the required color
//        int magnitudeColorResourceId = set_color(_mag);
        // a gradient drawable is the one that can change its color
        GradientDrawable circle = (GradientDrawable) mag.getBackground();
//        circle.setColor(magnitudeColorResourceId);

        TextView loc = (TextView) listItemView.findViewById(R.id.location);
        loc.setText(_loc);
        TextView place = (TextView) listItemView.findViewById(R.id.place);
        place.setText(_place);
//        TextView date  = (TextView) listItemView.findViewById(R.id.date);
//        date.setText(earthquake.getDate());
//        TextView time  = (TextView) listItemView.findViewById(R.id.time);
//        time.setText(earthquake.getTime());

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(earthquake.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);


        // opeing the details link on clicking a certain item
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent earthquakeIntent = new Intent(Intent.ACTION_VIEW);
                earthquakeIntent.setData(Uri.parse(earthquake.getUrl()));
                getContext().startActivity(earthquakeIntent);
            }
        });

        return listItemView;


    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
