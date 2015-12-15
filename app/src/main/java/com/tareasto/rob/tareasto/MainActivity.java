package com.tareasto.rob.tareasto;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends FragmentActivity  {
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private Intent starterIntent;
    ArrayList<String> fechass = new ArrayList<String>();
    //FragmentTransaction t;

    private void setCustomResourceForDates(String nn) {
/*        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -18);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 16);
        Date greenDate = cal.getTime();*/
        if (caldroidFragment != null) {
        // Leer base de datos
            SimpleDateFormat formattere = new SimpleDateFormat("dd-MMM-yyyy");
            IndiceSQLiteHelper indbh =
                    new IndiceSQLiteHelper(this, "DBTareas", null, 1);
            String fech;
            SQLiteDatabase db = indbh.getReadableDatabase();
            String[] campos = new String[] {"fecha"};
            boolean dblanco = true;
            Cursor c = db.query("Indice", campos, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    fech = c.getString(0);

                        if ((nn!=null) && (nn.equals("'"+fech+"'"))) {

                            dblanco=false;
                            }


                    try {
                        caldroidFragment.setBackgroundResourceForDate(R.color.green, formattere.parse(fech));
                        caldroidFragment.setTextColorForDate(R.color.white, formattere.parse(fech));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }while(c.moveToNext());
            }

            if ((nn!=null) && dblanco){
                try {
                    caldroidFragment.setBackgroundResourceForDate(R.color.white, formattere.parse(nn.replace("'", "")));
                    caldroidFragment.setTextColorForDate(R.color.black, formattere.parse(nn.replace("'", "")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


            c.close();
            db.close();

/*            caldroidFragment.setBackgroundResourceForDate(R.color.blue,
                    blueDate);
            caldroidFragment.setBackgroundResourceForDate(R.color.green,
                    greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        starterIntent = getIntent();
        Log.d("inicio", "prueba");
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();


              // Activity  despues de rotacion
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // activity nueva
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);


            caldroidFragment.setArguments(args);
        }
        //caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        setCustomResourceForDates(null);

        // Agregar a la activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onLongClickDate(Date date, View view) {

                ejecutar("'"+formatter.format(date)+"'");

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectDate(Date date, View view) {
            //public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Simple click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                String ff="'"+formatter.format(date)+"'";
                //ColorDrawable buttonColor = (ColorDrawable)view.getBackground();
                //int cb = buttonColor.getColor();

                if (!fechass.contains(ff)){
                    caldroidFragment.setBackgroundResourceForDate(R.color.blue,date);
                    caldroidFragment.setTextColorForDate(R.color.white,date);
                    fechass.add(ff);

                }
                else{


                    setCustomResourceForDates(ff);
                    fechass.remove(ff);
                    for(int index = 0; index < fechass.size(); index++){
                        String aux = fechass.get(index).replace("'", "");
                        try {
                            Date d = formatter.parse(aux);
//                            Calendar cal1 = Calendar.getInstance();
//                            //cal1.setTime();
//                            Calendar cal2 = Calendar.getInstance();
//                            cal2.setTime(d);
////                            Boolean bbb = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
////                                    cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
//                            if ((cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH))&&
//                                    (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))&&
//                                    (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))){
//
//                                caldroidFragment.setBackgroundResourceForDate(R.drawable.custom_border,d);
//                                caldroidFragment.setTextColorForDate(R.color.black,d);
//
//                            }
//                            else {
                                caldroidFragment.setBackgroundResourceForDate(R.color.blue, d);
                                caldroidFragment.setTextColorForDate(R.color.white, d);
                            //}
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Date d = null;


                    }

                    //Date d = date;
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date);
                    Calendar cal2 = Calendar.getInstance();
                    //cal2.setTime();
                    Boolean bbb = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                    if (bbb){

                        caldroidFragment.setBackgroundResourceForDate(R.drawable.custom_border,date);
                        caldroidFragment.setTextColorForDate(R.color.black,date);

                    }
                }





                caldroidFragment.refreshView();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        //final TextView textView = (TextView) findViewById(R.id.textview);

        //final Button customizeButton = (Button) findViewById(R.id.customize_button);



        Button showDialogButton = (Button) findViewById(R.id.show_dialog_button);

        final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Usar como dialogo a Caldroid
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // Rotacion
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup argumentos
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getSupportFragmentManager(),
                        dialogTag);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_about) {

            Intent intent = new Intent(this, ActivityAbout.class);
            this.startActivity(intent);
        }else{
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                setCustomResourceForDates(null);
                caldroidFragment.refreshView();
            }
            if (resultCode == RESULT_CANCELED) {
                setCustomResourceForDates(null);
                caldroidFragment.refreshView();
            }
        }
    }
    public void ejecutar(String datee) {
        //SimpleDateFormat formattere = new SimpleDateFormat("dd MMM yyyy");
        Intent i = new Intent(this, ActivityPrueba.class);
        i.putExtra("fecha", datee);
        startActivityForResult(i, 1);
    }
    public void ejecutarvarios(View view) {
        //SimpleDateFormat formattere = new SimpleDateFormat("dd MMM yyyy");
        if (!fechass.isEmpty()) {
            Intent i = new Intent(this, itemActivity.class);
            Bundle bundleObject = new Bundle();
            bundleObject.putSerializable("fechas", fechass);
            //i.putExtra("fechas", fechass);
            //i.putExtra("act", 0);
            i.putExtras(bundleObject);
            startActivityForResult(i, 1);
            fechass.clear();
        }
        else{
            Toast.makeText(getApplicationContext(),
                        "Ninguna Fecha seleccionada",
                        Toast.LENGTH_SHORT).show();
        }

    }

    private static Context context;

    public static Context getAppContext(){
        return MainActivity.context;
    }

}
