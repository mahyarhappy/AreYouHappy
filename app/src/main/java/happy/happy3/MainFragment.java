package happy.happy3;

/**
 * Created by LENOVO on 7/17/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.network.connectionclass.ConnectionQuality;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
//import com.kinvey.java.model.KinveyMetaData;
import com.kinvey.android.callback.KinveyDeleteCallback;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.model.KinveyDeleteResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final int DIALOG_FRAGMENT = 121;
    public static Typeface font;
    private String your_app_key = "kid_H1Fnk3aK";
    private String your_app_secret = "cd660e010c734b908d0c7720802aef5c";
    private String your_app_mastersecret = "0412a3c640df46a79352026684f1826c";

public static int ostan;
    public int minutecutConstant = 15;
    public int hourcutConstant = 3;
    static int DownloadMinute ;//1/60;
    static int UploadMinute ;
    public static boolean WorkOffline;
    public static boolean ImMaster;
    int gettimes = 0;

    private Button areyouhappy;
    private Button wereyouhappy;
    private Button PersAmarDay;
    private Button sabt;
    private Button tanzimat;
    private boolean isConnected=false;

    private ProgressDialog dialog;

    public static final  int  MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101;

    private static final int POLL_INTERVAL = 1000 * 60;
    private RatingBar ratingBar;
    public static final String[] ClockNames = new String[]{"minutecut", "hourcut", "day", "month"};


    public static Integer WhyPosition ;
    static Client mKinveyClient;
    public boolean LetUploadPersonal = false;

    int pendingactivities=0;
    ArrayList<String> runfunc = new ArrayList<String>();
    ArrayList<String> RunTaskDOne = new ArrayList<String>(); //Run Taskdone() at the end of this function
    static SQLiteDatabase mAdvDatabase;
    //Activity context1;
    Context context1;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean ismNetworkAvailable;

    private static final String TAG = "ConnectionClass-Sample";
    private String mURL = "http://connectionclass.parseapp.com/m100_hubble_4060.jpg";
    private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;
    private static boolean justsabt;
    private boolean FirstStartOfProgram;
public String[] engwhy={"dalilnarahatiyakhoshhali","khanevade","doostan","salamati",
        "varzeshi","tvgheirevarzesh","shoghlodaramad","khastegi","hichkodam","eshghi"};
    public boolean ForceUpload;
    private Button UpdateDataButton;
    private TextView tozih;

    //static SQLiteDatabase mAdvDatabase;
    // static SQLiteDatabase mtempDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //	sparray=new String[100+1];
        //	for(int i=0;i<=100;i+=1){
        //		sparray[i]="sp"+i;
        //	}



    }
    public void onCreateWorks(){
        ImMaster= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("ImMaster", false);
        PreferenceManager.getDefaultSharedPreferences(context1)
                .edit()
                .putBoolean("WorkOffline", false)
                .apply();



        WorkOffline= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("WorkOffline", false);
        ForceUpload= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("ForceUpload", false);
        ostan=PreferenceManager.getDefaultSharedPreferences(context1)
                .getInt("ostan", -1);
        font = Typeface.createFromAsset(context1.getAssets(), "fonts/BNAZANIN.ttf");
        //
//		mAdvDatabase = new DatabaseHelper(context1.getApplicationContext()).getWritableDatabase();//Temp databse

        UploadMinute=PreferenceManager.getDefaultSharedPreferences(context1)
                .getInt("UploadMinute2", 60*7);
        DownloadMinute=PreferenceManager.getDefaultSharedPreferences(context1)
                .getInt("DownloadMinute2", 60*12);

        mAdvDatabase = new DatabaseHelper(context1.getApplicationContext()).getWritableDatabase();//Original Database
        //
        //if(ImMaster){
        if(1==0){
            mKinveyClient = new Client.Builder(your_app_key,  your_app_mastersecret, context1).build();
        }else{
            mKinveyClient = new Client.Builder(your_app_key, your_app_secret, context1).build();
        }
        mKinveyClient.setRequestTimeout(2000);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.mainfragment, container, false);
        context1=getContext();
        onCreateWorks();
        //WorkOffline= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("WorkOffline", false);

        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(context1));
        String[] tempstring1 = getResources().getStringArray(R.array.conv_array);
        List<String> tempstring2 = new ArrayList<String>(Arrays.asList(tempstring1));
        mAdapter = new CrimeAdapter(tempstring2);
        mCrimeRecyclerView.setAdapter(mAdapter);
/*
        SwitchCompat WorkOfflineSwitch = (SwitchCompat) v.findViewById(R.id.WorkOffline2);


        WorkOfflineSwitch.setChecked(WorkOffline);
        WorkOfflineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               WorkOffline=isChecked;
                PreferenceManager.getDefaultSharedPreferences(context1)
                        .edit()
                        .putBoolean("WorkOffline", isChecked)
                        .apply();
            }
        });
*/

         UpdateDataButton = (Button) v.findViewById(R.id.justtest);

    UpdateDataButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ForceUpload=true;
           // new UpdateAsync().execute();
           PollService.setServiceAlarm(context1,true);
        }

    });


        wereyouhappy = (Button) v.findViewById(R.id.washappy);
        wereyouhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAndShowHappinessFragment();
            }
        });

        sabt = (Button) v.findViewById(R.id.sabt);
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        sabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                cheradialogclass editNameDialogFragment = new cheradialogclass();
                editNameDialogFragment.setTargetFragment(fm2.findFragmentById(R.id.fragment_container),DIALOG_FRAGMENT);
                editNameDialogFragment.show(fm2, "cheradialog");
            }
        });
        tanzimat = (Button) v.findViewById(R.id.tanzimat);

        tanzimat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new settingFragment())
                        .addToBackStack(null)
                      //  .commitAllowingStateLoss();
                        .commit();

            }
        });

        wereyouhappy.setTypeface(font);
        sabt.setTypeface(font);

         tozih=(TextView) v.findViewById(R.id.tozih);
        tozih.setTypeface(font);

        LayerDrawable stars = (LayerDrawable) ratingBar
                .getProgressDrawable();
      //  DrawableCompat.setTint(ratingBar.getProgressDrawable(),color);
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context1,R.color.colorAccent),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(context1,R.color.colorAccentVeryVeryLow),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context1,R.color.colorAccentVeryVeryLow),
                PorterDuff.Mode.SRC_ATOP); // for filled stars


        FirstStartOfProgram= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("FirstStartOfProgram", true);
if(FirstStartOfProgram){
    FirstStartOfProgram=false;
    PreferenceManager.getDefaultSharedPreferences(context1)
            .edit()
            .putBoolean("FirstStartOfProgram", false)
            .apply();


    getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, new chooseostanfragment())
            .addToBackStack(null)
            .commit();
    PollService.setServiceAlarm(context1,true);
}
        boolean SetServiceOnetime= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("SetServiceOnetime3", true);
        if(SetServiceOnetime){
            SetServiceOnetime=false;
            PreferenceManager.getDefaultSharedPreferences(context1)
                    .edit()
                    .putBoolean("SetServiceOnetime3", false)
                    .apply();
            PollService.setServiceAlarm(context1,true);
        }

        return v;
    }
    public void updateAndShowHappinessFragment() {
        ForceUpload= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("ForceUpload", false);
        RunTaskDOne.clear();
        runfunc.add("ShowHappinessFragment");
        justsabt=false;
        //runfunc.add("GetWorldDataForPlotting");
        //
        //UpdateAsyncInstance= (UpdateAsync) new UpdateAsync().execute();
        UpdateAsyncConditional();

     //   UpdateAsyncInstance.execute();
        //runfunc.add("updatedb2");

        //TaskDone();
        //TaskDone();
    }

    private void UpdateAsyncConditional() {
        Calendar cc = GregorianCalendar.getInstance();//cc.getTimeInMillis()
        long x22 = cc.getTimeInMillis();
        long LastTimeInternet = QueryPreferences.getStoredLong(context1, "LastTimeInternet");
        // runfunc.add("LoginToKinvey");
        if(ForceUpload || x22 - LastTimeInternet > 1000 * 60 * UploadMinute ) {
            new UpdateAsync().execute();
        }else{
            TaskDone();
        }
    }

    public void ShowHappinessFragment() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ShowHappiness())
                .addToBackStack(null)
                .commit();
    }
    public void updatedb3() {
        runfunc.add("updatedb4");
        //isNetworkAvailable(true);
        new NetworkUtil2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //new NetworkUtil2().execute(mURL);
    }
    public void updatedb4() {

        if(isConnected==true &&  ismNetworkAvailable) {
            Calendar cc = GregorianCalendar.getInstance();//cc.getTimeInMillis()
            long x22 = cc.getTimeInMillis();
            long LastTimeInternet = QueryPreferences.getStoredLong(context1, "LastTimeInternet");
           // runfunc.add("LoginToKinvey");

            if ((x22 - LastTimeInternet > 1000 * 60 * DownloadMinute && justsabt==false)||ForceUpload) {
                QueryPreferences.setStoredLong(context1, "LastTimeInternet", x22);
                if (ImMaster) {
                  //  RunTaskDOne.add()
                    runfunc.add("UploadWorldDataToWorldKinvey");
                    runfunc.add("ProcessWorldData");
                    runfunc.add("GetWorldDataforProcessing");
                }
                runfunc.add("GetWorldDataForPlotting");
            }
            if (x22 - LastTimeInternet > 1000 * 60 * UploadMinute || ForceUpload) {
                runfunc.add("UploadPersonalDataToKinvey");
            }
        }
        TaskDone();
    }
    public void updatedb2() {
        updatedb2(false);
    }
    public void updatedb2(boolean OnlyUpload) {
        if(!OnlyUpload) {
            ProcessPersonalData();
        }
        runfunc.add("updatedb3");
        isConnected=false;
        LoginToKinvey("Report");

    }
    public class UpdateAsync extends AsyncTask<String, Void, String> {

String dialogstring;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context1);


           String darhalesabt1= getResources().getString(R.string.darhalesabt);
            dialog.setMessage(darhalesabt1);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {
            updatedb2();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            /*
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            makeText(getActivity(),
                    R.string.sabtshod,
                    Toast.LENGTH_SHORT).show();

            TaskDone();
            */
        }

        public void dialogmessage(String temp) {
            dialogstring=temp;
            publishProgress();
        }
        @Override
        protected void onProgressUpdate(Void... v) {
            super.onProgressUpdate(v);
            if(dialog!=null) {
                if (dialog.isShowing()) {
                    dialog.setMessage(dialogstring);
                }
            }
        }

    }
    public void ProcessPersonalData() {

        updatedb("HappyDataBaseTemp", "HappyDataBase", "man");
        Updatesummary( "HappyDataBase", "HappyDataBaseSummary","man");

    }
    public void UploadPersonalDataToKinvey() {
        pendingactivities=0;
        //LoginToKinvey();
        Cursor cursor1 ;
        Cursor cursor ;

        if (LetUploadPersonal) {
            final AsyncAppData<EventEntity> myevents4 = mKinveyClient.appData("SummaryPersonal", EventEntity.class);
            Query query3 = mKinveyClient.query();
            myevents4.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {
                    Log.v("TAG", "deleted successfully");
                    mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleSummary", null, null);
                    pendingactivities-=1;
                    TaskDone();
                }

                public void onFailure(Throwable error) {
                    Log.e("TAG", "failed to delete ", error);

                    TaskDone();
                }
            });
             cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseSummary", null);
             cursor = new CursorWrapper(cursor1);
            cursor.moveToFirst();
            if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
            {
                try {
                    while (!cursor.isAfterLast()) {
                        double rating22 = cursor.getDouble(cursor.getColumnIndex("rating"));
                        Integer type22 = cursor.getInt(cursor.getColumnIndex("type"));
                        Integer IndexInPeriod22 = cursor.getInt(cursor.getColumnIndex("IndexInPeriod"));
                        EventEntity event = new EventEntity();
                        event.set("rating", rating22);
                        event.set("type", type22);
                        event.set("IndexInPeriod", IndexInPeriod22);
                        event.set("latitude", cursor.getDouble(cursor.getColumnIndex("latitude")));
                        event.set("longitude", cursor.getDouble(cursor.getColumnIndex("longitude")));
                        event.set("ostan", cursor.getInt(cursor.getColumnIndex("ostan")));
                        UploadEvent(event, "SummaryPersonal");
                        //UploadEvent(event, "WorldSummaryTemp");
                        cursor.moveToNext();
                    }
                } finally {
                    cursor.close();
                }
            }
        }


        String quer1 = "isuploaded = 0 OR isuploaded IS NULL";
        cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where " + quer1, null);
        cursor = new CursorWrapper(cursor1);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
        {
            try {
                while (!cursor.isAfterLast()) {
                    double rating22 = cursor.getDouble(cursor.getColumnIndex("rating"));
                    Integer type22 = cursor.getInt(cursor.getColumnIndex("type"));
                    Integer IndexInPeriod22 = cursor.getInt(cursor.getColumnIndex("IndexInPeriod"));
                    EventEntityTimeSeries event1 = new EventEntityTimeSeries();
                    event1.set("rating", cursor.getDouble(cursor.getColumnIndex("rating")));
                    event1.set("minute", cursor.getInt(cursor.getColumnIndex("minute")));
                    event1.set("minutecut", cursor.getInt(cursor.getColumnIndex("minutecut")));
                    event1.set("hour", cursor.getInt(cursor.getColumnIndex("hour")));
                    event1.set("hourcut", cursor.getInt(cursor.getColumnIndex("hourcut")));
                    event1.set("day", cursor.getInt(cursor.getColumnIndex("day")));
                    event1.set("month", cursor.getInt(cursor.getColumnIndex("month")));
                    event1.set("year", cursor.getInt(cursor.getColumnIndex("year")));
                    event1.set("type", cursor.getInt(cursor.getColumnIndex("type")));
                    event1.set("IndexInPeriod", cursor.getInt(cursor.getColumnIndex("IndexInPeriod")));
                    event1.set("latitude", cursor.getDouble(cursor.getColumnIndex("latitude")));
                    event1.set("longitude", cursor.getDouble(cursor.getColumnIndex("longitude")));
                    event1.set("date1", cursor.getLong(cursor.getColumnIndex("date1")));
                    event1.set("xlabel", cursor.getLong(cursor.getColumnIndex("xlabel")));
                    Integer lastdata1 = cursor.getInt(cursor.getColumnIndex("LastData"));
                    Integer WhyPosition1 = cursor.getInt(cursor.getColumnIndex("WhyPosition"));
                    event1.set("WhyPosition", cursor.getInt(cursor.getColumnIndex("WhyPosition")));
                    event1.set("ostan", cursor.getInt(cursor.getColumnIndex("ostan")));
                    event1.set("LastData", lastdata1);
                    if (LetUploadPersonal) {
                        UploadEvent(event1, "TSPersonal");
                    }
//                    if (lastdata1 == 1) {
                    if (lastdata1 == 0) {
                        UploadEvent(event1, "TSWorldRawArchive");
                    }
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
        }

        if (LetUploadPersonal) {
            final AsyncAppData<EventEntity> myevents40 = mKinveyClient.appData("WhyPersonal", EventEntity.class);

            Query query3 = mKinveyClient.query();
            myevents40.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {
                    Log.v("TAG", "deleted successfully");
                    //	mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleSummary", null, null);
                }

                public void onFailure(Throwable error) {
                    Log.e("TAG", "failed to delete ", error);
                }
            });


        }
        //delete Why Data Related to this user in WhyWorldRaw Database
        final AsyncAppData<EventEntity> myevents40 = mKinveyClient.appData("WhyWorldRaw", EventEntity.class);
        Query query = mKinveyClient.query();

        query.equals("_acl.creator", mKinveyClient.user().getId());
       // query.equals("_id", mKinveyClient.user().getId());
String sasdasd=mKinveyClient.user().getId();

        myevents40.delete(query, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
                sp sp1 = new sp(context1, 1);
               // Log.e("spcount",Integer.toString(engwhy.length));
                for (int iy = 0; iy <= (sp1.getarray(1).length - 1); iy += 1) {
                  //  Log.e("spcount",Integer.toString(iy));
                    EventEntityWhy event1 = new EventEntityWhy();
                    event1.set("whyindex", iy);
                    int temp12 = sp1.get(iy);
                    event1.set("whycount", sp1.get(iy));
                    event1.set("whypercent", sp1.getPercent(iy));
                    if (engwhy.length - 1 >= iy) {
                        event1.set("whystring", engwhy[iy]);
                    }

                    if(temp12>0) {
                        if (LetUploadPersonal) {
                            UploadEvent(event1, "WhyPersonal");
                        }
                        UploadEvent(event1, "WhyWorldRaw");
                    }
                }

                TaskDone();
            }

            public void onFailure(Throwable error)
            {
                Log.e("TAG", "failed to delete ", error);
                TaskDone();
            }
        });

        //sp2.makeallzero();

    }
    public void GetWorldDataForPlotting() {
        runfunc.add("GetWorldDataForPlotting2");
        //getting TimesSeries World Data
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorld", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                    Log.v("TAG", "received " + result.length + " events");
                    mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
                    ContentValues cv = new ContentValues();
                    for (EventEntityTimeSeries x1 : result) {
                        cv.put("rating", (Double) x1.get("rating"));
                        cv.put("minute", (Integer) x1.get("minute"));
                        cv.put("minutecut", (Integer) x1.get("minutecut"));
                        cv.put("hour", (Integer) x1.get("hour"));
                        cv.put("hourcut", (Integer) x1.get("hourcut"));
                        cv.put("day", (Integer) x1.get("day"));
                        cv.put("month", (Integer) x1.get("month"));
                        cv.put("year", (Integer) x1.get("year"));
                        cv.put("type", (Integer) x1.get("type"));
                        cv.put("IndexInPeriod", (Integer) x1.get("IndexInPeriod"));
                        cv.put("latitude", (Double) x1.get("latitude"));
                        cv.put("longitude", (Double) x1.get("longitude"));
                        cv.put("LastData", (Integer) x1.get("LastData"));
                        cv.put("ostan", (Integer) x1.get("ostan"));
                        cv.put("date1", (Long) x1.get("date1"));
                        cv.put("xlabel", (Long) x1.get("xlabel"));
                        String eventId = (String) x1.get("_id");
                        mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeople", null, cv);
                    }

                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }


        });

    }
    public void GetWorldDataForPlotting2() {
        runfunc.add("GetWorldDataForPlotting3");
        //getting Why World Data
        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("WhyWorld", EventEntityWhy.class);
        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(context1, 2);
                sp2.makeallzero();
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int index = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                    String temp3=(String) x1.get("_acl.creator");
                    final Object ac132 = x1.get("_ac1");
                    String st1=(String) x1.get("whystring");
                  //  int index = -1;
                    for (int i=0;i<engwhy.length;i++) {
                        if (engwhy[i].equals(st1)) {
                            index = i;
                            break;
                        }
                    }
                    int yuiyui=0;
                    if ((Integer) x1.get("whycount") > 0) {
                         yuiyui=0;
                    }
                    yuiyui=1;
                  //  sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                    sp2.increment(index, (Integer) x1.get("whycount"));
                }
                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }
        });
    }
    public void GetWorldDataForPlotting3(){
        runfunc.add("GetWorldDataForPlotting4");
        final AsyncAppData<EventEntity > myevents = mKinveyClient.appData("SummaryWorld", EventEntity .class);
        myevents.get(new KinveyListCallback<EventEntity >() {
            @Override
            public void onSuccess(EventEntity [] result) {
                Log.v("TAG", "received " + result.length + " events");
                mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleSummary", null, null);
                ContentValues cv = new ContentValues();
                for (EventEntity x1 : result) {
                    cv.put("rating", (Double) x1.get("rating"));
                    cv.put("type", (Integer) x1.get("type"));
                    cv.put("IndexInPeriod", (Integer) x1.get("IndexInPeriod"));
                    cv.put("latitude", (Double) x1.get("latitude"));
                    cv.put("longitude", (Double) x1.get("longitude"));
                    cv.put("ostan", (Integer) x1.get("ostan"));
                    mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeopleSummary", null, cv);
                }

                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }


        });

    }
    public void GetWorldDataForPlotting4(){
        runfunc.add("GetWorldDataForPlotting5");
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorldOstan", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                Log.v("TAG", "received " + result.length + " events");
                mAdvDatabase.delete("HappyDataBaseTimeSeriesOstanAllPeople", null, null);
                ContentValues cv = new ContentValues();
                for (EventEntityTimeSeries x1 : result) {
                    cv.put("rating", (Double) x1.get("rating"));
                    cv.put("minute", (Integer) x1.get("minute"));
                    cv.put("minutecut", (Integer) x1.get("minutecut"));
                    cv.put("hour", (Integer) x1.get("hour"));
                    cv.put("hourcut", (Integer) x1.get("hourcut"));
                    cv.put("day", (Integer) x1.get("day"));
                    cv.put("month", (Integer) x1.get("month"));
                    cv.put("year", (Integer) x1.get("year"));
                    cv.put("type", (Integer) x1.get("type"));
                    cv.put("IndexInPeriod", (Integer) x1.get("IndexInPeriod"));
                    cv.put("latitude", (Double) x1.get("latitude"));
                    cv.put("longitude", (Double) x1.get("longitude"));
                    cv.put("LastData", (Integer) x1.get("LastData"));
                    cv.put("date1", (Long) x1.get("date1"));
                    cv.put("ostan", (Integer) x1.get("ostan"));
                    cv.put("xlabel", (Long) x1.get("xlabel"));
                    String eventId = (String) x1.get("_id");
                    mAdvDatabase.insert("HappyDataBaseTimeSeriesOstanAllPeople", null, cv);
                }

                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }


        });
    }
    public void GetWorldDataForPlotting5(){
        final AsyncAppData<EventEntity > myevents = mKinveyClient.appData("SummaryWorldOstan", EventEntity .class);
        myevents.get(new KinveyListCallback<EventEntity >() {
            @Override
            public void onSuccess(EventEntity [] result) {
                Log.v("TAG", "received " + result.length + " events");
                mAdvDatabase.delete("HappyDataBaseTimeSeriesOstanAllPeopleSummary", null, null);
                ContentValues cv = new ContentValues();
                for (EventEntity x1 : result) {
                    cv.put("rating", (Double) x1.get("rating"));
                    cv.put("type", (Integer) x1.get("type"));
                    cv.put("IndexInPeriod", (Integer) x1.get("IndexInPeriod"));
                    cv.put("latitude", (Double) x1.get("latitude"));
                    cv.put("longitude", (Double) x1.get("longitude"));
                    cv.put("ostan", (Integer) x1.get("ostan"));
                    mAdvDatabase.insert("HappyDataBaseTimeSeriesOstanAllPeopleSummary", null, cv);
                }

                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }


        });

    }
    public void GetWorldDataforProcessing() {
      //  LoginToKinvey();
        runfunc.add("GetWorldDataforProcessing2");
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorldRawArchive", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                Log.v("TAG", "received " + result.length + " events");
                mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleTemp", null, null);
                ContentValues cv = new ContentValues();
                for (EventEntityTimeSeries x1 : result) {
                    cv.put("rating", (Double) x1.get("rating"));
                    cv.put("minute", (Integer) x1.get("minute"));
                    cv.put("minutecut", (Integer) x1.get("minutecut"));
                    cv.put("hour", (Integer) x1.get("hour"));
                    cv.put("hourcut", (Integer) x1.get("hourcut"));
                    cv.put("day", (Integer) x1.get("day"));
                    cv.put("month", (Integer) x1.get("month"));
                    cv.put("year", (Integer) x1.get("year"));
                    cv.put("type", (Integer) x1.get("type"));
                    cv.put("ostan", (Integer) x1.get("ostan"));
                    cv.put("IndexInPeriod", (Integer) x1.get("IndexInPeriod"));
                    cv.put("latitude", (Double) x1.get("latitude"));
                    cv.put("longitude", (Double) x1.get("longitude"));
                    cv.put("LastData", (Integer) x1.get("LastData"));
                    cv.put("date1", (Long) x1.get("date1"));
                    String eventId = (String) x1.get("_id");
                    mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeopleTemp", null, cv);
                    mAdvDatabase.insert("HappyDataBaseTimeSeriesOstanAllPeopleTemp", null, cv);
                }
                /*
                Query query3 = mKinveyClient.query();
                myevents.delete(query3, new KinveyDeleteCallback() {
                    @Override
                    public void onSuccess(KinveyDeleteResponse response) {
                    public void onSuccess(KinveyDeleteResponse response) {
                        Log.v("TAG", "deleted successfully");
                    }

                    public void onFailure(Throwable error) {
                        Log.e("TAG", "failed to delete ", error);
                    }
                });
                */

                TaskDone();
            }
            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }
        });

    }
    public void GetWorldDataforProcessing2() {
        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("WhyWorldRaw", EventEntityWhy.class);
        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(context1, 2);
                sp2.makeallzero();
                //sp sp1=new sp(context1,1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int index = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                   int temp3= (Integer) x1.get("whypercent");
                    String st1=(String) x1.get("whystring");
                    for (int i=0;i<engwhy.length;i++) {
                        if (engwhy[i].equals(st1)) {
                            index = i;
                            break;
                        }
                    }

                   // sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                  //  sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whypercent"));
                    sp2.increment(index, (Integer) x1.get("whypercent"));
                }
                /*
                Query query3 = mKinveyClient.query();
                myevents4.delete(query3, new KinveyDeleteCallback() {
                    @Override
                    public void onSuccess(KinveyDeleteResponse response) {
                        Log.v("TAG", "deleted successfully");
                    }

                    public void onFailure(Throwable error) {
                        Log.e("TAG", "failed to delete ", error);
                    }
                });
                */
                TaskDone();
                //UploadToKenvey();
            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }
        });
    }
    public void ProcessWorldData() {
        //get and process TSWorldRawArchive
        int aaasdsad=0;


        final AsyncAppData<EventEntityTimeSeries> myevents41 = mKinveyClient.appData("TSWorld", EventEntityTimeSeries.class);
        Query query3 = mKinveyClient.query();
        myevents41.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
            }

            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to delete ", error);
            }
        });

        final AsyncAppData<EventEntityTimeSeries> myevents42 = mKinveyClient.appData("SummaryWorldOstan", EventEntityTimeSeries.class);
         query3 = mKinveyClient.query();
        myevents42.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
            }

            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to delete ", error);
            }
        });


        final AsyncAppData<EventEntityTimeSeries> myevents43 = mKinveyClient.appData("TSWorldOstan", EventEntityTimeSeries.class);
         query3 = mKinveyClient.query();
        myevents43.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
            }

            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to delete ", error);
            }
        });


        mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
        mAdvDatabase.delete("HappyDataBaseTimeSeriesOstanAllPeople", null, null);
        mAdvDatabase.delete("HappyDataBaseTimeSeriesOstanAllPeopleSummary", null, null);
        updatedb("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople","kol");
        updatedb("HappyDataBaseTimeSeriesOstanAllPeopleTemp", "HappyDataBaseTimeSeriesOstanAllPeople","ostan");
        //get Why
        //New WorldSummaryData
       // LoginToKinvey();
        final AsyncAppData<EventEntityWhy> myevents46 = mKinveyClient.appData("SummaryWorld", EventEntityWhy.class);
         query3 = mKinveyClient.query();
        myevents46.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
            }

            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to delete ", error);
            }
        });


        //	Updatesummary( InputTableName,  OutputTableName, OutputSummaryTableName);
        Updatesummary("HappyDataBaseTimeSeriesAllPeople", "HappyDataBaseTimeSeriesAllPeopleSummary","kol");
        Updatesummary("HappyDataBaseTimeSeriesOstanAllPeople", "HappyDataBaseTimeSeriesOstanAllPeopleSummary","ostan");
TaskDone();
    }
    public void UploadWorldDataToWorldKinvey() {
        //Uploading WorldSummary
        Cursor cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesAllPeopleSummary", null);
        Cursor cursor = new CursorWrapper(cursor1);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
        {
            try {
                while (!cursor.isAfterLast()) {
                    double rating22 = cursor.getDouble(cursor.getColumnIndex("rating"));
                    Integer type22 = cursor.getInt(cursor.getColumnIndex("type"));
                    Integer IndexInPeriod22 = cursor.getInt(cursor.getColumnIndex("IndexInPeriod"));
                    EventEntity event = new EventEntity();
                    event.set("rating", rating22);
                    event.set("type", type22);
                    event.set("IndexInPeriod", IndexInPeriod22);
                    event.set("latitude", cursor.getDouble(cursor.getColumnIndex("latitude")));
                    event.set("longitude", cursor.getDouble(cursor.getColumnIndex("longitude")));
                    event.set("ostan", cursor.getInt(cursor.getColumnIndex("ostan")));
                    UploadEvent(event, "SummaryWorld");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }

        cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesAllPeople", null);
        cursor = new CursorWrapper(cursor1);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
        {
            try {
                while (!cursor.isAfterLast()) {
                    double rating22 = cursor.getDouble(cursor.getColumnIndex("rating"));
                    EventEntityTimeSeries event1 = new EventEntityTimeSeries();
                    event1.set("rating", cursor.getDouble(cursor.getColumnIndex("rating")));
                    event1.set("minute", cursor.getInt(cursor.getColumnIndex("minute")));
                    event1.set("minutecut", cursor.getInt(cursor.getColumnIndex("minutecut")));
                    event1.set("hour", cursor.getInt(cursor.getColumnIndex("hour")));
                    event1.set("hourcut", cursor.getInt(cursor.getColumnIndex("hourcut")));
                    event1.set("day", cursor.getInt(cursor.getColumnIndex("day")));
                    event1.set("month", cursor.getInt(cursor.getColumnIndex("month")));
                    event1.set("year", cursor.getInt(cursor.getColumnIndex("year")));
                    event1.set("type", cursor.getInt(cursor.getColumnIndex("type")));
                    event1.set("IndexInPeriod", cursor.getInt(cursor.getColumnIndex("IndexInPeriod")));
                    event1.set("latitude", cursor.getDouble(cursor.getColumnIndex("latitude")));
                    event1.set("longitude", cursor.getDouble(cursor.getColumnIndex("longitude")));
                    event1.set("LastData", cursor.getInt(cursor.getColumnIndex("LastData")));
                    event1.set("WhyPosition", cursor.getInt(cursor.getColumnIndex("WhyPosition")));
                    event1.set("date1", cursor.getLong(cursor.getColumnIndex("date1")));
                    event1.set("xlabel", cursor.getLong(cursor.getColumnIndex("xlabel")));
                    event1.set("ostan", cursor.getInt(cursor.getColumnIndex("ostan")));
                    //UploadEvent(event1, "TimeSeries");
                    UploadEvent(event1, "TSWorld");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            //   cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)", null);
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)");
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
            //mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);

          //  mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
        }


        //ppppppppppppppppppppppppppppppppppppppppppppppppp



         cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesOstanAllPeopleSummary", null);
         cursor = new CursorWrapper(cursor1);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
        {
            try {
                while (!cursor.isAfterLast()) {
                    double rating22 = cursor.getDouble(cursor.getColumnIndex("rating"));
                    Integer type22 = cursor.getInt(cursor.getColumnIndex("type"));
                    Integer IndexInPeriod22 = cursor.getInt(cursor.getColumnIndex("IndexInPeriod"));
                    EventEntity event8 = new EventEntity();
                    event8.set("rating", rating22);
                    event8.set("type", type22);
                    event8.set("IndexInPeriod", IndexInPeriod22);
                    event8.set("latitude", cursor.getDouble(cursor.getColumnIndex("latitude")));
                    event8.set("longitude", cursor.getDouble(cursor.getColumnIndex("longitude")));
                    event8.set("ostan", cursor.getInt(cursor.getColumnIndex("ostan")));
                    UploadEvent(event8, "SummaryWorldOstan");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }

        cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesOstanAllPeople", null);
        cursor = new CursorWrapper(cursor1);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
        {
            try {
                while (!cursor.isAfterLast()) {
                    double rating22 = cursor.getDouble(cursor.getColumnIndex("rating"));
                    EventEntityTimeSeries event1 = new EventEntityTimeSeries();
                    event1.set("rating", cursor.getDouble(cursor.getColumnIndex("rating")));
                    event1.set("minute", cursor.getInt(cursor.getColumnIndex("minute")));
                    event1.set("minutecut", cursor.getInt(cursor.getColumnIndex("minutecut")));
                    event1.set("hour", cursor.getInt(cursor.getColumnIndex("hour")));
                    event1.set("hourcut", cursor.getInt(cursor.getColumnIndex("hourcut")));
                    event1.set("day", cursor.getInt(cursor.getColumnIndex("day")));
                    event1.set("month", cursor.getInt(cursor.getColumnIndex("month")));
                    event1.set("year", cursor.getInt(cursor.getColumnIndex("year")));
                    event1.set("type", cursor.getInt(cursor.getColumnIndex("type")));
                    event1.set("IndexInPeriod", cursor.getInt(cursor.getColumnIndex("IndexInPeriod")));
                    event1.set("latitude", cursor.getDouble(cursor.getColumnIndex("latitude")));
                    event1.set("longitude", cursor.getDouble(cursor.getColumnIndex("longitude")));
                    event1.set("LastData", cursor.getInt(cursor.getColumnIndex("LastData")));
                    event1.set("WhyPosition", cursor.getInt(cursor.getColumnIndex("WhyPosition")));
                    event1.set("date1", cursor.getLong(cursor.getColumnIndex("date1")));
                    event1.set("xlabel", cursor.getLong(cursor.getColumnIndex("xlabel")));
                    event1.set("ostan", cursor.getInt(cursor.getColumnIndex("ostan")));
                    //UploadEvent(event1, "TimeSeries");
                    UploadEvent(event1, "TSWorldOstan");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            //   cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)", null);
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)");
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
            //mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);

            //  mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
        }














//ppppppppppppppppppppppppppppp
        final AsyncAppData<EventEntityWhy> myevents43 = mKinveyClient.appData("WhyWorld", EventEntityWhy.class);
        Query query3 = mKinveyClient.query();

        myevents43.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                sp sp2 = new sp(context1, 2);
                for (int iy = 0; iy <= (sp2.getarray(2).length - 1); iy += 1) {
                    EventEntityWhy event1 = new EventEntityWhy();
                    event1.set("whyindex", iy);
                    int temp12 = sp2.get(iy);
                    event1.set("whycount", sp2.get(iy));
                    event1.set("whypercent", sp2.getPercent(iy));
                    int temp3=sp2.getPercent(iy);
                    if (engwhy.length - 1 >= iy) {
                        event1.set("whystring", engwhy[iy]);
                    }

                    UploadEvent(event1, "WhyWorld");
                }
            }

            public void onFailure(Throwable error) {

            }
        });
TaskDone();
        //sp2.makeallzero();
    }
    public void updatedb(String InputTableName, String OutputTableName,String Who1) {
       boolean TurnToNext;
        if (Who1.equals("man")) {
            TurnToNext=true;
        }else{
            TurnToNext=false;
        }
        //boolean TurnToNext=true;
        ContentValues CurrentTimeValue = CurrentTime();
        for (int i1 = 0; i1 <= 2; i1 += 1) {
            	Log.e("whichi", Integer.toString(i1));
            Cursor cursor1 = null;
            Log.e("whichi", "SELECT * FROM " + InputTableName + " where type = " + Integer.toString(i1));
            cursor1 = mAdvDatabase.rawQuery("SELECT * FROM " + InputTableName + " where type = " + Integer.toString(i1), null);
            Cursor cursor = new CursorWrapper(cursor1);
            cursor.moveToFirst();
            int MakeItUnTemp = 0;
            long timepassed;
            long date222;
            if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
            {
                //Object year = CurrentTimeValue.get("year");
                String q1 = "SELECT avg(rating) as rating, type , latitude , longitude  , minutecut , hourcut , WhyPosition , ostan , year , month ,  day , hour  , minute , max(date1) as date1  FROM " + InputTableName + " where type = " + Integer.toString(i1) + " GROUP BY year , month , day";
                if (i1 == 0) {//minute
                    q1 += " , hour , minutecut";
                }
                if (i1 == 1) {//minute
                    q1 += " , hourcut";
                }
                if (Who1.equals("ostan")){
                    q1 += " , ostan";
                }
                Cursor cursor2 = null;
                cursor2 = mAdvDatabase.rawQuery(q1, null);
                if (!cursor2.isAfterLast() && cursor2.getCount() > 0)//just to see if data is nonemoty. it does not loop in cursor here
                {

                    int iy = 0;
                    cursor2.moveToFirst();
                    //Object year = CurrentTimeValue.get("year");
                    try {
                        while (!cursor2.isAfterLast()) {
                            ContentValues InsertContent = new ContentValues();
                            DatabaseUtils.cursorRowToContentValues(cursor2, InsertContent);
                            InsertContent.put("IndexInPeriod", InsertContent.getAsInteger(ClockNames[i1]));
                            InsertContent.remove("_id");

                      //      date222 = cursor2.getLong(cursor.getColumnIndex("date1"));
                          date222=InsertContent.getAsLong("date1");
                            timepassed=-2;
                            if (i1 == 0) {
                                timepassed = (long) Math.floor(date222 / (1000 * 60 * minutecutConstant)) * minutecutConstant;
                            } else if (i1 == 1) {
                                timepassed = (long) Math.floor(date222 / (1000 * 60 * 60 * hourcutConstant)) * hourcutConstant;
                            } else if (i1 == 2) {
                                timepassed = (long) Math.floor(date222 / (1000 * 60 * 60 * 24));
                            }

                            int ykjhkjh;
                            if(InputTableName.equals("HappyDataBaseTimeSeriesAllPeopleTemp")){
                                Integer yeartemp3 = cursor2.getInt(cursor2.getColumnIndex("year"));

                                ykjhkjh=0;
                                int s=ykjhkjh;

                            }
                            InsertContent.put("xlabel",timepassed );
                            //InsertContent.put("date1", date222);
                            Integer yeartemp = cursor2.getInt(cursor2.getColumnIndex("year"));
                            Integer monthtemp = cursor2.getInt(cursor2.getColumnIndex("month"));
                            Integer daytemp = cursor2.getInt(cursor2.getColumnIndex("day"));
                            Integer hourcuttemp = cursor2.getInt(cursor2.getColumnIndex("hourcut"));
                            Integer minutecuttemp = cursor2.getInt(cursor2.getColumnIndex("minutecut"));

                            boolean year1 = CurrentTimeValue.getAsInteger("year") > yeartemp;
                            boolean month1 = CurrentTimeValue.getAsInteger("month") > monthtemp;
                            boolean day1 = CurrentTimeValue.getAsInteger("day") > daytemp;
                            boolean hour1 = CurrentTimeValue.getAsInteger("hourcut") > hourcuttemp;
                            boolean minute1 = CurrentTimeValue.getAsInteger("minutecut") > minutecuttemp;

                            String q2 = " AND year = " + yeartemp + " AND month = " + monthtemp + " AND day = " + daytemp;
                            if (i1 == 0) {//minute
                                q2 += " AND hourcut = " + hourcuttemp + " AND minutecut = " + minutecuttemp;
                            }
                            if (i1 == 1) {//minute
                                q2 += " AND hourcut = " + hourcuttemp;
                            }

                            mAdvDatabase.execSQL("DELETE FROM " + OutputTableName + " where LastData = 1 AND type = " + Integer.toString(i1) + q2);//Last data is a temporary average but in untemp places
                            int final1 = 0;
                            if (year1 || month1 || day1 || (hour1 && i1 < 2) || (minute1 && i1 < 1)) {
                                final1 = 1;
                            }
                            int lastdata1 = 1 - final1;
                            InsertContent.put("LastData", lastdata1);
                            mAdvDatabase.insert(OutputTableName, null, InsertContent);
                            if (TurnToNext) {
                                mAdvDatabase.execSQL("DELETE FROM " + InputTableName + " where LastData = 1 AND type = " + Integer.toString(i1 + 1) + q2);
                                if (1 == 0) {
                                    InsertContent.put("istemp", 1);
                                }
                                InsertContent.put("LastData", lastdata1);

                                InsertContent.put("type", i1 + 1);
                                if (1 == 0) {
                                    mAdvDatabase.insert(OutputTableName, null, InsertContent);//in khat be nazaram bayad hazf beshe
                                }
                                if (i1 < 2) {
                                    // cursor2.close();
                                    mAdvDatabase.insert(InputTableName, null, InsertContent);
                                }
                            }
                            if (final1 == 1) {
                                mAdvDatabase.execSQL("DELETE FROM " + InputTableName + " where type =" + i1 + q2);
                            }
                            //     InsertContent.put("type", i1);
                            //  ContentValues InsertContent3=new ContentValues(InsertContent);
                            cursor2.moveToNext();
                        }
                    } finally {
                        cursor2.close();
                    }
                    int a = 2;


                }


                MakeItUnTemp = 1;
                //    cursor.moveToFirst();

            }
        }
        //Updatesummary( InputTableName,  OutputTableName, OutputSummaryTableName);
    }
    public ContentValues CurrentTime() {
        ContentValues values2 = new ContentValues();
        Calendar cc = GregorianCalendar.getInstance();//cc.getTimeInMillis()
        //values2.put("date1", cc.getTimeInMillis());
        values2.put("minute", cc.get(GregorianCalendar.MINUTE));
        values2.put("hour", cc.get(GregorianCalendar.HOUR_OF_DAY));
        values2.put("day", cc.get(GregorianCalendar.DAY_OF_MONTH));
        values2.put("month", cc.get(GregorianCalendar.MONTH));
        values2.put("year", cc.get(GregorianCalendar.YEAR));
        long x22 = cc.getTimeInMillis();
        values2.put("date1", cc.getTimeInMillis());
        //values2.put("date2", (long) Math.floor(cc.getTimeInMillis()/1000));
        values2.put("minutecut", Math.floor(cc.get(GregorianCalendar.MINUTE) / minutecutConstant) * minutecutConstant);
        values2.put("hourcut", Math.floor(cc.get(GregorianCalendar.HOUR_OF_DAY) / hourcutConstant) * hourcutConstant);
        //Double long1= QueryPreferences.getStoredDouble(getContext(), "Longitude");
        /*
        if (IncludeAllGPS == true) {
            Double long1 = QueryPreferences.getStoredDouble(context1, "Longitude");
            Double lat1 = QueryPreferences.getStoredDouble(context1, "Latitude");
            values2.put("latitude", lat1);
            values2.put("longitude", long1);
            //	Log.i("lat", Double.toString(lat1));
        }
*/

        return values2;
    }
    public void Updatesummary( String OutputTableName, String OutputSummaryTableName,String Who1) {


        ContentValues CurrentTimeValue = CurrentTime();
        //mAdvDatabase.delete("HappyDataBaseSummary", null, null);
        mAdvDatabase.delete(OutputSummaryTableName, null, null);
        for (int i1 = 0; i1 <= 3; i1 += 1) {
            	//Log.e(" salam", Integer.toString(i1));
            Cursor cursor1 = null;
            String q1;
            // cursor1 = mAdvDatabase.rawQuery("SELECT avg(rating) as rating, IndexInPeriod , type FROM HappyDataBase where istemp = 0 AND type = " + Integer.toString(i1) + " GROUP BY IndexInPeriod", null);

            //    q1="SELECT avg(rating) as rating, IndexInPeriod , type , ostan  , latitude , longitude FROM " + OutputTableName ;

                q1="SELECT avg(rating) as rating, IndexInPeriod , type , ostan  FROM " + OutputTableName;

            if(i1<3){
                q1+= " where type = " + Integer.toString(i1);
                q1+= " GROUP BY IndexInPeriod";

                if (Who1.equals("ostan")) {
                    q1+=" , ostan";
                }
            }else{

                if (Who1.equals("ostan")) {
                    q1+= " GROUP BY ostan";
                }

            }
            int t=0;
if(i1==3 &&Who1.equals("kol")){
   t=0;
}
            t=1;
            cursor1 = mAdvDatabase.rawQuery(q1, null);
            Cursor cursor = new CursorWrapper(cursor1);
            cursor.moveToFirst();
            int MakeItUnTemp = 0;
            //Log.e("salam", Arrays.toString(cursor.getColumnNames()));
            //Log.e("salam", "Number Of Rows: " + Integer.toString(cursor.getCount()));
            if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
            {
                int iy = 0;
                //Object year = CurrentTimeValue.get("year");
                try {
                    while (!cursor.isAfterLast()) {
                        String a1 = Float.toString(cursor.getInt(cursor.getColumnIndex("IndexInPeriod")));
                        String a2 = Float.toString(cursor.getInt(cursor.getColumnIndex("rating")));
                        //	Log.e(" salam", "rating:" + a2 + "type:" + i1 + "IndexInPeriod:" + a1);
                        ContentValues InsertContent = new ContentValues();
                        DatabaseUtils.cursorRowToContentValues(cursor, InsertContent);
                        if(i1==3){
                            InsertContent.put("type",3);
                        }
                        if (Who1.equals("ostan")) {
                      //      InsertContent.put("ostan",-1);
                        }
                        InsertContent.remove("_id");
                        mAdvDatabase.insert(OutputSummaryTableName, null, InsertContent);
                        cursor.moveToNext();
                        iy += 1;
                    }
                } finally {
                    cursor.close();
                }
                int a = 2;
            }
        }
    }
    public void putNewData(float NumStars) {
        ForceUpload= PreferenceManager.getDefaultSharedPreferences(context1).getBoolean("ForceUpload", false);
        Calendar cc = GregorianCalendar.getInstance();
        ContentValues values2 = CurrentTime();
        values2.put("rating", NumStars);
        values2.put("type", -1);
        values2.put("WhyPosition", WhyPosition);
        values2.put("ostan", ostan);
        sp sp1 = new sp(context1, 1);
        sp1.increment(WhyPosition);
        mAdvDatabase.insert("HappyDataBase", null, values2); //-1 means raw. actually it may not be important at all!
        values2.put("type", 0);
        mAdvDatabase.insert("HappyDataBaseTemp", null, values2);
        justsabt=true;

        UpdateAsyncConditional();


    }
    public void UploadEvent(EventEntityTimeSeries event, String CollectionName) {
      //  LoginToKinvey();
        AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData(CollectionName, EventEntityTimeSeries.class);
        myevents.save(event, new KinveyClientCallback<EventEntityTimeSeries>() {
            @Override
            public void onFailure(Throwable e) {
                Log.e("salam", "failed to save event data", e);
            }

            @Override
            public void onSuccess(EventEntityTimeSeries r) {
                Log.d("salam", "saved data for entity");
            }
        });
    }
    public void UploadEvent(EventEntity event, String CollectionName) {
      //  LoginToKinvey();
      //  new KinveyMetaData.AccessControlList.AclGroups().

        AsyncAppData<EventEntity> myevents = mKinveyClient.appData(CollectionName, EventEntity.class);
        myevents.save(event, new KinveyClientCallback<EventEntity>() {
            @Override
            public void onFailure(Throwable e) {
                Log.e("salam", "failed to save event data", e);
            }

            @Override
            public void onSuccess(EventEntity r) {
                Log.d("salam", "saved data for entity");
            }
        });
    }
    public void UploadEvent(EventEntityWhy event, String CollectionName) {
   //     LoginToKinvey();

        AsyncAppData<EventEntityWhy> myevents = mKinveyClient.appData(CollectionName, EventEntityWhy.class);
        myevents.save(event, new KinveyClientCallback<EventEntityWhy>() {
            @Override
            public void onFailure(Throwable e) {
                Log.e("salam", "failed to save event data", e);
            }

            @Override
            public void onSuccess(EventEntityWhy r) {
                Log.d("salam", "saved data for entity");
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // getActivity().invalidateOptionsMenu();
    }
    @Override
    public void onStop() {
        super.onStop();
    //   mGoogleApiClient.disconnect();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            context1 = (Activity) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //   context1=null;
    }
    public void LoginToKinvey(String Report) {
        int DoNotReport = 1;
        if (Report.equals("DoNotReport")) {
            DoNotReport = 1;
        } else {
            DoNotReport = 0;
        }
        if (!mKinveyClient.user().isUserLoggedIn()) {
            final int finalDoNotReport = DoNotReport;
            mKinveyClient.user().login(new KinveyUserCallback() {
                @Override
                public void onFailure(Throwable error) {
                    Log.e("salam", "Login Failure", error);
                    isConnected=false;
                    if (finalDoNotReport == 0) {
                        TaskDone();
                    }
                }

                @Override
                public void onSuccess(User result) {
                    isConnected=true;
                    Log.i("salam", "Logged in a new implicit user with id: ");
                    if (finalDoNotReport == 0) {
                        TaskDone();
                    }
                }
            });
        }else{
            if (DoNotReport == 0) {
                isConnected=true;
                TaskDone();
            }
        }

    }
    public void LoginToKinvey() {
        LoginToKinvey("DoNotReport");
    }


    public long convert1(long date222,int i1){
       long timepassed=-2;
        if (i1 == 0) {
            timepassed = (long) Math.floor(date222 / (1000 * 60 * minutecutConstant)) * minutecutConstant;
        } else if (i1 == 1) {
            timepassed = (long) Math.floor(date222 / (1000 * 60 * 60 * hourcutConstant)) * hourcutConstant;
        } else if (i1 == 2) {
            timepassed = (long) Math.floor(date222 / (1000 * 60 * 60 * 24));
        }
        return timepassed;

    }
    public void DeleteEverythingMethod(){
        mAdvDatabase.delete("HappyDataBase", null, null);
        mAdvDatabase.delete("HappyDataBaseTemp", null, null);
        mAdvDatabase.delete("HappyDataBaseSummary", null, null);
        mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleSummary", null, null);
        mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleTemp", null, null);
        mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
        mAdvDatabase.delete("HappyDataBaseChera", null, null);
        if(ImMaster==true) {
            LoginToKinvey();
            AsyncAppData<EventEntity> myevents4 = mKinveyClient.appData("SummaryWorld", EventEntity.class);
            Query query3 = mKinveyClient.query();
            myevents4.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {
                    Log.e("salam", "success");
                }

                public void onFailure(Throwable error) {
                    Log.e("salam", "failed to delete  data" + error);
                }
            });
            AsyncAppData<EventEntityTimeSeries> myevents41 = mKinveyClient.appData("TSWorld", EventEntityTimeSeries.class);
            query3 = mKinveyClient.query();
            myevents41.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {

                }

                public void onFailure(Throwable error) {

                }
            });

            final AsyncAppData<EventEntityTimeSeries> myevents42 = mKinveyClient.appData("TSWorldRawArchive", EventEntityTimeSeries.class);
            query3 = mKinveyClient.query();
            myevents42.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {

                }

                public void onFailure(Throwable error) {

                }
            });
            final AsyncAppData<EventEntityWhy> myevents43 = mKinveyClient.appData("WhyWorld", EventEntityWhy.class);
            query3 = mKinveyClient.query();
            myevents43.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {

                }

                public void onFailure(Throwable error) {

                }
            });

            final AsyncAppData<EventEntityWhy> myevents44 = mKinveyClient.appData("WhyWorldRaw", EventEntityWhy.class);
            query3 = mKinveyClient.query();
            myevents44.delete(query3, new KinveyDeleteCallback() {
                @Override
                public void onSuccess(KinveyDeleteResponse response) {

                }

                public void onFailure(Throwable error) {

                }
            });
        }

    }





    @Override
    public void onConnected(Bundle bundle) {
       // updatemyplacesetOnClickListener();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener  {
        public TextView mTitleTextView;
        public int position1;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
           // mTitleTextView = (TextView) itemView;
            mTitleTextView=(TextView) itemView.findViewById(R.id.listtextview);
        }
        @Override
        public void onClick(View v) {
            switch(position1){
                case 3:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 4:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 5:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_chera);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.man);
                    // QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 6:


                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_chera);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_day);
                    // QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 7:

                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(context1,"FromWhen",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_day);
                    // QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 8:

                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(context1,"FromWhen",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_day);
                    updateAndShowHappinessFragment();


                    break;
                case 9:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.region);
                    QueryPreferences.setStoredInt(context1,"FromWhen",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_day);
                    updateAndShowHappinessFragment();


                    break;
                case 0:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_min);
                    updateAndShowHappinessFragment();
                    break;

                case 1:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_min);
                    updateAndShowHappinessFragment();
                    break;

                case 2:
                    QueryPreferences.setStoredInt(context1,"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(context1,"DefaultWho",R.id.region);
                    QueryPreferences.setStoredInt(context1,"DefaultTime",R.id.radio_min);
                    updateAndShowHappinessFragment();
                    break;

            }



        }
    }
    public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<String> mCrimes;
        public CrimeAdapter(List<String> crimes) {
            mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context1);
            /*
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
                    */
            View view = layoutInflater
                    .inflate(R.layout.listtext, parent, false);
            return new CrimeHolder(view);
        }
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            String crime = mCrimes.get(position);
            holder.position1=position;
            holder.mTitleTextView.setText(crime);
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


    public class NetworkUtil2 extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {
            Log.e("S","S");
            isNetworkAvailable2(false);
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            /*
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            */
            TaskDone();
        }
    }




    public  void isNetworkAvailable2( boolean InvokeTaskDone) {
        if(WorkOffline==false) {
            final boolean InvokeTaskDone2 = InvokeTaskDone;
            try {
             //   Log.e("lop", "sdfsdfsdf");
                String url1 = "http://stackoverflow.com/questions/6493517/detect-if-android-device-has-internet-connection";
                url1 = "http://devcenter.kinvey.com/images/logo.png";
                System.setProperty("http.keepAlive", "false");
                HttpURLConnection urlc = (HttpURLConnection) (new URL(url1).openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);

                urlc.connect();
                ismNetworkAvailable = (urlc.getResponseCode() == 200);
                if (InvokeTaskDone) {
                    TaskDone();
                }
                //   return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("lop", "Error checking internet connection", e);
                ismNetworkAvailable = false;
                if (InvokeTaskDone) {
                    TaskDone();
                }
            }
        }else{
            ismNetworkAvailable = false;
            if (InvokeTaskDone) {
                TaskDone();
            }
        }
    }





















    public void TaskDone() {
         String[] t={"new"};
        if (runfunc.size() >= 1) {

            String temp = runfunc.get(runfunc.size() - 1);
            Log.e("function  ",temp);
            runfunc.remove(runfunc.size() - 1);
           // dialog.setMessage(temp);
            new UpdateAsync().dialogmessage(temp);
            switch (temp) {
                case "GetWorldDataForPlotting":
                    GetWorldDataForPlotting();
                    //String darhalesabt1= getResources().getString(R.string.darhalesabt);

                    break;
                case "GetWorldDataForPlotting2":
                    GetWorldDataForPlotting2();
                    break;
                case "GetWorldDataforProcessing":
                    GetWorldDataforProcessing();
                    break;
                case "GetWorldDataforProcessing2":
                    GetWorldDataforProcessing2();
                    break;
                case "GetWorldDataForPlotting3":
                    GetWorldDataForPlotting3();
                    break;
                case "DownloadFromKenveyForPlotting2":
                    //     DownloadFromKenveyForPlotting2();
                    break;
                case "UploadWorldDataToWorldKinvey":
                    UploadWorldDataToWorldKinvey();
                    break;
                case "ShowHappinessFragment":
                    ShowHappinessFragment();
                    break;
                case "ProcessWorldData":
                    ProcessWorldData();
                    break;
                case "updatedb2":
                    updatedb2();
                    break;
                case "updatedb3":
                    updatedb3();
                    break;
                case "updatedb4":
                    updatedb4();
                    break;
                case "GetWorldDataForPlotting4":
                    GetWorldDataForPlotting4();
                    break;
                case "GetWorldDataForPlotting5":
                    GetWorldDataForPlotting5();
                break;
                case "UploadPersonalDataToKinvey":
                UploadPersonalDataToKinvey();
                break;
                default:
                    Log.e("errorrrr","no function to run  "+temp);
            }
        }else{
            Log.e("End","End");
            if(dialog!=null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }
    }

/*
    @Override
    public void onDismiss(final DialogInterface dialog) {
        //Fragment dialog had been dismissed
        putNewData(ratingBar.getRating());
    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DIALOG_FRAGMENT:

                if (resultCode == Activity.RESULT_OK) {
                    putNewData(ratingBar.getRating());
                    // After Ok code.
                } else if (resultCode == Activity.RESULT_CANCELED){
                    // After Cancel code.
                }

                break;
        }
    }

}
