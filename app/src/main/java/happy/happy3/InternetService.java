package happy.happy3;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Heaven on 9/19/2016.
 */
public class InternetService extends IntentService {
	private static final String TAG = "InternetService";
	public InternetService() {
		super(TAG);
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		//Calendar calendar = Calendar.getInstance();
		//String str= "In"
		Log.i(TAG,TAG);
	}

}
