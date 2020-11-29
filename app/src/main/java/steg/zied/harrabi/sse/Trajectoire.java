package steg.zied.harrabi.sse;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trajectoire extends FragmentActivity implements OnMapReadyCallback {
Button ok,no;
    private GoogleMap mMap;
    LatLng PARIS =null;
    double lat1,lon1;
    LatLng Tunis = null;
    private LatLngBounds latlngBounds;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private int width,height;
    private Polyline newPolyline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajectoire);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ok=(Button)findViewById(R.id.ok);
        final String latt=getIntent().getStringExtra("lat");
        final String lonn=getIntent().getStringExtra("lon");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cin=getIntent().getStringExtra("cin");
                if(ContextCompat.checkSelfPermission(Trajectoire.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(Trajectoire.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
                    {
                        ActivityCompat.requestPermissions(Trajectoire.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},1);

                    }
                    else
                    {
                        ActivityCompat.requestPermissions(Trajectoire.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},1);

                    }
                }
                else {
                    LocationManager lo = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location l = lo.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (l != null) {
                        lat1 = l.getLatitude();
                        lon1 = l.getLongitude();
                        try {
                            StringRequest sr=new StringRequest(Request.Method.POST, Params.ip+"emp.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try
                                    {

                                    }
                                    catch(Exception e)
                                    {

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            })
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> para=new HashMap<String, String>();
                                    para.put("lat",String.valueOf(lat1));
                                    para.put("lon",String.valueOf(lon1));
                                    para.put("cin",cin);
                                    return para;
                                }
                            };
                            RequestQueue rq= Volley.newRequestQueue(Trajectoire.this);
                            rq.add(sr);

                        } catch (Exception e) {

                        }
                    }
                }

Intent i=new Intent(Trajectoire.this,PrendreMesure.class);
                i.putExtra("lat",latt);
                i.putExtra("lon",lonn);
                i.putExtra("cin",cin);
                startActivity(i);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final double lat1,lon1;
        double lat2=0,lon2=0;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(34.74, 10.91);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.74, 10.65), 10.0f));

        String adresse=getIntent().getStringExtra("adr_comp");
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            Log.i("permission","manque permission");
        }
        else {
            try {
                Toast.makeText(Trajectoire.this, "Permission existante", Toast.LENGTH_SHORT).show();
                LocationManager lo = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location l = lo.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                final String c=getIntent().getStringExtra("cin");
                lat1 = l.getLatitude();
                lon1 = l.getLongitude();
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat1,lon1)).title("ma position"));
                double latt= Double.parseDouble(getIntent().getStringExtra("lat"));
                double lonn=Double.parseDouble(getIntent().getStringExtra("lon"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(latt,lonn)).title("mission"));
                Toast.makeText(Trajectoire.this,lat1+"**"+lon1+"*****"+latt+"**"+lonn,Toast.LENGTH_LONG);
                findDirections(lat1, lon1, latt, lonn, GMapV2Direction.MODE_WALKING);

                StringRequest sr = new StringRequest(Request.Method.POST, Params.ip+"location.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> para = new HashMap<>();
                        para.put("latitude", String.valueOf(lat1));
                        para.put("longitude", String.valueOf(lon1));
                        para.put("cin", c);
                        return para;
                    }
                };
                RequestQueue rq1 = Volley.newRequestQueue(getApplicationContext());
                rq1.add(sr);
            } catch (Exception e) {
            }
        }






    }

    //tracer les segments du trajectoire
    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.BLUE);

        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null)
        {
            newPolyline.remove();
        }
        newPolyline = mMap.addPolyline(rectLine);

        latlngBounds = createLatLngBoundsObject(Tunis, PARIS);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));


    }


    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
    {
        if (firstLocation != null && secondLocation != null)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }



    //chercher la possibilité des directions ainsi que le plus court chemin
    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode) {
        final String USER_CURRENT_LAT = "user_current_lat";
        final String USER_CURRENT_LONG = "user_current_long";
        final String DESTINATION_LAT = "destination_lat";
        final String DESTINATION_LONG = "destination_long";
        final String DIRECTIONS_MODE = "directions_mode";
        final Map<String, String> map = new HashMap<String, String>();
        map.put(USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(DIRECTIONS_MODE, mode);

        new AsyncTask<Map<String, String>, Object, ArrayList<LatLng>>() {


            @Override
            public void onPostExecute(ArrayList result) {

                handleGetDirectionsResult(result);

            }

            @Override
            protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
                Map<String, String> paramMap = map;
                try {
                    LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)), Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
                    LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)), Double.valueOf(paramMap.get(DESTINATION_LONG)));
                    GMapV2Direction md = new GMapV2Direction();
                    Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
                    ArrayList<LatLng> directionPoints = md.getDirection(doc);
                    return directionPoints;
                } catch (Exception e) {

                    return null;
                }
            }


        }.execute();
    }
}
