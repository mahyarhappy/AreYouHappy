package happy.happy3;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by LENOVO on 7/18/2016.
 */
public class ShowHappiness extends Fragment {
   // private TextView ltext;
    //private Button showhap;
    private CursorWrapper cursor;
    private LineChart chart;
    private PieChart piechart;
    private ScatterChart scatterchart;
    private RadioGroup radiomh;
    private RadioGroup radiowho;
    private RadioGroup radioTarikhcheOrAmar;
    private TextView onvanex2;
    String khoshhalidartoole;
    String[] vahedezaman ;
    String haiemontahibealan;
    String comment1;
    String[] chekasi;
    String amarcommentgraph1;
    String amarcommentgraph2;
    LinearLayout chartcontainer;
    boolean IsGlobalWhyLoaded;
     View w;
    View w2;
    //  private double minhour;
    int WhichMode;
    private RadioGroup radiofromwhen;
    private String khoshhali;
    private String dartoole;
    private String miangin;
    private String zohr;
    private String shab;
    private String sobh;
    private String ghoroob;
    private String avamel;
    private int ostan1;
    private long lasttimepassednotconverted;
Integer zamanhaMs;
    private AppCompatRadioButton region1;
    private AppCompatRadioButton min1;
    private AppCompatRadioButton hour1;
    private AppCompatRadioButton day1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         khoshhalidartoole= getResources().getString(R.string.khoshhalidartoole);
         vahedezaman = getResources().getStringArray(R.array.vahedezaman);
         haiemontahibealan= getResources().getString(R.string.haiemontahibealan);
         comment1= getResources().getString(R.string.commentzirenemoodarTS);
         chekasi = getResources().getStringArray(R.array.chekasi);
         amarcommentgraph1=getResources().getString(R.string.amarcommentgraph1);
         amarcommentgraph2=getResources().getString(R.string.amarcommentgraph2);
        khoshhali=getResources().getString(R.string.khoshhali);
        dartoole=getResources().getString(R.string.dartoole);
        miangin = getResources().getString(R.string.miangin);
        zohr = getResources().getString(R.string.zohr);
        shab = getResources().getString(R.string.shab);
        sobh = getResources().getString(R.string.sobh);
        ghoroob= getResources().getString(R.string.ghoroob);
        avamel= getResources().getString(R.string.avamel);
        ostan1 = new MainFragment().ostan;
//        minhour=1000*60.0*60*24;
//here the Fragment does not get inflated! there is no setContentView
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,final  ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.showhappiness, container, false);
        IsGlobalWhyLoaded=false;
        radiomh=(RadioGroup)v.findViewById(R.id.radio_mh);
        radiowho=(RadioGroup)v.findViewById(R.id.radio_who);
        radiofromwhen = (RadioGroup) v.findViewById(R.id.radioGroupsincewhen);
        region1=(AppCompatRadioButton) v.findViewById(R.id.region);
        min1=(AppCompatRadioButton) v.findViewById(R.id.radio_min);
        hour1=(AppCompatRadioButton) v.findViewById(R.id.radio_hour);
        day1=(AppCompatRadioButton) v.findViewById(R.id.radio_day);
        chartcontainer = (LinearLayout) v.findViewById(R.id.chartcontainer);
       onvanex2= (TextView)v.findViewById(R.id.onvanexx);

        w = inflater.inflate(R.layout.linearchart1, container, false);
        w2 = inflater.inflate(R.layout.piechart1, container, false);
        chart = (LineChart) w.findViewById(R.id.linechart);
        piechart = (PieChart) w2.findViewById(R.id.piechart);
        radioTarikhcheOrAmar=(RadioGroup) v.findViewById(R.id.radio_TarikhcheOrAmar);
        //drawchart();
        Integer x1=QueryPreferences.getStoredInt(getActivity(),"DefaultTime");
        Integer x2=QueryPreferences.getStoredInt(getActivity(),"DefaultTarikhcheOrAmar");
        Integer x3=QueryPreferences.getStoredInt(getActivity(),"DefaultWho");
        Integer x4=QueryPreferences.getStoredInt(getActivity(),"FromWhen");
        MainFragment xx = new MainFragment();
     //   xx.context1=getActivity();
       // xx.updatedb2();
     //   radioTarikhcheOrAmar.check(R.id.radio_amar);
        if(x1==-1){
            QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_hour);
            x1=QueryPreferences.getStoredInt(getActivity(),"DefaultTime");
        }
        if(x2==-1){
            QueryPreferences.setStoredInt(getActivity(),"DefaultTime",R.id.radio_amar);
            x2=QueryPreferences.getStoredInt(getActivity(),"DefaultTime");
        }
        if(x3==-1){
            QueryPreferences.setStoredInt(getActivity(),"DefaultWho",R.id.man);
            x3=QueryPreferences.getStoredInt(getActivity(),"DefaultWho");
        }
        if(x4==-1){
            QueryPreferences.setStoredInt(getActivity(),"FromWhen",R.id.hamezamanha);
            x4=QueryPreferences.getStoredInt(getActivity(),"FromWhen");
        }

            radiomh.check(x1);

        if(x2!=-1){
            radioTarikhcheOrAmar.check(x2);
        }

        if(x3!=-1){
            radiowho.check(x3);
        }


        if(x4!=-1){
            radiofromwhen.check(x4);
        }




       drawchart(v, inflater,  container);

        //QueryPreferences.getStoredInt(getActivity(),"DefaultTime",radiomh.getCheckedRadioButtonId());
        radiomh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                QueryPreferences.setStoredInt(getActivity(),"DefaultTime",radiomh.getCheckedRadioButtonId());
                drawchart(v, inflater,  container);
            }
        });


        radioTarikhcheOrAmar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                QueryPreferences.setStoredInt(getActivity(),"DefaultTarikhcheOrAmar",radioTarikhcheOrAmar.getCheckedRadioButtonId());
                drawchart(v, inflater,  container);
            }
        });

        radiowho.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                QueryPreferences.setStoredInt(getActivity(),"DefaultWho",radiowho.getCheckedRadioButtonId());
                drawchart(v, inflater,  container);
            }
        });
        radiofromwhen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                QueryPreferences.setStoredInt(getActivity(),"FromWhen",radiofromwhen.getCheckedRadioButtonId());

                drawchart(v, inflater,  container);
            }
        });
      //  Integer DefaultTime = QueryPreferences.getStoredInt(getActivity(),"DefaultTime");

        return v;
    }

    private void computezaman() {

    }

    private void drawchart(View v,LayoutInflater inflater, ViewGroup container) {
    onvanex2.setText("");
    long lasttimepassed = 0;
    whichSelected();
    int yy = WhichMode;
    int whoindex=0;
    if(radiowho.getCheckedRadioButtonId()==R.id.man){
        whoindex=0;
    }else if(radiowho.getCheckedRadioButtonId()==R.id.kol ||radiowho.getCheckedRadioButtonId()==R.id.kol ){
        whoindex=1;
    }else if(radiowho.getCheckedRadioButtonId()==R.id.kol ||radiowho.getCheckedRadioButtonId()==R.id.region ){
        whoindex=2;
    }

    //xx.updatedb();
   // xx.updatedb("HappyDataBaseTemp","HappyDataBase");

    ArrayList<Entry> entries = new ArrayList<Entry>();
    ArrayList<PieEntry> entries2 = new ArrayList<PieEntry>();
    ArrayList<String> labels = new ArrayList<String>();
    int i1 = 0;
    Integer IsTaikhche=-10;
    Cursor cursor1 = null;
    if(radioTarikhcheOrAmar.getCheckedRadioButtonId()== R.id.radio_tarikhche){
        radiofromwhen.setVisibility(View.VISIBLE);
        radiomh.setVisibility(View.VISIBLE);
        region1.setVisibility(View.VISIBLE);
        min1.setVisibility(View.VISIBLE);
        min1.setText(getActivity().getResources().getString(R.string.daghighe));
        hour1.setText(getActivity().getResources().getString(R.string.saat));
        day1.setText(getActivity().getResources().getString(R.string.day));
        IsTaikhche=1;

        onvanex2.setText(khoshhali+" "+chekasi[whoindex]+" "+dartoole+" "+vahedezaman[WhichMode]+" "+haiemontahibealan+
                " "+System.getProperty("line.separator")+comment1);
      //  onvanex2.setText("khoshhali dar toole zaman("+new MainFragment().ClockNames[WhichMode]+")");
        if(radiowho.getCheckedRadioButtonId()==R.id.man){
            cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBase where type = " + yy, null);
        }else if(radiowho.getCheckedRadioButtonId()==R.id.kol  ){
            cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesAllPeople where type = " + yy, null);
        }else if(radiowho.getCheckedRadioButtonId()==R.id.region ){
            cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesOstanAllPeople where type = " + yy+" AND ostan ="+ostan1, null);
        }

    }else if (radioTarikhcheOrAmar.getCheckedRadioButtonId()== R.id.radio_amar){
        min1.setText(getActivity().getResources().getString(R.string.amardaghighe));
        hour1.setText(getActivity().getResources().getString(R.string.amarsaat));
        day1.setText(getActivity().getResources().getString(R.string.amarday));
        min1.setVisibility(View.INVISIBLE);
        IsTaikhche=0;
        radiofromwhen.setVisibility(View.INVISIBLE);
        radiomh.setVisibility(View.VISIBLE);
        region1.setVisibility(View.VISIBLE);
        onvanex2.setText(khoshhali+" "+chekasi[whoindex]+" "+
                dartoole+" "+vahedezaman[WhichMode]+" "+
                amarcommentgraph2+" "+vahedezaman[WhichMode+1]+System.getProperty("line.separator")+miangin);
        if(radiowho.getCheckedRadioButtonId()==R.id.man){
            cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseSummary where type = " + yy, null);
        }else if(radiowho.getCheckedRadioButtonId()==R.id.kol ){
            cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesAllPeopleSummary where type = " + yy, null);
        }else if(radiowho.getCheckedRadioButtonId()==R.id.region ){
            cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseTimeSeriesOstanAllPeopleSummary where type = " + yy+" AND ostan ="+ostan1, null);
        }

    }else if (radioTarikhcheOrAmar.getCheckedRadioButtonId()== R.id.radio_chera){
        radiofromwhen.setVisibility(View.INVISIBLE);
        radiomh.setVisibility(View.INVISIBLE);
        region1.setVisibility(View.INVISIBLE);
        //cursor1 = new MainFragment().mAdvDatabase.rawQuery("SELECT * FROM HappyDataBaseChera", null);
        IsTaikhche=-1;
    }
    boolean DrawLine=false;
    if(IsTaikhche!=-10) {
        if (IsTaikhche == 0 || IsTaikhche == 1) {
            DrawLine = true;
            cursor = new CursorWrapper(cursor1);
            if (IsTaikhche == 1) {

                cursor.moveToLast();
                if (!cursor.isAfterLast()) {
                    lasttimepassed = cursor.getLong(cursor.getColumnIndex("xlabel"));
                }
                
            }
            cursor = new CursorWrapper(cursor1);
            cursor.moveToFirst();
            Calendar cc = GregorianCalendar.getInstance();
           // ContentValues CurrentTimeValue =new MainFragment().CurrentTime();
            lasttimepassed=cc.getTimeInMillis();// CurrentTimeValue.getAsLong("date1");
            lasttimepassednotconverted=lasttimepassed;
            lasttimepassed= new MainFragment().convert1(lasttimepassed,WhichMode);

            try {
                String state2="";
                if (IsTaikhche == 1) {

                    if (radiofromwhen.getCheckedRadioButtonId() == R.id.hamezamanha) {
                        state2="hamezamanha";
                    } else if (radiofromwhen.getCheckedRadioButtonId() == R.id.azyekhaftepish) {
                        state2="azyekhaftepish";
                    }else if (radiofromwhen.getCheckedRadioButtonId() == R.id.azyekmahepish) {
                        state2 = "azyekmahepish";

                    }
                }
                while (!cursor.isAfterLast()) {
                    double rating = cursor.getDouble(cursor.getColumnIndex("rating"));
                    if (IsTaikhche == 1) {
                        long timepassed = cursor.getLong(cursor.getColumnIndex("xlabel"));
                        long date14 = cursor.getLong(cursor.getColumnIndex("date1"));
                        boolean letadd=false;
                        if (  state2.equals("hamezamanha")) {
                            letadd=true;
                        } else if (  state2.equals("azyekhaftepish") && lasttimepassednotconverted-date14<= 1000*60*60*24*7L) {
                            letadd=true;
                        }else if (   state2.equals("azyekmahepish") && lasttimepassednotconverted-date14<= 1000*60*60*24*31L) {
                            letadd=true;
                        }
                        if (letadd) {
                            entries.add(new Entry(timepassed - lasttimepassed, (float) rating));
                        }

                    } else if (IsTaikhche == 0) {
                        int IndexInPeriod = cursor.getInt(cursor.getColumnIndex("IndexInPeriod"));
                        entries.add(new Entry(IndexInPeriod, (float) rating));


                    }
                    //labels.add(Integer.toString(i1));
                    i1 = i1 + 1;
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
                Collections.sort(entries, new EntryXComparator());
            }
        } else {
            DrawLine = false;
            i1 = 0;
            String stringarray[] = getActivity().getResources().getStringArray(R.array.planets_array);
            sp sp1 = null;
            if (radiowho.getCheckedRadioButtonId() == R.id.man) {
                sp1 = new sp(getActivity(), 1);
            } else if (radiowho.getCheckedRadioButtonId() == R.id.kol || radiowho.getCheckedRadioButtonId() == R.id.region) {
                sp1 = new sp(getActivity(), 2);
            }

            for (int i = 1; i <= (sp1.getarray(2).length - 1); i += 1) {
                //   int popo=sp.get(i);
                if (sp1.get(i) > 0) {
                    i1 += 1;
                    //entries2.add(new PieEntry(i, sp1.get(i)));

                    if (stringarray.length - 1 >= i) {
                        if (i == 0) {
                            labels.add(getActivity().getResources().getString(R.string.notchosen));
                        } else {
                            labels.add(stringarray[i]);
                        }
int percenttemp=sp1.getPercent(i);
                        if(percenttemp>15) {
                            entries2.add(new PieEntry(percenttemp, stringarray[i]));
                        }
                    }
                }
                // entries.add(new Entry(stringarray[i],sp.get(i)));
            }

        }
        //if(i1>0) {
        boolean scatterOrLine = false;
        if (scatterOrLine == false) {

            // dataset.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

            if (DrawLine) {
                if (w2.getParent() != null) {
                    chartcontainer.removeView(w2);
                }
                // isadded=chartcontainer.findViewById(R.id.linearchart1);
                if (w.getParent() == null) {
                    chartcontainer.addView(w);
                }

                chart.invalidate();
                chart.clear();
                chart.setNoDataText(getString(R.string.nodata));

                if (i1 > 0) {
                    LineDataSet dataset = new LineDataSet(entries, "o");
                    dataset.setDrawValues(false);

                    dataset.setLineWidth(6);
                   dataset.setDrawCircleHole(true);

                //    dataset.setColor(Color.RED);
                //    dataset.setCircleColor(Color.RED);
                    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
                    dataSets.add(dataset); // add the datasets
                    dataset.setDrawFilled(true);
                    int soorat1 = ContextCompat.getColor(getActivity(), R.color.colorAccent);
                    dataset.setColor(soorat1);
                  //  dataset.setFillFormatter();
                    dataset.setFillColor(soorat1);
                    dataset.setCircleRadius(6);
                    dataset.setCircleColor(Color.BLACK);
                 //   dataset.setCircleColorHole(soorat1);
                //   dataset.setColor(Color.GREEN);
                    dataset.setCircleHoleRadius(4);
                   // dataset.setColors(new int[]{Color.BLACK});
                    //chart = (LineChart) v.findViewById(R.id.chart);
                    LineData data = new LineData(dataset);
                    chart.setData(data);
                    YAxis yAxis = chart.getAxisLeft();
                    yAxis.setDrawGridLines(false);
                    yAxis = chart.getAxisRight();
                    yAxis.setDrawGridLines(false);
                    chart.setData(data);
                    chart.setDescription("");
                    chart.getXAxis().setDrawGridLines(false);
                    final Integer finalIsTaikhche = IsTaikhche;
                    chart.getXAxis().setValueFormatter(new AxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            if (finalIsTaikhche == 0   && WhichMode==1) {
                                switch ((int) value) {

                                    case 12:
                                        return zohr;
                                    case 0:
                                        return shab;
                                    case 18:
                                        return ghoroob;
                                    case 6:
                                        return sobh;
                                    default:
                                        return String.valueOf(value);
                                      //  return "";
                                }
                            }else  if (finalIsTaikhche == 1  && WhichMode==2) {
                                switch ((int) (value*2)) {

                                    case -2:
                                        String dirooz=getResources().getString(R.string.dirooz);
                                        return dirooz;
                                    case 0:
                                        String emrooz=getResources().getString(R.string.emrooz);
                                        return emrooz;
                                    case -4:
                                        String parirooz=getResources().getString(R.string.parirooz);
                                        return parirooz;
                                    default:
                                        //return String.valueOf(value);
                                        if(value<-2){
                                            return String.valueOf((int) value);
                                        }else{
                                            return "";
                                        }

                                }
                            }
                            else  if (finalIsTaikhche == 1  && WhichMode==2) {
                                switch ((int) (value*2)) {
                                    case 0:
                                        return "emrooz";
                                    case -4:
                                        String parirooz=getResources().getString(R.string.parirooz);
                                        return parirooz;
                                    default:
                                        //return String.valueOf(value);
                                        if(value<-2){
                                            return String.valueOf((int) value);
                                        }else{
                                            return "";
                                        }

                                }
                            }
                            else{
                                return String.valueOf((int) value);
                            }

                        }

                        @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    });

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    chart.getLegend().setEnabled(false);
                    chart.invalidate();
                }
            } else {

                if (w.getParent() != null) {
                    chartcontainer.removeView(w);
                }
                if (w2.getParent() == null) {
                    chartcontainer.addView(w2);
                }
                //  w2 = inflater.inflate(R.layout.piechart1, container, false);
                //   piechart = (PieChart) v.findViewById(R.id.piechart);
                //  chartcontainer.addView(w2);
                piechart.invalidate();
                piechart.clear();
                piechart.setNoDataText(getString(R.string.nodata));
                piechart.setDescription("");
                //    new LineDataSet()
                if (i1 > 0) {
                    PieDataSet dataset = new PieDataSet(entries2, "o");
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    PieData data = new PieData(dataset);
                    data.setValueTextSize(30f);
                    data.setValueTextSize(30f);

                    //     data.setValueTypeface(mTfLight);
                    data.setValueTextColor(Color.BLACK);
                    //    (labels,);
                    // (labels, dataset);
                    piechart.setData(data);
                    piechart.setHoleRadius(0f);
                    piechart.setDrawHoleEnabled(false);
                    onvanex2.setText(avamel+" "+chekasi[whoindex]);
                }
                //piechart.setx
            }


            if (1 == 0) {
                chart.animateX(Math.min(i1 * 100, 1000));
            }


            //chart.invalidate();

        } else {
            ScatterDataSet scatterdataset = new ScatterDataSet(entries, "of Calls");
            ScatterData scatterdata = new ScatterData(scatterdataset);
            scatterchart.setData(scatterdata);
        }
    }
    // set the data and list of lables into chart
}
public void whichSelected(){
    int selectedId = radiomh.getCheckedRadioButtonId();
    if(selectedId== R.id.radio_hour){
        //        minhour=1000*60*60;
        WhichMode=1;
    }else if(selectedId== R.id.radio_min){
        //      minhour=1000*60*60*24;
        WhichMode=0;
    }else if(selectedId== R.id.radio_day){
        //    minhour=1000*60;
        WhichMode=2;
    }

}
    public void drawfromdata(ArrayList<Entry> entries ){

    }


}
