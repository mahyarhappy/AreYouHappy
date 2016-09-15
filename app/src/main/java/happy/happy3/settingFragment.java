package happy.happy3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by Heaven on 8/31/2016.
 */
public class settingFragment extends Fragment {
	private Button deleteeverything;
	private Button updatemyplace;
	private Button whereami;
	private EditText textViewDownloadMinute;
	private EditText textViewUploadMinute;
	private Switch ImMasterSwitch;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		new MainFragment().mGoogleApiClient.connect();
	}
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.settinglayout2, container, false);
		whereami = (Button) v.findViewById(R.id.whereami);
		updatemyplace = (Button) v.findViewById(R.id.updatemyplace);
		 ImMasterSwitch = (Switch) v.findViewById(R.id.ImMaster);

		 textViewDownloadMinute=(EditText) v.findViewById(R.id.textViewDownloadMinute);
		textViewUploadMinute=(EditText) v.findViewById(R.id.textViewUploadMinute);
		ImMasterSwitch.setChecked(new MainFragment().ImMaster);
		ImMasterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				new MainFragment().ImMaster=isChecked;
				PreferenceManager.getDefaultSharedPreferences(getActivity())
						.edit()
						.putBoolean("ImMaster", isChecked)
						.apply();
			}
		});


		textViewUploadMinute.setText(Integer.toString(new MainFragment().UploadMinute));
		textViewDownloadMinute.setText(Integer.toString(new MainFragment().DownloadMinute));

		textViewUploadMinute.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int m1=Integer.parseInt(textViewUploadMinute.getText().toString());
				new MainFragment().UploadMinute=m1;
				QueryPreferences.setStoredInt(getActivity(), "UploadMinute", m1);
			}
		});






		textViewDownloadMinute.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int m1=Integer.parseInt(textViewDownloadMinute.getText().toString());
				new MainFragment().DownloadMinute=m1;
				QueryPreferences.setStoredInt(getActivity(), "DownloadMinute", m1);
			}
		});






		Switch WorkOfflineSwitch = (Switch) v.findViewById(R.id.WorkOffline);


		WorkOfflineSwitch.setChecked(new MainFragment().WorkOffline);
		WorkOfflineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				new MainFragment().WorkOffline=isChecked;
				PreferenceManager.getDefaultSharedPreferences(getActivity())
						.edit()
						.putBoolean("WorkOffline", isChecked)
						.apply();
			}
		});


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
			makeText(getActivity(),
					R.string.deleteshod,
					Toast.LENGTH_SHORT).show();
		}
	}
}
