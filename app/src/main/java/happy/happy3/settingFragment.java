package happy.happy3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
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

	private EditText textViewDownloadMinute;
	private EditText textViewUploadMinute;
	//private SwitchCompat ImMasterSwitch;
	private Button chooseostan;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.settinglayout2, container, false);

		SwitchCompat ImMasterSwitch = (SwitchCompat) v.findViewById(R.id.ImMaster);
chooseostan=(Button) v.findViewById(R.id.selectyourostanbutton);

		chooseostan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, new chooseostanfragment())
						.addToBackStack(null)
						.commit();
			}
		});

		 textViewDownloadMinute=(EditText) v.findViewById(R.id.textViewDownloadMinute);
		textViewUploadMinute=(EditText) v.findViewById(R.id.textViewUploadMinute);



		ImMasterSwitch.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("ImMaster", false));



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
				String temp20=textViewDownloadMinute.getText().toString();
				int m1;
				if(temp20.equals("")){
					m1=0;
				}else{
					m1=Integer.parseInt(temp20);
				}
				new MainFragment().UploadMinute=m1;
				QueryPreferences.setStoredInt(getActivity(), "UploadMinute2", m1);
			}
		});






		textViewDownloadMinute.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String temp20=textViewDownloadMinute.getText().toString();
				int m1;
				if(temp20.equals("")){
					m1=0;
				}else{
					m1=Integer.parseInt(temp20);
				}

				new MainFragment().DownloadMinute=m1;
				QueryPreferences.setStoredInt(getActivity(), "DownloadMinute2", m1);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});






		SwitchCompat WorkOfflineSwitch = (SwitchCompat) v.findViewById(R.id.WorkOffline);



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


		SwitchCompat ForceUploadSwitch = (SwitchCompat) v.findViewById(R.id.ForceUpload);

		ForceUploadSwitch.setChecked(new MainFragment().ForceUpload);
		ForceUploadSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				new MainFragment().ForceUpload=isChecked;
				PreferenceManager.getDefaultSharedPreferences(getActivity())
						.edit()
						.putBoolean("ForceUpload", isChecked)
						.apply();
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
