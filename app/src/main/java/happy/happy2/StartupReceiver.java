package happy.happy2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Heaven on 8/3/2016.
 */
public class StartupReceiver extends BroadcastReceiver {
	private static final String TAG = "StartupReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.i(TAG, "Received broadcast intent: " + intent.getAction());
		PollService.setServiceAlarm(context,true);
	}
}