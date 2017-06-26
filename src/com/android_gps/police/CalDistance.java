package com.android_gps.police;

public class CalDistance {

	public double Distance(String lat1,String lat2,String lon1,String lon2)
	{
		final int R=6371;
		double dlat1=Double.parseDouble(lat1);
		double dlon1=Double.parseDouble(lon1);
		double dlat2=Double.parseDouble(lat2);
		double dlon2=Double.parseDouble(lon2);
		
		double latDistance=Math.toRadians(dlat2-dlat1);
		double lonDistance=Math.toRadians(dlon2-dlon1);
		
		double a=Math.sin(latDistance/2)*Math.sin(latDistance/2)+Math.cos(Math.toRadians(dlat1))*Math.cos(Math.toRadians(dlat2))*Math.sin(lonDistance/2)*Math.sin(lonDistance/2);
		
		double c=2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		
		double distance=R*c;
		return distance;
	}
}
