package happy.happy3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 11;
    private static final String DATABASE_NAME = "HappyDataBaseTemp.db";
    String HappyDataBaseScheme="(" +
            " _id integer primary key autoincrement, " +
            "rating REAL"+ ", " +
            "date1 INTEGER"+ ", " +
            "minute INTEGER"+ ", " +
            "hour INTEGER"+ ", " +
            "minutecut INTEGER"+ ", " +
            "hourcut INTEGER"+ ", " +
            "day INTEGER"+ ", " +
            "month INTEGER"+ ", " +
            "year INTEGER"+ ", " +
            "istemp INTEGER"+ ", " +
            "tempDone INTEGER"+ ", " +
            "xlabel INTEGER"+ ", " +
            "IndexInPeriod INTEGER"+ ", " +
            "LastData INTEGER"+ ", " +
            "latitude INTEGER"+ ", " +
            "longitude INTEGER"+ ", " +
            "isuploaded INTEGER"+ ", " +
            "WhyPosition INTEGER"+ ", " +
            "type INTEGER"+  ")";
    String HappyDataBaseSummaryScheme="(" +
            " _id integer primary key autoincrement, " +
            "rating REAL"+ ", " +
            "IndexInPeriod INTEGER"+ ", " +
            "latitude INTEGER"+ ", " +
            "longitude INTEGER"+ ", " +
            "isuploaded INTEGER"+ ", " +
            "WhyPosition INTEGER"+ ", " +
            "type INTEGER"+  ")";
    private String DATABASE_TableName_Chosen;
    private String DATABASE_File_NAME_Chosen;
    //private static final String DATABASE_NAME = "HappyDataBaseTemp.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + "HappyDataBaseTemp" + "(" +
                " _id integer primary key autoincrement, " +
                "rating REAL"+ ", " +
                "date1 INTEGER"+ ", " +
                "minute INTEGER"+ ", " +
                "hour INTEGER"+ ", " +
                "day INTEGER"+ ", " +
                "month INTEGER"+ ", " +
                "year INTEGER"+ ", " +
                "minutecut INTEGER"+ ", " +
                "hourcut INTEGER"+ ", " +
                "IndexInPeriod INTEGER"+ ", " +
                "istemp INTEGER"+ ", " +
                "tempDone INTEGER"+ ", " +
                "xlabel INTEGER"+ ", " +
                "LastData INTEGER"+ ", " +
              "latitude INTEGER"+ ", " +
             "longitude INTEGER"+ ", " +
                "isuploaded INTEGER"+ ", " +
                "WhyPosition INTEGER"+ ", " +
                "type INTEGER"+  ")"
        );
        /*
        db.execSQL("create table " + "HappyDataBaseSummary" + "(" +
                " _id integer primary key autoincrement, " +
                "rating REAL"+ ", " +
                "IndexInPeriod INTEGER"+ ", " +
               "latitude INTEGER"+ ", " +
              "longitude INTEGER"+ ", " +
                "isuploaded INTEGER"+ ", " +
                "WhyPosition INTEGER"+ ", " +
                "type INTEGER"+  ")"
        );
        */
        db.execSQL("create table " + "HappyDataBaseSummary" + HappyDataBaseSummaryScheme);
        db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeopleSummary" + HappyDataBaseSummaryScheme);
        db.execSQL("create table " + "HappyDataBase" + HappyDataBaseScheme );
        db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeopleTemp" + HappyDataBaseScheme );
        db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeople" + HappyDataBaseScheme );
        db.execSQL("create table " + "HappyDataBaseChera" + "(" +
                " _id integer primary key autoincrement, " +
                "sp INTEGER"+ ", " +
                "count INTEGER"+  ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==2 & oldVersion<=1) {
            String upgradeQuery = "ALTER TABLE HappyDataBase ADD COLUMN latitude INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseSummary ADD COLUMN latitude INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTemp ADD COLUMN latitude INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBase ADD COLUMN longitude INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseSummary ADD COLUMN longitude INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTemp ADD COLUMN longitude INTEGER";
            db.execSQL(upgradeQuery);
        }
        if(newVersion==3 & oldVersion<=2) {
            String upgradeQuery = "ALTER TABLE HappyDataBase ADD COLUMN isuploaded INTEGER";
            db.execSQL(upgradeQuery);
             upgradeQuery = "ALTER TABLE HappyDataBaseSummary ADD COLUMN isuploaded INTEGER";
            db.execSQL(upgradeQuery);
             upgradeQuery = "ALTER TABLE HappyDataBaseTemp ADD COLUMN isuploaded INTEGER";
            db.execSQL(upgradeQuery);

           // String sql="update HappyDataBase set isuploaded=0";

            db.execSQL("update HappyDataBase set isuploaded=0");
            db.execSQL("update HappyDataBaseSummary set isuploaded=0");
            db.execSQL("update HappyDataBaseTemp set isuploaded=0");
        }
        if(newVersion==4 & oldVersion<=3) {
            db.execSQL("create table " + "HappyDataBaseAlaki" + "(" +
                    " _id integer primary key autoincrement, " +
                    "rating REAL" + ", " +
                    "date1 INTEGER" + ", " +
                    "minute INTEGER" + ", " +
                    "hour INTEGER" + ", " +
                    "minutecut INTEGER" + ", " +
                    "hourcut INTEGER" + ", " +
                    "day INTEGER" + ", " +
                    "month INTEGER" + ", " +
                    "year INTEGER" + ", " +
                    "istemp INTEGER" + ", " +
                    "tempDone INTEGER" + ", " +
                    "xlabel INTEGER" + ", " +
                    "IndexInPeriod INTEGER" + ", " +
                    "LastData INTEGER" + ", " +
                    "latitude INTEGER" + ", " +
                    "longitude INTEGER" + ", " +
                    "isuploaded INTEGER" + ", " +
                    "type INTEGER" + ")"
            );

        }
        if(newVersion==6 & oldVersion<=5) {
            db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeopleTemp" + HappyDataBaseScheme );
        }
        if(newVersion==7 & oldVersion<=6) {
            db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeople" + HappyDataBaseScheme );
        }
        if(newVersion==8 & oldVersion<=8) {
            db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeople" + HappyDataBaseScheme );
        }
        if(newVersion==10 & oldVersion<=9) {
            String upgradeQuery = "ALTER TABLE HappyDataBase ADD COLUMN WhyPosition INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseSummary ADD COLUMN WhyPosition INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTemp ADD COLUMN WhyPosition INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTimeSeriesAllPeopleTemp ADD COLUMN WhyPosition INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTimeSeriesAllPeople ADD COLUMN WhyPosition INTEGER";
            db.execSQL(upgradeQuery);

            // String sql="update HappyDataBase set isuploaded=0";

            db.execSQL("update HappyDataBase set WhyPosition=0");
            db.execSQL("update HappyDataBaseSummary set WhyPosition=0");
            db.execSQL("update HappyDataBaseTemp set WhyPosition=0");
            db.execSQL("update HappyDataBaseTimeSeriesAllPeopleTemp set WhyPosition=0");
            db.execSQL("update HappyDataBaseTimeSeriesAllPeople set WhyPosition=0");
        }
        if(newVersion==11 & oldVersion<=10) {
            db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeopleSummary" + HappyDataBaseSummaryScheme);
        }
    }


}

/*

	public class DownloadFromKenvey2 extends AsyncTask<Void, Void, Boolean> {
		// Flag for login flow
		private boolean flag ;
		@Override
		protected Boolean doInBackground(Void... params) {
			//EventEntityTimeSeries events = new EventEntityTimeSeries();
			final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorldTemp", EventEntityTimeSeries.class);
			//The EventEntity class is defined above
			//AsyncAppData<EventEntity> myevents = mKinveyClient.appData("events", EventEntity.class);
			myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
				@Override
				public void onSuccess(EventEntityTimeSeries[] result) {
					Log.v("TAG", "received "+ result.length + " events");
					mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleTemp", null, null);
					ContentValues cv=new ContentValues() ;
					for (EventEntityTimeSeries x1 : result){
						cv.put("rating",(Double) x1.get("rating"));
						cv.put("minute",(Integer) x1.get("minute"));
						cv.put("minutecut",(Integer) x1.get("minutecut"));
						cv.put("hour",(Integer) x1.get("hour"));
						cv.put("hourcut",(Integer) x1.get("hourcut"));
						cv.put("day",(Integer) x1.get("day"));
						cv.put("month",(Integer) x1.get("month"));
						cv.put("year",(Integer) x1.get("year"));
						cv.put("type",(Integer) x1.get("type"));
						cv.put("IndexInPeriod",(Integer) x1.get("IndexInPeriod"));
						cv.put("latitude",(Double) x1.get("latitude"));
						cv.put("longitude",(Double) x1.get("longitude"));
						cv.put("LastData",(Integer) x1.get("LastData"));
						String eventId =(String) x1.get("_id");
						Log.i("cv",cv.toString());
						mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeopleTemp", null, cv);
					}
					flag=true;
//					DownloadFromKenvey3 task = new DownloadFromKenvey3();
//					task.execute();

				}
				@Override
				public void onFailure(Throwable error)
				{
					Log.e("TAG", "failed to fetch all", error);
					flag=false;
				}
		});
			//return flag;
			return true;
		}
		@Override
		protected void onPostExecute(final Boolean success) {

			if ( flag==true) {
			//	DownloadFromKenvey3 task = new DownloadFromKenvey3();
			//	task.execute();
			} else {
			}
		}
		@Override
		protected void onCancelled() {
		}
	}


	public class DownloadFromKenvey3 extends AsyncTask<Void, Void, Boolean> {
		// Flag for login flow
		private boolean flag ;
		@Override
		protected Boolean doInBackground(Void... params) {
			final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorldTemp", EventEntityTimeSeries.class);
			Query query3 = mKinveyClient.query();
			myevents.delete(query3, new KinveyDeleteCallback() {
				@Override
				public void onSuccess(KinveyDeleteResponse response) {
					Log.v("TAG", "deleted successfully");
					flag=true;
					mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
					updatedb("HappyDataBaseTimeSeriesAllPeopleTemp","HappyDataBaseTimeSeriesAllPeople");
		//			UploadToKenvey();


				}
				public void onFailure(Throwable error) {
					Log.e("TAG", "failed to delete ", error);
					flag=false;
				}
			});



			return flag;
		}
		@Override
		protected void onPostExecute(final Boolean success) {

			if (success &flag) {

				mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
				updatedb("HappyDataBaseTimeSeriesAllPeopleTemp","HappyDataBaseTimeSeriesAllPeople");
				UploadToKenvey();
			} else {
			}
		}
		@Override
		protected void onCancelled() {
		}
	}




		}

 */

