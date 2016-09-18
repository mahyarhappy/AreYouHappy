package happy.happy3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 16;
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
            "ostan INTEGER"+ ", " +
            "type INTEGER"+  ")";
    String HappyDataBaseSummaryScheme="(" +
            " _id integer primary key autoincrement, " +
            "rating REAL"+ ", " +
            "IndexInPeriod INTEGER"+ ", " +
            "latitude INTEGER"+ ", " +
            "longitude INTEGER"+ ", " +
            "isuploaded INTEGER"+ ", " +
            "WhyPosition INTEGER"+ ", " +
            "ostan INTEGER"+ ", " +
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
                "ostan INTEGER"+ ", " +
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
        db.execSQL("create table " + "HappyDataBase" + HappyDataBaseScheme );

        db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeopleSummary" + HappyDataBaseSummaryScheme);
        db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeopleTemp" + HappyDataBaseScheme );
        db.execSQL("create table " + "HappyDataBaseTimeSeriesAllPeople" + HappyDataBaseScheme );

        db.execSQL("create table " + "HappyDataBaseTimeSeriesOstanAllPeopleSummary" + HappyDataBaseSummaryScheme);
        db.execSQL("create table " + "HappyDataBaseTimeSeriesOstanAllPeopleTemp" + HappyDataBaseScheme );
        db.execSQL("create table " + "HappyDataBaseTimeSeriesOstanAllPeople" + HappyDataBaseScheme );
        db.execSQL("create table " + "HappyDataBaseTimeSeriesMyOstanAllPeople" + HappyDataBaseScheme);
        db.execSQL("create table " + "HappyDataBaseTimeSeriesMyOstanAllPeopleSummary" + HappyDataBaseSummaryScheme);

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
        if (newVersion == 13 & oldVersion <= 12) {
            String upgradeQuery = "ALTER TABLE HappyDataBase ADD COLUMN ostan INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseSummary ADD COLUMN ostan INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTemp ADD COLUMN ostan INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTimeSeriesAllPeopleTemp ADD COLUMN ostan INTEGER";
            db.execSQL(upgradeQuery);
            upgradeQuery = "ALTER TABLE HappyDataBaseTimeSeriesAllPeople ADD COLUMN ostan INTEGER";
            db.execSQL(upgradeQuery);
        }
        if (newVersion == 14 & oldVersion <= 13) {
            db.execSQL("update HappyDataBase set ostan=-1");
            db.execSQL("update HappyDataBaseSummary set ostan=-1");
            db.execSQL("update HappyDataBaseTemp set ostan=-1");
            db.execSQL("update HappyDataBaseTimeSeriesAllPeopleTemp set ostan=-1");
            db.execSQL("update HappyDataBaseTimeSeriesAllPeople set ostan=-1");
        }
        if (newVersion == 15 & oldVersion <= 14) {
            String upgradeQuery = "ALTER TABLE HappyDataBaseTimeSeriesAllPeopleSummary ADD COLUMN ostan INTEGER";
            db.execSQL(upgradeQuery);
            db.execSQL("update HappyDataBaseTimeSeriesAllPeopleSummary set ostan=-1");

        }

        if (newVersion == 16 & oldVersion <= 15) {

            db.execSQL("create table " + "HappyDataBaseTimeSeriesOstanAllPeopleSummary" + HappyDataBaseSummaryScheme);
            db.execSQL("create table " + "HappyDataBaseTimeSeriesOstanAllPeopleTemp" + HappyDataBaseScheme);
            db.execSQL("create table " + "HappyDataBaseTimeSeriesOstanAllPeople" + HappyDataBaseScheme);



            db.execSQL("create table " + "HappyDataBaseTimeSeriesMyOstanAllPeople" + HappyDataBaseScheme);
            db.execSQL("create table " + "HappyDataBaseTimeSeriesMyOstanAllPeopleSummary" + HappyDataBaseSummaryScheme);

        }

    }


}


