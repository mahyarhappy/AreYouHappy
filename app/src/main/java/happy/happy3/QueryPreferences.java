package happy.happy3;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Heaven on 8/7/2016.
 */
public class QueryPreferences {
	//private static final String PREF_SEARCH_QUERY = "DefaultTime";
	public static int getStoredInt(Context context, String key1) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getInt(key1, -1);
	}
	public static void setStoredInt(Context context, String key1,Integer value1) {
		PreferenceManager.getDefaultSharedPreferences(context)
				.edit()
				.putInt(key1, value1)
				.apply();
	}
	public static String getStoredString(Context context, String key1) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(key1, null);
	}
	public static void setStoredString(Context context, String key1,String value1) {
		PreferenceManager.getDefaultSharedPreferences(context)
				.edit()
				.putString(key1, value1)
				.apply();
	}
	public static Double getStoredDouble(Context context, String key1) {
		Log.v(key1,key1);
		Double x= Double.longBitsToDouble(PreferenceManager.getDefaultSharedPreferences(context).getLong(key1, Double.doubleToLongBits(-1)));
		return x;
	}
	public static void setStoredDouble(Context context, String key1,Double value1) {
		PreferenceManager
				.getDefaultSharedPreferences(context)
				.edit().putLong(key1, Double.doubleToLongBits(value1))
				.apply();
	}
	public static long getStoredLong(Context context, String key1) {
		Log.v(key1,key1);
		long x= PreferenceManager.getDefaultSharedPreferences(context).getLong(key1, -1);
		return x;
	}
	public static void setStoredLong(Context context, String key1,Long value1) {
		PreferenceManager
				.getDefaultSharedPreferences(context)
				.edit().putLong(key1, value1)
				.apply();
	}

}
