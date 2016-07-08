package co.alectronic.locationdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;
    TextView txtLoc;
    Button btnLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoc = (Button) findViewById(R.id.btnUpdateLoc);
        txtLoc = (TextView) findViewById(R.id.txtLoc);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider,400,1,this);
        if (loc != null){
            Log.i("Location Info","Location Found");
        }else{
            Log.i("Location Info","Location Not Found");
        }


    }

    protected void onResume(){
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
        updateLoc(location);
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


    public void btnClick(View v){
        switch(v.getId()){
            case R.id.btnUpdateLoc: updateLoc();
                break;
        }
    }




    private void updateLoc(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        spitInfo(locationManager.getLastKnownLocation(provider));
    }

    private void updateLoc(Location loc){
        spitInfo(loc);
    }
//51.696, -0.174023
    private void spitInfo(Location loc){
        String strAddrs = "";
        Log.i("Location Info", getLatLng(loc) );
        List<Address> addrsList = getAddressList(loc);
        if(addrsList.size() > 0) {
            Address addrs = addrsList.get(0);
            strAddrs = " Address= ";
            for(int i = 0; i > addrs.getMaxAddressLineIndex(); i++){ strAddrs = strAddrs + " , " + addrs.getAddressLine(i);}strAddrs = strAddrs + "\n";
            strAddrs = strAddrs + " Country Name= " + addrs.getCountryName()+ "\n";
            strAddrs = strAddrs + " Country code= " + addrs.getCountryCode()+ "\n";
            strAddrs = strAddrs + " Admin Area= " + addrs.getAdminArea()+ "\n";
            strAddrs = strAddrs + " Sub-Admin Area= " + addrs.getSubAdminArea()+ "\n";
            strAddrs = strAddrs + " Locality= " + addrs.getLocality() + "\n";;
            strAddrs = strAddrs + " Sub-Locality= " + addrs.getSubLocality()+ "\n";
            strAddrs = strAddrs + " Feature Name= " + addrs.getFeatureName()+ "\n";
            strAddrs = strAddrs + " ThoroughFare= " + addrs.getThoroughfare()+ "\n";
            strAddrs = strAddrs + " Postal Code= " + addrs.getPostalCode()+ "\n";
            strAddrs = strAddrs + " Phone= " + addrs.getPhone()+ "\n";
            strAddrs = strAddrs + " URL= " + addrs.getUrl()+ "\n";
            strAddrs = strAddrs + " EXTRAS= " + addrs.getExtras();
        }
        Log.i("Location Info", strAddrs);
        txtLoc.setText(strAddrs);
    }




    private String getLatLng(Location loc){
        return "Lat = " + loc.getLatitude() + " Lng = " + loc.getLongitude();
    }

    private List<Address> getAddressList(Location loc){
        try {
            return new Geocoder(getApplicationContext(), Locale.getDefault()).getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<Address> getAddressList(Double lat, Double lng){
        try {
            return new Geocoder(getApplicationContext(), Locale.getDefault()).getFromLocation(lat, lng, 1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
