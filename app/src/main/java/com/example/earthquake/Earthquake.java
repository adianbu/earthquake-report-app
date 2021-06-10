package com.example.earthquake;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Earthquake {

    String mag;
    private double mMagnitude;
    String location;
    String date;
    String time;
    String url;
    /** Time of the earthquake */
    private long mTimeInMilliseconds;

    public String getDate() {
        return date;
    }

    public void setDate(String dates) {
        this.date = dates;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    Earthquake(String mag, String loc, String date, String time, String url){
        this.mag = mag;
        this.location = loc;
        this.date = date;
        this.time =time;
        this.url = url;
    }

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds) {
        this.mMagnitude = magnitude;
        this.location = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {

        long timeInMilliseconds = 1454124312220L;
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        return time;
    }

    /**
     * Returns the time of the earthquake.
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
