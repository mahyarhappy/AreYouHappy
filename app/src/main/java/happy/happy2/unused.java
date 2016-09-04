package happy.happy2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;

/**
 * Created by Heaven on 8/24/2016.
 */
public class unused {
/*
	public void Insert2db(ContentValues values1) {
		//mDatabase.insert("HappyDataBase", null, values1);
	}

	public void updatedb() {
		ContentValues CurrentTimeValue = CurrentTime();
		for (int i1 = 0; i1 <= 2; i1 += 1) {
			Cursor cursor1 = null;
			cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTemp where type = " + Integer.toString(i1), null);
			Cursor cursor = new CursorWrapper(cursor1);
			cursor.moveToFirst();
			int MakeItUnTemp = 0;
			if (!cursor.isAfterLast())//just to see if data is nonemoty. it does not loop in cursor here
			{
				//Object year = CurrentTimeValue.get("year");
				boolean year1 = CurrentTimeValue.getAsInteger("year") > cursor.getInt(cursor.getColumnIndex("year"));
				boolean month1 = CurrentTimeValue.getAsInteger("month") > cursor.getInt(cursor.getColumnIndex("month"));
				boolean day1 = CurrentTimeValue.getAsInteger("day") > cursor.getInt(cursor.getColumnIndex("day"));
				boolean hour1 = CurrentTimeValue.getAsInteger("hourcut") > cursor.getInt(cursor.getColumnIndex("hourcut"));
				boolean minute1 = CurrentTimeValue.getAsInteger("minutecut") > cursor.getInt(cursor.getColumnIndex("minutecut"));
				ContentValues InsertContent = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(cursor, InsertContent);
				InsertContent.remove("_id");
				InsertContent.put("IndexInPeriod", InsertContent.getAsInteger(ClockNames[i1]));
				MakeItUnTemp = 1;
				cursor.moveToFirst();
				int timepassed = (int) cursor.getInt(cursor.getColumnIndex("date1"));
				float allsum = 0;
				int allcount = 0;
				try {
					while (!cursor.isAfterLast()) {
						allsum += cursor.getDouble(cursor.getColumnIndex("rating")) + 0.0;
						allcount += 1.0;
						cursor.moveToNext();
					}
				} finally {
					cursor.close();
				}
				float ytre = (float) allsum / allcount;
				InsertContent.put("rating", (float) allsum / allcount);
				//InsertContent.put("istemp", 0);
				InsertContent.put("type", i1);

				if (i1 == 0) {
					timepassed = (int) Math.floor(timepassed / (1000 * 60 * minutecutConstant)) * minutecutConstant;
				} else if (i1 == 1) {
					timepassed = (int) Math.floor(timepassed / (1000 * 60 * 60 * hourcutConstant)) * hourcutConstant;
				} else if (i1 == 2) {
					timepassed = (int) Math.floor(timepassed / (1000 * 60 * 60 * 24));
				}
				InsertContent.put("xlabel", timepassed);
				//  ContentValues InsertContent3=new ContentValues(InsertContent);

				mAdvDatabase.execSQL("DELETE FROM HappyDataBase where LastData = 1 AND type =" + i1);//Last data is a temporary average but in untemp places

				int final1 = 0;
				if (year1 || month1 || day1 || (hour1 && i1 < 2) || (minute1 && i1 < 1)) {
					final1 = 1;
				}
				int lastdata1 = 1 - final1;
				InsertContent.put("LastData", lastdata1);
				mAdvDatabase.insert("HappyDataBase", null, InsertContent);
				mAdvDatabase.execSQL("DELETE FROM HappyDataBaseTemp where LastData = 1 AND type =" + Integer.toString(i1 + 1));
				InsertContent.put("istemp", 1);
				InsertContent.put("LastData", lastdata1);
				InsertContent.put("type", i1 + 1);
				if (i1 < 2) {
					cursor.close();
					mAdvDatabase.insert("HappyDataBaseTemp", null, InsertContent);
				}
				if (final1 == 1) {
					mAdvDatabase.execSQL("DELETE FROM HappyDataBaseTemp where type =" + i1);
				}
			}
		}
		Updatesummary();
		UploadToKenvey();
	}
	public void UploadToKenvey() {
        LoginToKinvey();
        runfunc.add("UploadToKenvey3");
        final AsyncAppData<EventEntity> myevents4 = mKinveyClient.appData("SummaryPersonal", EventEntity.class);
        Query query3 = mKinveyClient.query();
        myevents4.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
                mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleSummary", null, null);
                TaskDone();
            }

            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to delete ", error);
                TaskDone();
            }
        });

    }
    public void UploadToKenvey3() {

        //Cursor cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseSummary where isuploaded = 0 OR isuploaded IS NULL", null);
        Cursor cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseSummary", null);
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
                    UploadEvent(event, "SummaryPersonal");
                    cursor.moveToNext();

                }
            } finally {
                cursor.close();
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
                    Integer lastdata1 = cursor.getInt(cursor.getColumnIndex("LastData"));
                    Integer WhyPosition1 = cursor.getInt(cursor.getColumnIndex("WhyPosition"));
                    event1.set("WhyPosition", cursor.getInt(cursor.getColumnIndex("WhyPosition")));
                    event1.set("LastData", lastdata1);
                    UploadEvent(event1, "TSPersonal");
                    if (lastdata1 == 1) {
                        UploadEvent(event1, "TSWorldTemp");
                    }

                    cursor.moveToNext();

                }
            } finally {
                cursor.close();
            }
            //   cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)", null);
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)");
            mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
        }

        cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesAllPeopleTemp", null);
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
                    UploadEvent(event1, "TSWorldTemp");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            //   cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)", null);
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)");
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
            //mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
            mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleTemp", null, null);
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


        sp sp2 = new sp(getActivity(), 2);

        for (int iy = 0; iy <= (sp2.getarray(2).length - 1); iy += 1) {
            EventEntityWhy event1 = new EventEntityWhy();
            event1.set("whyindex", iy);
            int temp12 = sp2.get(iy);
            event1.set("whycount", sp2.get(iy));
            UploadEvent(event1, "Why");
        }
        sp2.makeallzero();

    }
    public void DownloadFromKenveyForPlotting() {
        runfunc.add("DownloadFromKenveyForPlotting2");
        LoginToKinvey();
        //EventEntityTimeSeries events = new EventEntityTimeSeries();
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorld", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                if (1 == 1) {
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
                }
                LoginToKinvey();
                final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("SummaryWorld", EventEntityWhy.class);
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
                //	Updatesummary( InputTableName,  OutputTableName, OutputSummaryTableName);
                Updatesummary("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople", "HappyDataBaseTimeSeriesAllPeopleSummary");
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
                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }


        });


    }
    public void DownloadFromKenveyForPlotting2() {


        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("Why", EventEntityWhy.class);
        //The EventEntity class is defined above
        //AsyncAppData<EventEntity> myevents = mKinveyClient.appData("events", EventEntity.class);

        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(getActivity(), 2);
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int temp1 = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                    sp2.set((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                }
                Log.e("TAG", "Everything Done");
                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                Log.e("TAG", "Everything Done");
                TaskDone();
            }


        });


    }
    public void DownloadFromKenvey3() {
        //	runfunc.add("UploadToKenvey");
        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("Why", EventEntityWhy.class);
        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(getActivity(), 2);
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int temp1 = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                    sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                }
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
                //TaskDone();
                //UploadToKenvey();

            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                //TaskDone();
            }


        });
        UploadToKenvey();
    }
    public void DownloadFromKenvey() {
        LoginToKinvey();
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorldTemp", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                gettimes += 1;
                if (1 == 1) {
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
                        String eventId = (String) x1.get("_id");
                        mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeopleTemp", null, cv);
                    }
                    Query query3 = mKinveyClient.query();
                    myevents.delete(query3, new KinveyDeleteCallback() {
                        @Override
                        public void onSuccess(KinveyDeleteResponse response) {
                            Log.v("TAG", "deleted successfully");
                        }

                        public void onFailure(Throwable error) {
                            Log.e("TAG", "failed to delete ", error);
                        }
                    });

                    mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);

                    //	TaskDone();
                    //	updatedb("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople","HappyDataBaseTimeSeriesAllPeopleSummary");
                }
            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                //	TaskDone();
            }


        });
        DownloadFromKenvey3();
    }
public void DownloadFromKenvey() {
        LoginToKinvey();
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorldTemp", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                gettimes += 1;
                if (1 == 1) {
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
                        String eventId = (String) x1.get("_id");
                        mAdvDatabase.insert("HappyDataBaseTimeSeriesAllPeopleTemp", null, cv);
                    }
                    Query query3 = mKinveyClient.query();
                    myevents.delete(query3, new KinveyDeleteCallback() {
                        @Override
                        public void onSuccess(KinveyDeleteResponse response) {
                            Log.v("TAG", "deleted successfully");
                        }

                        public void onFailure(Throwable error) {
                            Log.e("TAG", "failed to delete ", error);
                        }
                    });

                    mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);

                    //	TaskDone();
                    //	updatedb("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople","HappyDataBaseTimeSeriesAllPeopleSummary");
                }
            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                //	TaskDone();
            }


        });
        DownloadFromKenvey3();
    }
    public void DownloadFromKenveyForPlotting2() {


        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("Why", EventEntityWhy.class);
        //The EventEntity class is defined above
        //AsyncAppData<EventEntity> myevents = mKinveyClient.appData("events", EventEntity.class);

        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(getActivity(), 2);
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int temp1 = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                    sp2.set((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                }
                Log.e("TAG", "Everything Done");
                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                Log.e("TAG", "Everything Done");
                TaskDone();
            }


        });


    }
    public void DownloadFromKenveyForPlotting() {
        runfunc.add("DownloadFromKenveyForPlotting2");
        LoginToKinvey();
        //EventEntityTimeSeries events = new EventEntityTimeSeries();
        final AsyncAppData<EventEntityTimeSeries> myevents = mKinveyClient.appData("TSWorld", EventEntityTimeSeries.class);
        myevents.get(new KinveyListCallback<EventEntityTimeSeries>() {
            @Override
            public void onSuccess(EventEntityTimeSeries[] result) {
                if (1 == 1) {
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
                }
                LoginToKinvey();
                final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("SummaryWorld", EventEntityWhy.class);
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
                //	Updatesummary( InputTableName,  OutputTableName, OutputSummaryTableName);
                Updatesummary("HappyDataBaseTimeSeriesAllPeopleTemp", "HappyDataBaseTimeSeriesAllPeople", "HappyDataBaseTimeSeriesAllPeopleSummary");
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
                TaskDone();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to fetch all", error);
                TaskDone();
            }


        });


    }

    public void UploadToKenvey() {
        LoginToKinvey();
        runfunc.add("UploadToKenvey3");
        final AsyncAppData<EventEntity> myevents4 = mKinveyClient.appData("SummaryPersonal", EventEntity.class);
        Query query3 = mKinveyClient.query();
        myevents4.delete(query3, new KinveyDeleteCallback() {
            @Override
            public void onSuccess(KinveyDeleteResponse response) {
                Log.v("TAG", "deleted successfully");
                mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleSummary", null, null);
                TaskDone();
            }

            public void onFailure(Throwable error) {
                Log.e("TAG", "failed to delete ", error);
                TaskDone();
            }
        });

    }
    public void UploadToKenvey3() {

        //Cursor cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseSummary where isuploaded = 0 OR isuploaded IS NULL", null);
        Cursor cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseSummary", null);
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
                    UploadEvent(event, "SummaryPersonal");
                    cursor.moveToNext();

                }
            } finally {
                cursor.close();
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
                    Integer lastdata1 = cursor.getInt(cursor.getColumnIndex("LastData"));
                    Integer WhyPosition1 = cursor.getInt(cursor.getColumnIndex("WhyPosition"));
                    event1.set("WhyPosition", cursor.getInt(cursor.getColumnIndex("WhyPosition")));
                    event1.set("LastData", lastdata1);
                    UploadEvent(event1, "TSPersonal");
                    if (lastdata1 == 1) {
                        UploadEvent(event1, "TSWorldTemp");
                    }

                    cursor.moveToNext();

                }
            } finally {
                cursor.close();
            }
            //   cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)", null);
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)");
            mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
        }

        cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesAllPeopleTemp", null);
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
                    UploadEvent(event1, "TSWorldTemp");
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            //   cursor1 = mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)", null);
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE LastData = 0 AND (isuploaded = 0 OR isuploaded IS NULL) AND (istemp = 0 OR istemp IS NULL)");
            //mAdvDatabase.execSQL("update HappyDataBase set isuploaded=1 WHERE " + quer1);
            //mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeople", null, null);
            mAdvDatabase.delete("HappyDataBaseTimeSeriesAllPeopleTemp", null, null);
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


        sp sp2 = new sp(getActivity(), 2);

        for (int iy = 0; iy <= (sp2.getarray(2).length - 1); iy += 1) {
            EventEntityWhy event1 = new EventEntityWhy();
            event1.set("whyindex", iy);
            int temp12 = sp2.get(iy);
            event1.set("whycount", sp2.get(iy));
            UploadEvent(event1, "Why");
        }
        sp2.makeallzero();

    }

    public void DownloadFromKenvey3() {
        //	runfunc.add("UploadToKenvey");
        final AsyncAppData<EventEntityWhy> myevents4 = mKinveyClient.appData("Why", EventEntityWhy.class);
        myevents4.get(new KinveyListCallback<EventEntityWhy>() {
            @Override
            public void onSuccess(EventEntityWhy[] result) {
                sp sp2 = new sp(getActivity(), 2);
                //sp sp1=new sp(getActivity(),1);
                //;
                for (EventEntityWhy x1 : result) {
                    //	sp2.set((Integer) x1.get("whyindex"))
                    int temp1 = (Integer) x1.get("whyindex");
                    int temp2 = (Integer) x1.get("whycount");
                    sp2.increment((Integer) x1.get("whyindex"), (Integer) x1.get("whycount"));
                }
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
                //TaskDone();
                //UploadToKenvey();

            }

            @Override
            public void onFailure(Throwable error) {

                Log.e("TAG", "failed to fetch all", error);
                //TaskDone();
            }


        });
        UploadToKenvey();
    }

*/
}
