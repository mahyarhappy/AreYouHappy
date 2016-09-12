package happy.happy3;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Heaven on 8/3/2016.
 */
public class PollService extends IntentService {
	static int i11=0;
	private static final String TAG = "PollService";
	Context context1;
	private static final int POLL_INTERVAL = 1000 * 60; // 60 seconds
	public static Intent newIntent(Context context) {
		Context context1= context;
		return new Intent(context, PollService.class);
	}
	public PollService() {
		super(TAG);
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		Calendar calendar = Calendar.getInstance();
		String str= "Are You Happy Right Now? " +calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
		Log.i(TAG,str);
		i11+=1;
		notified(str);
	}

	public static void setServiceAlarm(Context context, boolean isOn) {
		Intent i = PollService.newIntent(context);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		AlarmManager alarmManager = (AlarmManager)
				context.getSystemService(Context.ALARM_SERVICE);
		if (isOn) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			//calendar.set(Calendar.HOUR_OF_DAY, 0);

			calendar.set(Calendar.MINUTE  ,0);
			//calendar.set(Calendar.MINUTE  ,calendar.get(Calendar.MINUTE)+1);
			calendar.set(Calendar.SECOND  ,0);
			int h1=calendar.get(Calendar.HOUR_OF_DAY);
			Double h2= new Double(Math.ceil(h1/6)*6);
			int h3=h2.intValue();
			if(h3<24){
				calendar.set(Calendar.HOUR_OF_DAY, h3);
			}else{
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.add(Calendar.DAY_OF_MONTH, 1);

			}
		//	calendar.set(Calendar.HOUR_OF_DAY, 0);

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
			if(1==1){
				//this is actual alarm
				alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
						1000 * 60 * 60*6, pi);
			}else{
				//this is testing alarm
				alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime(), POLL_INTERVAL, pi);
			}




		} else {
			alarmManager.cancel(pi);
			pi.cancel();
		}
	}
	public void notified(String str){
		Resources resources = getResources();
		Intent i = new Intent(this, MainActivity.class);
		//  Intent i = PhotoGalleryActivity.newIntent(this);
	//	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		//stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent to the top of the stack
		//stackBuilder.addNextIntent(resultIntent);

	//	PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
	//			PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Notification notification = new NotificationCompat.Builder(this)
				.setTicker("Are You Happy")
				//.setSmallIcon(android.R.drawable.ic_menu_report_image)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("Are You Happy")
				.setContentText(str)
				 .setContentIntent(pi)
				.setAutoCancel(true)
				.setSound(soundUri)
				.build();
		NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(this);
		notificationManager.notify(0, notification);
	}

}