package happy.happy3;

/**
 * Created by LENOVO on 7/17/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static Typeface font;// = Typeface.createFromAsset(getActivity().getAssets(), "BNAZANIN.ttf");
    private String your_app_key = "kid_H1Fnk3aK";
    private String your_app_secret = "cd660e010c734b908d0c7720802aef5c";
    private String your_app_mastersecret = "0412a3c640df46a79352026684f1826c";
    int gettimes = 0;
    int MinuteBetweenUpdates=1;//1/60;
    int MinuteBetweenUpdatesforGraph=1;
    private Button areyouhappy;
    private Button wereyouhappy;
    private Button PersAmarDay;
    private Button sabt;
    private Button tanzimat;
    private boolean isConnected=false;
    Spinner spinner;
    public static final  int  MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101;
    public static final boolean IncludeAllGPS = true;
    private static final int POLL_INTERVAL = 1000 * 60;
    private RatingBar ratingBar;
    public static final String[] ClockNames = new String[]{"minutecut", "hourcut", "day", "month"};
    public int minutecutConstant = 2;
    public int hourcutConstant = 3;
    public static GoogleApiClient mGoogleApiClient;
    public static Location mCurrentLocation;
    public Integer WhyPosition = 0;
    static Client mKinveyClient;
    public boolean LetUploadPersonal = false;
    public boolean ImMaster =true;
    int pendingactivities=0;
    ArrayList<String> runfunc = new ArrayList<String>();
    ArrayList<String> RunTaskDOne = new ArrayList<String>(); //Run Taskdone() at the end of this function
    //static SQLiteDatabase mDatabase;
    static SQLiteDatabase mAdvDatabase;
    String[] sparray;
    Activity context1;
    private Button IranAmarDay;
    private Button WhyMe;
    private Button WhyAll;
    private Button TSThisWeekAll;
    private Button TSThisWeekMe;
    public static boolean TryConnectiontoGoogleApi=false;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    //static SQLiteDatabase mAdvDatabase;
    // static SQLiteDatabase mtempDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BNAZANIN.ttf");
       //
//		mAdvDatabase = new DatabaseHelper(getActivity().getApplicationContext()).getWritableDatabase();//Temp databse
        if(TryConnectiontoGoogleApi==false){
            TryConnectiontoGoogleApi=true;
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
     //       mGoogleApiClient.connect();

        }

        mAdvDatabase = new DatabaseHelper(getActivity().getApplicationContext()).getWritableDatabase();//Original Database
      //
        if(ImMaster){
            mKinveyClient = new Client.Builder(your_app_key,  your_app_mastersecret, getActivity()).build();
        }else{
            mKinveyClient = new Client.Builder(your_app_key, your_app_secret, getActivity()).build();
        }


        //	sparray=new String[100+1];
        //	for(int i=0;i<=100;i+=1){
        //		sparray[i]="sp"+i;
        //	}
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.mainfragment, container, false);

        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String[] tempstring1 = getResources().getStringArray(R.array.conv_array);
        List<String> tempstring2 = new ArrayList<String>(Arrays.asList(tempstring1));
        mAdapter = new CrimeAdapter(tempstring2);
        mCrimeRecyclerView.setAdapter(mAdapter);



        spinner = (Spinner) v.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WhyPosition = position;
                Object itemAtPosition = parent.getItemAtPosition(position);
                Log.i("finished", Integer.toString(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                putNewData(ratingBar.getRating());
                /*
                makeText(getActivity(),
                        R.string.sabtshod,
                        Toast.LENGTH_SHORT).show();
                        */
                // notified();
                spinner.setSelection(0);

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

        return v;
    }
    public void updateAndShowHappinessFragment() {
        makeText(getActivity(),
                R.string.darhalesabt,
                Toast.LENGTH_SHORT).show();
        RunTaskDOne.clear();
        runfunc.add("ShowHappinessFragment");

        //runfunc.add("GetWorldDataForPlotting");
        //
        runfunc.add("updatedb2");

        TaskDone();
        //TaskDone();
    }
    public void ShowHappinessFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ShowHappiness())
                .addToBackStack(null)
                .commit();
    }
    public void updatedb3() {
        if(isConnected==true) {

            Calendar cc = GregorianCalendar.getInstance();//cc.getTimeInMillis()
            long x22 = cc.getTimeInMillis();
            long LastTimeInternet = QueryPreferences.getStoredLong(getActivity(), "LastTimeInternet");
           // runfunc.add("LoginToKinvey");

            if (x22 - LastTimeInternet > 1000 * 60 * MinuteBetweenUpdates) {
                QueryPreferences.setStoredLong(getActivity(), "LastTimeInternet", x22);
                if (ImMaster) {
                  //  RunTaskDOne.add()
                    runfunc.add("UploadWorldDataToWorldKinvey");
                    runfunc.add("ProcessWorldData");
                }
                runfunc.add("GetWorldDataforProcessing");
                runfunc.add("GetWorldDataForPlotting");
            }
            if (x22 - LastTimeInternet > 1000 * 60 * MinuteBetweenUpdatesforGraph) {
                runfunc.add("UploadPersonalDataToKinvey");
                TaskDone();
            }
        }

    }
    public void updatedb2() {
        ProcessPersonalData();
        runfunc.add("updatedb3");

        isConnected=false;
        LoginToKinvey("Report");

    }
    public void ProcessPersonalData() {

        updatedb("HappyDataBaseTemp", "HappyDataBase", "HappyDataBaseSummary", true);
        Updatesummary("HappyDataBaseTemp", "HappyDataBase", "HappyDataBaseSummary");

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
                sp sp1 = new sp(getActivity(), 1);
                for (int iy = 0; iy <= (sp1.getarray(1).length - 1); iy += 1) {
                    EventEntityWhy event1 = new EventEntityWhy();
                    event1.set("whyindex", iy);
                    int temp12 = sp1.get(iy);
                    event1.set("whycount", sp1.get(iy));
                    event1.set("whypercent", sp1.getPercent(iy));
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
        //getting Why World Data
        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("WhyWorld", EventEntityWhy.class);
        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(getActivity(), 2);
                sp2.makeallzero();
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int temp1 = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                    String temp3=(String) x1.get("_acl.creator");
                    final Object ac132 = x1.get("_ac1");
                    sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
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
                    cv.put("IndexInPeriod", (Integer) x1.get("IndexInPeriod"));
                    cv.put("latitude", (Double) x1.get("latitude"));
                    cv.put("longitude", (Double) x1.get("longitude"));
                    cv.put("LastData", (Integer) x1.get("LastData"));
                    cv.put("date1", (Long) x1.get("date1"));
                    String eventId = (String) x1.get("_id");
                    mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeopleTemp", null, cv);
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
                sp sp2 = new sp(getActivity(), 2);
                sp2.makeallzero();
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int temp1 = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                   int temp3= (Integer) x1.get("whypercent");
                   // sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                    sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whypercent"));
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


        mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
        updatedb("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople","HappyDataBaseTimeSeriesAllPeopleSummary",false);
        //get Why
        //New WorldSummaryData
       // LoginToKinvey();
        final AsyncAppData<EventEntityWhy> myevents42 = mKinveyClient.appData("SummaryWorld", EventEntityWhy.class);
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


        //	Updatesummary( InputTableName,  OutputTableName, OutputSummaryTableName);
        Updatesummary("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople", "HappyDataBaseTimeSeriesAllPeopleSummary");
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
                    UploadEvent(event, "SummaryWorld");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }

        /*
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
            mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
        }
        */

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

        final AsyncAppData<EventEntityWhy> myevents43 = mKinveyClient.appData("WhyWorld", EventEntityWhy.class);
        Query query3 = mKinveyClient.query();

        myevents43.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                sp sp2 = new sp(getActivity(), 2);
                for (int iy = 0; iy <= (sp2.getarray(2).length - 1); iy += 1) {
                    EventEntityWhy event1 = new EventEntityWhy();
                    event1.set("whyindex", iy);
                    int temp12 = sp2.get(iy);
                    event1.set("whycount", sp2.get(iy));
                    event1.set("whypercent", sp2.getPercent(iy));
                    int temp3=sp2.getPercent(iy);
                    UploadEvent(event1, "WhyWorld");
                }
            }

            public void onFailure(Throwable error) {

            }
        });
TaskDone();
        //sp2.makeallzero();
    }
    public void updatedb(String InputTableName, String OutputTableName, String OutputSummaryTableName, boolean TurnToNext) {
        //boolean TurnToNext=true;
        ContentValues CurrentTimeValue = CurrentTime();
        for (int i1 = 0; i1 <= 2; i1 += 1) {
            //	Log.e("whichi", Integer.toString(i1));
            Cursor cursor1 = null;
            cursor1 = mAdvDatabase.rawQuery("SELECT * FROM " + InputTableName + " where type = " + Integer.toString(i1), null);
            Cursor cursor = new CursorWrapper(cursor1);
            cursor.moveToFirst();
            int MakeItUnTemp = 0;
            long timepassed;
            long date222;
            if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
            {
                //Object year = CurrentTimeValue.get("year");
                String q1 = "SELECT avg(rating) as rating, type , latitude , longitude  , minutecut , hourcut , WhyPosition , year , month ,  day , hour  , minute , max(date1) as date1  FROM " + InputTableName + " where type = " + Integer.toString(i1) + " GROUP BY year , month , day";
                if (i1 == 0) {//minute
                    q1 += " , hour , minutecut";
                }
                if (i1 == 1) {//minute
                    q1 += " , hourcut";
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



/*
int timepassed = (int) cursor2.getInt(cursor.getColumnIndex("date1"));
							if (i1 == 0) {
								timepassed = (int) Math.floor(timepassed / (1000 * 60 * minutecutConstant)) * minutecutConstant;
							} else if (i1 == 1) {
								timepassed = (int) Math.floor(timepassed / (1000 * 60 * 60 * hourcutConstant)) * hourcutConstant;
							} else if (i1 == 2) {
								timepassed = (int) Math.floor(timepassed / (1000 * 60 * 60 * 24));
							}
							*/
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
        if (IncludeAllGPS == true) {
            Double long1 = QueryPreferences.getStoredDouble(context1, "Longitude");
            Double lat1 = QueryPreferences.getStoredDouble(context1, "Latitude");
            values2.put("latitude", lat1);
            values2.put("longitude", long1);
            //	Log.i("lat", Double.toString(lat1));
        }


        return values2;
    }
    public void Updatesummary(String InputTableName, String OutputTableName, String OutputSummaryTableName) {
        ContentValues CurrentTimeValue = CurrentTime();
        //mAdvDatabase.delete("HappyDataBaseSummary", null, null);
        mAdvDatabase.delete(OutputSummaryTableName, null, null);
        for (int i1 = 0; i1 <= 2; i1 += 1) {
            //	Log.e(" salam", Integer.toString(i1));
            Cursor cursor1 = null;
            // cursor1 = mAdvDatabase.rawQuery("SELECT avg(rating) as rating, IndexInPeriod , type FROM HappyDataBase where istemp = 0 AND type = " + Integer.toString(i1) + " GROUP BY IndexInPeriod", null);
            if (IncludeAllGPS == true) {
                cursor1 = mAdvDatabase.rawQuery("SELECT avg(rating) as rating, IndexInPeriod , type , latitude , longitude FROM " + OutputTableName + " where type = " + Integer.toString(i1) + " GROUP BY IndexInPeriod", null);
            } else {
                cursor1 = mAdvDatabase.rawQuery("SELECT avg(rating) as rating, IndexInPeriod , type FROM " + OutputTableName + " where type = " + Integer.toString(i1) + " GROUP BY IndexInPeriod", null);
            }
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
                        InsertContent.remove("_id");
                        mAdvDatabase.insert(OutputSummaryTableName, null, InsertContent);
                        cursor.moveToNext();
                        iy += 1;
                    }
                } finally {
                    cursor.close();
                }
                int a = 2;
                //mAdvDatabase.execSQL("DELETE FROM HappyDataBase where LastData = 1 AND type ="+i1);//Last data is a temporary average but in untemp places
            }
        }
    }
    public void putNewData(float NumStars) {

        Calendar cc = GregorianCalendar.getInstance();
        ContentValues values2 = CurrentTime();
        values2.put("rating", NumStars);
        values2.put("type", -1);
        values2.put("WhyPosition", WhyPosition);
        //sp sp1=new sp(getActivity(),sparray);
        sp sp1 = new sp(getActivity(), 1);
        sp1.increment(WhyPosition);

     //   sp sp2 = new sp(getActivity(), 2);
       // sp2.increment(WhyPosition);

        mAdvDatabase.insert("HappyDataBase", null, values2); //-1 means raw. actually it may not be important at all!
        //updatedb();
        values2.put("type", 0);
        mAdvDatabase.insert("HappyDataBaseTemp", null, values2);
        //  updatedb("HappyDataBaseTemp","HappyDataBaseAlaki");
        //updatedb("HappyDataBaseTemp", "HappyDataBase");
        makeText(getActivity(),
                R.string.darhalesabt,
                Toast.LENGTH_SHORT).show();
        updatedb2();

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
        mGoogleApiClient.connect();
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
    public void TaskDone() {
        if (runfunc.size() >= 1) {

            String temp = runfunc.get(runfunc.size() - 1);
            Log.e("function  ",temp);
            runfunc.remove(runfunc.size() - 1);
            switch (temp) {
                case "GetWorldDataForPlotting":
                    GetWorldDataForPlotting();
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
                case "UploadPersonalDataToKinvey":
                    UploadPersonalDataToKinvey();
                    break;
                    default:
                        Log.e("errorrrr","no function to run  "+temp);
            }
        }else{
            makeText(getActivity(),
                    R.string.sabtshod,
                    Toast.LENGTH_SHORT).show();
        }
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
        LoginToKinvey();
        AsyncAppData<EventEntity> myevents4 = mKinveyClient.appData("SummaryWorld", EventEntity.class);
        Query query3 = mKinveyClient.query();
        myevents4.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.e("salam", "success");
            }

            public void onFailure(Throwable error) {
                Log.e("salam", "failed to delete  data"+ error);
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

    public void updatemyplacesetOnClickListener() {
      //  new MainFragment().updatemyplacesetOnClickListener();

     //   if (mGoogleApiClient.isConnected()) {
// Assume thisActivity is the current activity
       // Activity thisActivity=getActivity();
        //context1


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M){
            int permissionCheck = ContextCompat.checkSelfPermission(context1,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (ContextCompat.checkSelfPermission(context1,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(context1,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            // Do something for lollipop and above versions
        } else{
            // do something for phones running an SDK before lollipop
            updatemyplaceAfterPermission();
        }



    }


    public void updatemyplaceAfterPermission(){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(3);
        request.setInterval(0);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        // QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",location.getLatitude());
                        if (location != null) {
                            QueryPreferences.setStoredDouble(getActivity(), "Latitude", location.getLatitude());
                            QueryPreferences.setStoredDouble(getActivity(), "Longitude", location.getLongitude());
                        }
                        mCurrentLocation = location;
                    }
                });

        String strtemp;
        if (mCurrentLocation == null) {
            strtemp = "No Data. Seems Not Connected. You May need to update Google Play Services";
        } else {
            strtemp = "Location Retrieved Successfully";
        }
        makeText(getActivity(),
                strtemp,
                Toast.LENGTH_LONG).show();

        //   }
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the task you need to do.
                    updatemyplaceAfterPermission();


                } else {

                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }
    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener  {
        public TextView mTitleTextView;
        public int position1;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView;
        }
        @Override
        public void onClick(View v) {
            switch(position1){
                case 0:
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 1:
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 2:
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_chera);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
                    // QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 3:


                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_chera);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_day);
                    // QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 4:

                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(getActivity(),"FromWhen",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_day);
                    // QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                    break;
                case 5:

                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(getActivity(),"FromWhen",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_day);
                    updateAndShowHappinessFragment();


                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
            }

            /*
            PersAmarDay = (Button) v.findViewById(R.id.PersAmarDay);
            PersAmarDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                }
            });
            IranAmarDay = (Button) v.findViewById(R.id.IranAmarDay);
            IranAmarDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_amar);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                }
            });
            WhyMe = (Button) v.findViewById(R.id.Why1);
            WhyMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_chera);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
                    // QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                }
            });
            WhyAll = (Button) v.findViewById(R.id.Why2);
            WhyAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_chera);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_day);
                    // QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                }
            });

            TSThisWeekAll = (Button) v.findViewById(R.id.TSWThisWeek);
            TSThisWeekAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.kol);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_day);
                    // QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
                    updateAndShowHappinessFragment();
                }
            });

            TSThisWeekMe  = (Button) v.findViewById(R.id.TSPersThisWeek);
            TSThisWeekMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",R.id.radio_tarikhche);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
                    QueryPreferences.setStoredInt(getActivity(),"FromWhen",R.id.azyekhaftepish);
                    QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_day);
                    updateAndShowHappinessFragment();
                }
            });
            */

        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<String> mCrimes;
        public CrimeAdapter(List<String> crimes) {
            mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
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
}
