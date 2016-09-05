package happy.happy2;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.widget.Toast.makeText;

/**
 * Created by Heaven on 8/31/2016.
 */
public class settingFragment extends Fragment {
	private Button deleteeverything;
	private Button updatemyplace;
	private Button whereami;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		new MainFragment().mGoogleApiClient.connect();
	}
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.settinglayout2, container, false);
		whereami = (Button) v.findViewById(R.id.whereami);
		updatemyplace = (Button) v.findViewById(R.id.updatemyplace);

		whereami.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, new MapFragment())
						.addToBackStack(null)
						.commit();


			}
		});

		updatemyplace.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				MainFragment x1 = new MainFragment();
				x1.context1=getActivity();
				x1.updatemyplacesetOnClickListener();
			}
		});

		deleteeverything = (Button) v.findViewById(R.id.deleteeverything);

		deleteeverything.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentManager manager = getFragmentManager();
				AreYouSure dialog = new AreYouSure();
				dialog.setTargetFragment(settingFragment.this, 0);
				dialog.show(manager, "salam");

			}
		});
		deleteeverything.setTypeface(new MainFragment().font);



		return v;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == 0) {

		new MainFragment().DeleteEverythingMethod();
		}
	}
}