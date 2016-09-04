package happy.happy2;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Heaven on 8/23/2016.
 */
public class sp {
	//sp is for personal
	//wp is for world
	//public Integer spNum=8;
	public static String[] stringarray;
	public static Context mcontext;
	String[] sparray;
public static Integer  N1=20;
	public String[] getarray(int type){
		String temp;
		if (type==1){
			temp="sp";
		}else{
			temp="wp";
		}
		String[] sparray3=new String[N1+1];
		for(int i=0;i<=N1;i+=1){
			sparray3[i]=temp+i;
		}
		return sparray3;
	}
	public sp(Context context1, int type){
		stringarray=getarray(type);
		mcontext=context1;
	}
	public sp(Context context1, String[] stringarray2){
		stringarray=stringarray2.clone();
		mcontext=context1;
	}
	public sp(Context context1){
		mcontext=context1;
		stringarray=new String[N1+1];
		for(int i=0;i<=N1;i+=1){
			stringarray[i]="sp"+i;
		}
		//stringarray=mcontext.getResources().getStringArray(R.array.planets_array);
	}
	public static void set(int index,int value){
		QueryPreferences.setStoredInt(mcontext, stringarray[index], value);
	}
	public static int get(int index){
		//QueryPreferences.getStoredInt(mcontext, stringarray[index]);
		return PreferenceManager.getDefaultSharedPreferences(mcontext).getInt(stringarray[index],0);
	}
	public static void increment(int index){
		Integer value=QueryPreferences.getStoredInt(mcontext, stringarray[index]);
		QueryPreferences.setStoredInt(mcontext, stringarray[index], value+1);
		int valuejadid=get(index);
		int ssdfsdfdsf=0;
	}
	public static void increment(int index,int step){
		Integer value=QueryPreferences.getStoredInt(mcontext, stringarray[index]);
		QueryPreferences.setStoredInt(mcontext, stringarray[index], value+step);
	}
	public static void makeallzero(){
		for(int i=0;i<=N1;i+=1){
			set(i,0);
		}
	}
}
