package happy.happy2;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.widget.Toast.makeText;

/**
 * Created by Heaven on 8/6/2016.
 */
public class MapFragment extends SupportMapFragment {
	//private GoogleApiClient mGoogleApiClient;
	private GoogleMap mMap;

	private Bitmap mMapImage;
	//private GalleryItem mMapItem;
	private Location mCurrentLocation;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap googleMap) {
				mMap = googleMap;
				updateUI();
			}
		});
		Log.i("salam","chetori");

	}
	private void updateUI() {
		if (mMap == null ) {
			return;
		}
		Double long1= QueryPreferences.getStoredDouble(getActivity(), "Longitude");
		Double lat1= QueryPreferences.getStoredDouble(getActivity(), "Latitude");
		//mCurrentLocation=new MainFragment().mCurrentLocation;
		if (long1==-1 || lat1==-1){//(mCurrentLocation == null ) {
			makeText(getActivity(),
					"No Data. Seems Not Connected. You May need to update Google Play Services or ...",
					Toast.LENGTH_LONG).show();
				return;
		}


		//LatLng itemPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
		LatLng itemPoint = new LatLng(lat1,long1);
		//LatLng myPoint = new LatLng(
		//		mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

	//	double longitude = mCurrentLocation.getLongitude();


		LatLngBounds bounds = new LatLngBounds.Builder()
				.include(itemPoint)
				//.include(myPoint)
				.build();
		int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
		//CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(itemPoint, 16);
		mMap.animateCamera(update);
		MarkerOptions myMarker = new MarkerOptions()
				.position(itemPoint);
		//mMap.clear();
		//mMap.addMarker(itemMarker);
		mMap.addMarker(myMarker);
		//mMap.animateCamera(CameraUpdateFactory.zoomOut());

	}

/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.showhappiness, container, false);



		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setNumUpdates(1);
		request.setInterval(0);

		mGoogleApiClient.connect();
		LocationServices.FusedLocationApi
				.requestLocationUpdates(mGoogleApiClient, request, new LocationListener() {
					@Override
					public void onLocationChanged(Location location) {
						Log.i("LocatrFragment", "Got a fix: " + location);

					}
				});

		makeText(getActivity(),
				"LocatrFragment"+"Got a fix" ,
				Toast.LENGTH_SHORT).show();



		return v;
	}
	public void onStart() {
		super.onStart();
		//mGoogleApiClient.connect();
	}
	@Override
	public void onStop() {
		super.onStop();
		//mGoogleApiClient.disconnect();
	}
	*/
	/*
@Override
public void onStart() {
	super.onStart();
	// getActivity().invalidateOptionsMenu();
	new MainFragment().mGoogleApiClient.connect();
	updateUI();
}
	@Override
	public void onStop() {
		super.onStop();
		new MainFragment().mGoogleApiClient.disconnect();
		updateUI();
	}
	*/
}
