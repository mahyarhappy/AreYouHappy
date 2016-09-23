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
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
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
	static Context context1;
	private static final int POLL_INTERVAL = 1000 * 60*60*3; // 60 seconds
	private static MainFragment x1;
	private boolean notify1;
private static final boolean isexact=true;
	//Context context1;
	public static Intent newIntent(Context context) {
		context1= context;
		return new Intent(context, PollService.class);
	}

	public PollService() {
		super(TAG);
	}
	@Override
	protected void onHandleIntent(Intent intent) {

context1=getApplicationContext();
		if (isexact) {
			setServiceAlarm(context1,true);
		}

		x1=new MainFragment();
		x1.context1=context1;

		x1.ForceUpload=true;
		x1.onCreateWorks();
		x1.updatedb2();
		Calendar calendar = Calendar.getInstance();
		String str= "Are You Happy Right Now? " +calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
		Log.e(TAG,str);
		i11+=1;
		notify1= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("notify", true);
		if (notify1) {
			notified(str);
		}
		PreferenceManager.getDefaultSharedPreferences(context1)
				.edit()
				.putBoolean("notify", !notify1)
				.apply();
	//	salam(context1);
	}
	public static void salam(Context context) {
		Log.e("salamService","salamService");
		context1= context;
		x1=new MainFragment();
		x1.context1=context1;
		x1.updatedb2();
	}
	public static void setServiceAlarm(Context context, boolean isOn) {
		context1=context;
		Log.e("setServiceAlarm","setServiceAlarm");
		Intent i = PollService.newIntent(context);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		AlarmManager alarmManager = (AlarmManager)
				context.getSystemService(Context.ALARM_SERVICE);
		if (isOn) {
			if(1==1){
			Calendar calendar2 = Calendar.getInstance();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar2.set(Calendar.MINUTE  ,0);
			//calendar.set(Calendar.MINUTE  ,calendar.get(Calendar.MINUTE)+1);
			calendar2.set(Calendar.SECOND  ,0);
			calendar2.set(Calendar.HOUR_OF_DAY  ,0);
			calendar.add(Calendar.SECOND  ,1);

			//finding next time
			long timeInMillis2 = calendar2.getTimeInMillis();
			long timeInMillis = calendar.getTimeInMillis();

			long dif=timeInMillis-timeInMillis2;
			Long h2= (long) (Math.ceil((double)dif/POLL_INTERVAL)*POLL_INTERVAL);
			long nexttime=h2+timeInMillis2;
			Calendar nexttimecalendar = Calendar.getInstance();
			nexttimecalendar.setTimeInMillis(nexttime);
Log.e("the time",Integer.toString(nexttimecalendar.get(Calendar.MINUTE )));
				Log.e("the time",Integer.toString(nexttimecalendar.get(Calendar.HOUR_OF_DAY )));
			if (isexact) {
				final int SDK_INT = Build.VERSION.SDK_INT;
				if (SDK_INT < Build.VERSION_CODES.KITKAT) {
					alarmManager.set(AlarmManager.RTC_WAKEUP, nexttime, pi);
				}
				else if (Build.VERSION_CODES.KITKAT <= SDK_INT  && SDK_INT < Build.VERSION_CODES.M) {
					alarmManager.setExact(AlarmManager.RTC_WAKEUP, nexttime, pi);
				}
				else if (SDK_INT >= Build.VERSION_CODES.M) {
					alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nexttime, pi);
				}

			} else {
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, nexttime,POLL_INTERVAL , pi);
			}
			/*
			calendar.set(Calendar.MINUTE  ,0);
			calendar.add(Calendar.MINUTE, 1);
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
			*/

				//this is actual alarm
			/*
					alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
						1000 * 60 * 60*3/60/3, pi);
						*/



				//long timeInMillis = calendar.getTimeInMillis() + 1000 * 60 * 60 * 2 / 60 ;


/*
				alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis()+1000 * 60 * 60*3/60/3, pi);
				*/
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