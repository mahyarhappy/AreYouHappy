package happy.happy3;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends SingleFragmentActivity {




    @Override
     protected Fragment createFragment() {
        return new MainFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PollService.setServiceAlarm(this,true);


    }
    public void onRadioButtonClicked(View v){

    }
    public void ReplaceFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, NextFragment)
                .addToBackStack(null)
                .commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GooglePlayServicesUtil
                    .getErrorDialog(errorCode, this, 0,
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
// Leave if services are unavailable.
                                    finish();
                                }
                            });
            errorDialog.show();
        }
    }
}
