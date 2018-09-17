package test.com.assessment.Activity;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> listLatlng;
    private LatLng latLng;
    private String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        location=getIntent().getStringExtra("location");

        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(location, 1);

                listLatlng = new ArrayList<LatLng>(addresses.size());
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        listLatlng.add(new LatLng(a.getLatitude(), a.getLongitude()));
                    }
                }

            } catch (IOException e) {
               e.printStackTrace();
            }
        }
        latLng = listLatlng.get(0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(latLng).title(location)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
