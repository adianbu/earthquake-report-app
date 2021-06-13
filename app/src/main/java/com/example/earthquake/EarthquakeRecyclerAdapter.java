package com.example.earthquake;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeRecyclerAdapter extends RecyclerView.Adapter<EarthquakeRecyclerAdapter.ViewHolder> {
    Context context;
    List<Earthquake> earthquakes;

    public EarthquakeRecyclerAdapter(Context context, List<Earthquake> earthquakes) {
        this.context = context;
        this.earthquakes = earthquakes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.earthquake_recycler_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  EarthquakeRecyclerAdapter.ViewHolder holder, int position) {
         Earthquake earthquake = earthquakes.get(position);
        String location = earthquake.getLocation();


        //for location and place
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
        holder.loc.setText(_loc);
        holder.place.setText(_place);

        String formattedMagnitude = formatMagnitude(earthquake.getMagnitude());
        Date dateObject = new Date(earthquake.getTimeInMilliseconds());
        //for magnitude
        // Display the magnitude of the current earthquake in that TextView
        holder.magnitudeView.setText(formattedMagnitude);
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        holder.dateView.setText(formattedDate);

        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        holder.timeView.setText(formattedTime);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                   // Convert the String URL into a URI object (to pass into the Intent constructor)
                   Uri earthquakeUri = Uri.parse(earthquake.getUrl());

                   // Create a new intent to view the earthquake URI
                   Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                   // Send the intent to launch a new activity
                   context.startActivity(websiteIntent);
               }
           }
        );
    }

    @Override
    public int getItemCount() {
        return earthquakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateView;
        TextView timeView;
        TextView place;
        TextView loc;
        TextView magnitudeView;
        View parentView;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
             parentView=itemView;
             magnitudeView = (TextView) itemView.findViewById(R.id.magnitude);
             loc = (TextView) itemView.findViewById(R.id.location);
             place = (TextView) itemView.findViewById(R.id.place);
             dateView = (TextView) itemView.findViewById(R.id.date);
             timeView = (TextView) itemView.findViewById(R.id.time);
        }
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

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }
}

