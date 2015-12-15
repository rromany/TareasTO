package com.tareasto.rob.tareasto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
//import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class itemActivity extends ActionBarActivity {

    private String sdate;
    private EditText nombre;
    private EditText detalle;
    private Integer uriimg;
    private ImageView prev;
    private TimePicker tp;
    private CheckBox pcb;
    private Integer ao;
    ArrayList<String> fechass = new ArrayList<String>();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        nombre = (EditText)findViewById(R.id.etitem1);
        detalle = (EditText)findViewById(R.id.etitem2);
        prev = (ImageView)findViewById(R.id.imageView2);
        tp = (TimePicker)findViewById(R.id.timePicker);
        pcb = (CheckBox)findViewById(R.id.cbalarmi);
        Bundle bundle = getIntent().getExtras();
        fechass = (ArrayList<String>) bundle.getSerializable("fechas");
        context=App.context;
        uriimg = R.drawable.education_application5;
        prev.setImageResource(uriimg);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void aceptar(View v) {
        tp.clearFocus();
        Integer hora = tp.getCurrentHour();
        Integer minn = tp.getCurrentMinute();
        IndiceSQLiteHelper indbh =
                new IndiceSQLiteHelper(this, "DBTareas", null, 1);

        SQLiteDatabase db = indbh.getWritableDatabase();
        for(int index = 0; index < fechass.size(); index++){
            sdate =fechass.get(index);
            // creamos la tabla con la fecha como nombre
            String cmdsql = "CREATE TABLE IF NOT EXISTS " + sdate + " (nombre TEXT, detalle TEXT, imagen INTEGER, horas INTEGER, minutos INTEGER, alarma INTEGER, listo INTEGER)";
        String[] args = new String[]{sdate.replace("'", "")};
        db.execSQL(cmdsql);

        String[] campos = new String[]{"items"};
        //String[] args = new String[] {sdate};
        //String[] campos0 = new String[] {"nombre", "detalle"};
        int ial =0;
        if (pcb.isChecked()) {
            String[] cmp = new String[]{"id"};
            Cursor ccc;
            Integer nid =1;// valor del id de la alarma esperado
            Integer vid =1;// valor para comparar y hacer funcionar el loop
            do {
                ccc = db.query("Ids", cmp, "id="+nid, null, null, null, null);
                if (ccc.moveToFirst()) {
                    nid=nid+1;
                    vid=vid+1;
                }
                else{
                    vid=vid+1;
                    ContentValues idval = new ContentValues();
                    idval.put("id", nid);
                    db.insert("Ids", null,idval);
                }
            ccc.close();
            } while (nid==vid);
            ial=nid;
            String[] parts = sdate.replace("'", "").split("-");
            Calendar myAlarmDate = Calendar.getInstance();
            myAlarmDate.setTimeInMillis(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            //int me =1;
            int monthInt = 1;
            try {
                cal.setTime(new SimpleDateFormat("MMM").parse(parts[1]));
                monthInt = cal.get(Calendar.MONTH);// + 1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //int monthInt = cal.get(Calendar.MONTH) + 1;
            Log.i("iniciando alarma",parts[2]+"-"+ monthInt+"-"+ parts[0]+"-"+ hora+"-"+ minn);
            myAlarmDate.set(Integer.parseInt(parts[2]), monthInt, Integer.parseInt(parts[0]), hora, minn, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent _myIntent = new Intent(itemActivity.this, MyReceiver.class);
            _myIntent.setAction("com.tareasto.rob.tareasto.mybroadcast");
            _myIntent.putExtra("nombre",nombre.getText().toString());
            _myIntent.putExtra("fecha",sdate.replace("'", ""));
            _myIntent.putExtra("horas",hora);
            _myIntent.putExtra("minutos",minn);
            _myIntent.putExtra("id",ial);
            PendingIntent _myPendingIntent = PendingIntent.getBroadcast(itemActivity.this, ial, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);



        } else {
            ial = 0;
            String[] cmp = new String[]{"alarma"};
            Cursor ccc=db.query(sdate, cmp, "nombre='"+nombre.getText().toString()+"'", null, null, null, null);
            if (ccc.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    //String fechas= c2.getString(0);
                    Integer nnn = ccc.getInt(0);
                    if (nnn > 0) {
                        db.delete("Ids", "id=" + nnn, null);
                    }

                } while (ccc.moveToNext());
            }
            ccc.close();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent _myIntent = new Intent(itemActivity.this, MyReceiver.class);
            _myIntent.setAction("com.tareasto.rob.tareasto.mybroadcast");
            _myIntent.putExtra("fecha",sdate.replace("'", ""));
            _myIntent.putExtra("horas",hora);
            _myIntent.putExtra("minutos",minn);
            _myIntent.putExtra("id",ial);
            PendingIntent _myPendingIntent = PendingIntent.getBroadcast(itemActivity.this, ial, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);
            alarmManager.cancel(_myPendingIntent);

        }
        ContentValues nuevoRegistro = new ContentValues();

        //String value = text.getText().toString();
        nuevoRegistro.put("nombre", nombre.getText().toString());
        nuevoRegistro.put("detalle", detalle.getText().toString());
        nuevoRegistro.put("imagen", uriimg);

        nuevoRegistro.put("horas", hora);
        nuevoRegistro.put("minutos", minn);
        nuevoRegistro.put("alarma", ial);
        nuevoRegistro.put("listo", 0);
        //Insertamos el registro en la base de datos
        db.insert(sdate, null, nuevoRegistro);

        //db.execSQL("INSERT INTO Indice (fecha, items) " +
        //       "VALUES ('" + nombre + "', " + items +")");

        Cursor c2 = db.query("Indice", campos, "fecha=?", args, null, null, null);
        //Nos aseguramos de que existe al menos un registro
        if (c2.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                //String fechas= c2.getString(0);
                Integer num = c2.getInt(0);
                num = num + 1;
                ContentValues valores = new ContentValues();
                valores.put("fecha", sdate.replace("'", ""));
                String[] args2 = new String[]{String.valueOf(num)};

                //Actualizamos el registro en la base de datos
                db.update("Indice", valores, "items=?", args2);
            } while (c2.moveToNext());
        } else {
            db.execSQL("INSERT INTO Indice (fecha, items) " +
                    "SELECT " + sdate + ", " + 1 + " WHERE NOT EXISTS (SELECT 1 FROM Indice WHERE fecha = " + sdate + ")");
        }
            c2.close();
    }
        // cerramos
        //c2.close();
        db.close();

        Intent returnIntent = new Intent();
        if (fechass.size()<2) {
            returnIntent.putExtra("result", sdate);
            //returnIntent.putExtra("imgid",uriimg);
            setResult(RESULT_OK, returnIntent);
        }
        else{
            setResult(RESULT_CANCELED, returnIntent);
        }

        finish();



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                uriimg=data.getIntExtra("result",0);
                prev.setImageResource(uriimg);

            }

            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//onActivityResult


    public void cancelar(View v) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();

    }

    public void selec(View v) {
        Intent i = new Intent(this, ActivityIMG.class);
        //i.putExtra("fecha", sdate);
        //startActivity(i);
        startActivityForResult(i, 1);

    }
}
