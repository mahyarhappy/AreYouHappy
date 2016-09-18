package happy.happy3;

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




    public class NetworkUtil extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {
            if (new CheckNetwork(getActivity()).isNetworkAvailable4()) {
                // your get/post related code..like HttpPost = new HttpPost(url);
            } else {
                Toast.makeText(getActivity(), "no internet!", Toast.LENGTH_SHORT).show();
            }


            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        }





    public class CheckNetwork {
        private Context context;

        public CheckNetwork(Context context) {
            this.context = context;
        }

        public boolean isNetworkAvailable4() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        }






    private class DownloadImage extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDeviceBandwidthSampler.startSampling();
            //mRunningBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... url) {
            String imageURL = url[0];
            try {
                // Open a stream to download the image from our URL.
                URLConnection connection = new URL(imageURL).openConnection();
                connection.setUseCaches(false);
                connection.connect();
                InputStream input = connection.getInputStream();
                try {
                    byte[] buffer = new byte[1024];

                    // Do some busy waiting while the stream is open.
                    while (input.read(buffer) != -1) {
                    }
                } finally {
                    input.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error while downloading image.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            mDeviceBandwidthSampler.stopSampling();
            if (mConnectionClass == ConnectionQuality.UNKNOWN && mTries < 0) {
                mTries++;
                new DownloadImage().execute(mURL);
            }
            // Retry for up to 10 times until we find a ConnectionClass.
            if (mConnectionClass == ConnectionQuality.UNKNOWN ) {

                ismNetworkAvailable=false;
                TaskDone();
            }else{
                ismNetworkAvailable=true;
                TaskDone();
            }

        }
    }








    public void isNetworkAvailable( boolean InvokeTaskDone){
        mConnectionClassManager = ConnectionClassManager.getInstance();
        mDeviceBandwidthSampler = DeviceBandwidthSampler.getInstance();
        new DownloadImage().execute(mURL);
    }
    public void isNetworkAvailable3( boolean InvokeTaskDone){
        final boolean InvokeTaskDone2=InvokeTaskDone;
        final AsyncAppData<EmptyEventEntity> myevents4 = mKinveyClient.appData("TestCollectonDoNotDelete", EmptyEventEntity.class);
        myevents4.get(new KinveyListCallback<EmptyEventEntity>() {
            @Override
            public void onSuccess(EmptyEventEntity[] result) {
                if(InvokeTaskDone2){
                    ismNetworkAvailable=true;
                    TaskDone();

                }
            }

            @Override
            public void onFailure(Throwable error) {
                if(InvokeTaskDone2){
                    ismNetworkAvailable=false;
                    TaskDone();
                }
            }
        });
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
}
