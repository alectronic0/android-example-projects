package co.alectronic.hikerwatch;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;

    TextView txtLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        txtLoc = (TextView)findViewById(R.id.txtLoc);
        provider = locationManager.getBestProvider(new Criteria(),false);

        updateTxt(locationManager.getLastKnownLocation(provider));

    }

    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }

    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider,400,1,this);
    }


    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        updateTxt(location);
    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *                 <p/>
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *                 <p/>
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }


    private void updateTxt(Location location){
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        double alt = location.getAltitude();
        double bear = location.getBearing();
        float time = location.getTime();
        double acc = location.getAccuracy();
        String addr = "";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> listAddress = null;

        try {
            listAddress = geocoder.getFromLocation(lat,lng,1);
            if(listAddress != null && listAddress.size() > 0){ addr = listAddress.get(0).getAddressLine(0).toString() + " " + listAddress.get(0).getPostalCode(); }
        } catch (IOException e) {
            e.printStackTrace();
        }


//51.6960636,-0.1870108
        String s = "Lat " + lat +
                "\nlng " + lng +
                "\nAlt " + alt +
                "\nBear " + bear +
                "\nTime " + time +
                "\nAcc " + acc +
                "\nAddr " + addr;

        txtLoc.setText(s);
        Log.i("locInfo",s);
    }

}
