package com.android_gps.police;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;



import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity{
	private GoogleMap googleMap;
	//private GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        try{
        	initializeMap();
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }


    private void initializeMap() {
		// TODO Auto-generated method stub
		if(googleMap!=null)
		{
			googleMap=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		   
			
			if(googleMap==null)
			{
				Toast.makeText(getApplicationContext(), "Sorry!Unable to create maps", Toast.LENGTH_LONG).show();
			}
		
		}
		
	}
    


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initializeMap();
	}


	

}
