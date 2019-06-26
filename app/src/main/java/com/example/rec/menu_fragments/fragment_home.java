package com.example.rec.menu_fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Location;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rec.R;
import com.example.rec.myads_fragments.ProductAdapter;
import com.example.rec.myads_fragments.aanishPRoduct;
import com.example.rec.myads_fragments.product;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.example.rec.R.id.map;

public class fragment_home extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private MapView mapView;
    String uidz;
    ArrayList<aanishPRoduct>aanishbhai=new ArrayList();
    ImageView backBtn;
    Spinner Location;
    ImageView filterBtn;
    private static final int PERMISSION_REQUEST_CODE = 1;
    ImageView searchBtn;
    DrawerLayout drawer;
    private LocationManager lm;
    private MinDisLocationListener locationListener;
    ImageView mic;
    String markerAddress = null;
    boolean check = false;
        ArrayList<product> productList = new ArrayList<>();
    DatabaseReference mUserDatabase;
    String current_uid;

    class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container,false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String speachaddress = result.get(0);
                    Activity a = getActivity();
                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(speachaddress,
                            a.getApplicationContext(), new GeocoderHandler());
                }
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapView = (MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_map_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        backBtn = viewX.findViewById(R.id.drawer);
        filterBtn = viewX.findViewById(R.id.filter);
        searchBtn = viewX.findViewById(R.id.search);
        drawer = ((AppCompatActivity)getActivity()).findViewById(R.id.drawer_layout);
        mic = viewX.findViewById(R.id.mic);

        //firebase
        /*                        Activity a = getActivity();
                        GeocodingLocation locationAddress = new GeocodingLocation();
                        locationAddress.getAddressFromLocation(fulladdress,
                                a.getApplicationContext(), new GeocoderHandler());*/




        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("myAds");
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    uidz = dataSnapshot1.child("uid").getValue().toString();
                    String type = dataSnapshot1.child("type").getValue().toString();
                    if(current_uid.equals(uidz) && type.equals("seller"))
                    {
                        aanishPRoduct l = dataSnapshot1.getValue(aanishPRoduct.class);
                        aanishbhai.add(l);
                    }

                }
                //System.out.println("ye aray list ki length hai   " + aanishbhai.size());
                //adding some items to our list
                for(int i=0;i<aanishbhai.size()-1;i++) {
                    System.out.println("kya ha hai");
                    Activity a = getActivity();
                    GeocodingLocation locationAddress = new GeocodingLocation();
                    locationAddress.getAddressFromLocation(aanishbhai.get(i).getLocation(),
                            a.getApplicationContext(), new GeocoderHandler());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                Activity a = getActivity();
                if (i.resolveActivity(a.getPackageManager()) != null) {
                    startActivityForResult(i, 1);
                } else {
                    Toast.makeText(a.getBaseContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locationListener = new MinDisLocationListener();
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, this.locationListener);
        } else {
            requestPermission();
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, this.locationListener);




        getMarkers();

        Location = viewX.findViewById(R.id.spinner);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //yahan krna hai search btn ka kaam saqib, this is the onclickListener of the search btn
                final List<String> list = new ArrayList<String>();
                list.add("Johar Town");
                list.add("Faisal Town");
                list.add("DHA");
                list.add("Model Town");
                list.add("Garden Town");
                list.add("Shalimar Town");
                list.add("Iqbal Town");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Location.setAdapter(dataAdapter);

                Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String addresspartone = list.get(position);
                        String addressparttwo = " Lahore Pakistan";
                        String fulladdress = markerAddress + addresspartone + addressparttwo;

                        Activity a = getActivity();
                        GeocodingLocation locationAddress = new GeocodingLocation();
                        locationAddress.getAddressFromLocation(fulladdress,
                                a.getApplicationContext(), new GeocoderHandler());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                (getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_filters()).commit();
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //idher intent bana kr us advertisement pr lay jaye jis advertisement ka marker hai
        return false;
    }

    public void getMarkers(){
        String add = "852 B";
        markerAddress = add + " ";
    }

    public class GeocodingLocation {

        private final String TAG = "GeocodingLocation";


        public void getAddressFromLocation(final String locationAddress,
                                           final Context context, final Handler handler) {
            Activity a = getActivity();
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    String result = null;
                    double longi = -1;
                    double lati = -1;
                    try {
                        List addressList = geocoder.getFromLocationName(locationAddress, 1);
                        if (addressList != null && addressList.size() > 0) {
                            Address address = (Address) addressList.get(0);
                            longi = address.getLongitude();
                            lati = address.getLatitude();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Unable to connect to Geocoder", e);
                    } finally {
                        Message message = Message.obtain();
                        message.setTarget(handler);
                        if (longi != -1 && lati != -1) {
                            message.what = 1;
                            LatLng newLoc = new LatLng(lati, longi);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 15f));
                            MarkerOptions marker = new MarkerOptions().position(newLoc);
                            googleMap.addMarker(marker);

                        } else {
                            message.what = 1;
                            Toast.makeText(context, "Cannot find location", Toast.LENGTH_LONG).show();
                        }
                        message.sendToTarget();
                    }
                }
            });
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(getActivity(), "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setOnMapLoadedCallback(this);
//        drawMarker(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(49.39, -124.83), 15f));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.7750, 122.4183))
                .title("San Francisco")
                .snippet("Population: 776733"));
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, this.locationListener);
        } else {
            requestPermission();
        }
    }

    public void drawMarker(double lat, double lon) {
        if (googleMap != null) {
            LatLng newLoc = new LatLng(lat, lon);
            //MarkerOptions marker = new MarkerOptions().position(newLoc);
            //marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            //googleMap.addMarker(marker);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 15f));
        }
    }

    @Override
    public void onMapLoaded() {

    }

    public class MinDisLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if(check == false) {
                check = true;
                //Log.d("location", "onLocationChanged");
                drawMarker(location.getLatitude(), location.getLongitude());
                //Toast.makeText(getActivity(), "onLocationChanged", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            /*Log.d("location", "onStatusChanged");
            Toast.makeText(getActivity(), "onStatusChanged", Toast.LENGTH_SHORT).show();*/
        }

        @Override
        public void onProviderEnabled(String provider) {
            /*Log.d("location", "onProviderEnabled");
            Toast.makeText(getActivity(), "onProviderEnabled", Toast.LENGTH_SHORT).show();*/
        }

        @Override
        public void onProviderDisabled(String provider) {
            /*Log.d("location", "onProviderEnabled");
            Toast.makeText(getActivity(), "onProviderEnabled", Toast.LENGTH_SHORT).show();*/
        }
    }

}