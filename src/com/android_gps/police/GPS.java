package com.android_gps.police;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPS extends Activity implements ConnectionCallbacks,OnConnectionFailedListener, LocationListener{
	private static final String TAG = GPS.class.getSimpleName();
	 
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
 







 private GoogleApiClient mGoogleApiClient;
 
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
 
    private LocationRequest mLocationRequest;
 
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

	Button btnViewLocation,btnNext;
	
	TextView view1;
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		//gps=new GPSTracker(GPS.this);
		
		
		if (checkPlayServices()) {
			 
            // Building the GoogleApi client
            buildGoogleApiClient();
 
            createLocationRequest();
        }
		
		
		btnViewLocation=(Button) findViewById(R.id.btnViewLocation);
		
		btnNext=(Button) findViewById(R.id.btnNext);
		
		view1=(TextView) findViewById(R.id.lblLocation);
		
		btnViewLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			/*	 double latitude=gps.getLatitude();
				 double longitude=gps.getLongitude();
				if(gps.canGetLocation()){
				view1.setText("Lat: " + latitude + "," + "Long: " + longitude);
			}
			else
			{
				view1.setText("Could not find location.Make sure that your GPS is enabled.");
			}*/
				displayLocation();
				togglePeriodicLocationUpdates();
			}
		});
		
		
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			/*if(gps.canGetLocation())
			{
				Intent intent=null,chooser=null;
				 double latitude=gps.getLatitude();
				 double longitude=gps.getLongitude();
				 intent=new Intent(android.content.Intent.ACTION_VIEW);
					intent.setData(Uri.parse("geo: " + latitude + "," + longitude));
                 chooser=Intent.createChooser(intent,btnNext.getText());                   
					startActivity(chooser);
			}
			else
			{
				gps.showSettingsAlert();
			}
				
			}*/
				
				Intent i=new Intent(GPS.this,MainActivity.class);
				
				startActivity(i);
			}
		});
	}

	private void displayLocation()
	{
		gps=new GPSTracker(GPS.this);
		 double latitude=gps.getLatitude();
		 double longitude=gps.getLongitude();
		if(gps.canGetLocation()){
		view1.setText("Lat: " + latitude + "," + "Long: " + longitude);
	}
	else
	{
		view1.setText("Could not find location.Make sure that your GPS is enabled.");
	}
	}
	@Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
	
	@Override
    protected void onResume() {
        super.onResume();
 
        checkPlayServices();
 
        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
 
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
 




  
		// TODO Auto-generated method stub
	  protected void stopLocationUpdates() {
	        LocationServices.FusedLocationApi.removeLocationUpdates(
	                mGoogleApiClient, this);
	    }
	 
	

private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {

mRequestingLocationUpdates = true;
 
            // Starting the location updates
            startLocationUpdates();
 
            Log.d(TAG, "Periodic location updates started!");
  } 
}



  protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }



 protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }



private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }
 



protected void startLocationUpdates() {
 
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
 
    }




  public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }
 
    @Override
    public void onConnected(Bundle arg0) {
 
        // Once connected with google api, get the location
        displayLocation();
 
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
 
    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


 public void onLocationChanged(Location location) {
        // Assign the new location
        @SuppressWarnings("unused")
		Location mLastLocation = location;
 
        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();
 

displayLocation();
}



	

}


