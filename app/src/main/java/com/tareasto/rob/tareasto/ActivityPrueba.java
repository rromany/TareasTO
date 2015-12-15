package com.tareasto.rob.tareasto;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Calendar;

//import java.text.SimpleDateFormat;
//import java.util.Locale;
//import java.util.Date;


public class ActivityPrueba extends ActionBarActivity {

    private String sdate;
    //private TextView txttexto;
    private ListView lista;
    private String ntarea;
    private Intent starterIntent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        starterIntent = getIntent();
        TextView txtfecha = (TextView)findViewById(R.id.textview1);
        //txttexto = (TextView)findViewById(R.id.textViewt);
        Bundle bundle = getIntent().getExtras();
        sdate = bundle.getString("fecha");
        ActionBar ab = getSupportActionBar();
        ab.setTitle(sdate);
        context=App.context;
        lista = (ListView) findViewById(R.id.ListView_listado);
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>(); // agregar abajo
        //String texto ="Tabla: \n";
//        lo = bundle.getLong("fecha", 0);
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(lo);
//        Date ddate = cal.getTime();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        txtfecha.setText("Agregar actividad o Tarea");

        IndiceSQLiteHelper indbh =
                new IndiceSQLiteHelper(this, "DBTareas", null, 1);

        SQLiteDatabase db = indbh.getReadableDatabase();
        String[] campos = new String[] {"items"};
        String[] args = new String[] {sdate.replace("'", "")};
        String[] campos2 = new String[] {"nombre", "detalle", "imagen","horas","minutos","alarma", "listo"};


        Cursor c = db.query("Indice", campos, "fecha=?", args, null, null, null);

        if (c.moveToFirst()) {
            Log.i("APrueba", "primer if");
            //Recorremos el cursor hasta que no haya más registros
            do {
                //String fecha= c.getString(0);
                Integer items = c.getInt(0);
                if (items > 0) {
                    Log.i("APrueba", "segundo if");
                    Cursor c2 = db.query(sdate, campos2, null, null, null, null, "horas ASC, minutos ASC");
                    Log.i("largo cursor Oncreate",String.valueOf(c2.getCount()));
                    if (c2.moveToFirst()) {
                        Log.i("APrueba", "tercer if");
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            datos.add(new Lista_entrada(c2.getInt(2),c2.getString(0),c2.getString(1),c2.getInt(3),c2.getInt(4),c2.getInt(5),c2.getInt(6))); // agregar abajo
                            //texto = texto + c2.getString(0) + "-" + c2.getString(1) +"\n";
                            //Log.d("log create", texto);
                        } while(c2.moveToNext());
                    }
                    c2.close();
                }
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        listar(datos);
        //txttexto.setText(texto);
        //Log.i("texto", texto);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_prueba, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                sdate=data.getStringExtra("result");
                //uriimg=data.getIntExtra("imgid",0);
                //String texto ="Tabla: \n";

                IndiceSQLiteHelper indbh =
                        new IndiceSQLiteHelper(this, "DBTareas", null, 1);

                SQLiteDatabase db = indbh.getReadableDatabase();
                String[] campos = new String[] {"items"};
                String[] args = new String[] {sdate.replace("'", "")};
                String[] campos2 = new String[] {"nombre", "detalle", "imagen","horas","minutos","alarma", "listo"};
                ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

                Cursor c = db.query("Indice", campos, "fecha=?", args, null, null, null);

                if (c.moveToFirst()) {
                    Log.i("APrueba", "primer if");
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        //String fecha= c.getString(0);
                        Integer items = c.getInt(0);
                       if (items > 0) {
                           Log.i("APrueba", "segundo if");
                            Cursor c2 = db.query(sdate, campos2, null, null, null, null, "horas ASC, minutos ASC");
                           Log.i("largo cursor result", String.valueOf(c2.getCount()));
                            if (c2.moveToFirst()) {
                                Log.i("APrueba", "tercer if");
                                //Recorremos el cursor hasta que no haya más registros
                                do {
                                    datos.add(new Lista_entrada(c2.getInt(2),c2.getString(0),c2.getString(1),c2.getInt(3),c2.getInt(4),c2.getInt(5),c2.getInt(6)));
                                    //texto =texto + c2.getString(0) + "-" + c2.getString(1) +"\n";
                                    //Log.d("log volver", texto);
                                } while(c2.moveToNext());
                            }
                            c2.close();
                        }
                    } while(c.moveToNext());
                }
                c.close();
                db.close();
                listar(datos);
                //txttexto.setText(texto);
                //Log.i("texto", texto);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//onActivityResult

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void finalizar(View v) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void agregar(View v) {
        Intent i = new Intent(this, itemActivity.class);
        Bundle bundleObject = new Bundle();
        ArrayList<String> fechass = new ArrayList<String>();
        fechass.add(sdate);
        bundleObject.putSerializable("fechas", fechass);
        //i.putExtra("fechas", fechass);
        //i.putExtra("act", 0);
        i.putExtras(bundleObject);
        startActivityForResult(i, 1);


    }
    private void listar(ArrayList<Lista_entrada> datos) {
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());

                    TextView hor = (TextView) view.findViewById(R.id.textViewCLK);
                    if (hor != null)
                        hor.setText(((Lista_entrada) entrada).get_hor()+":"+((Lista_entrada) entrada).get_min());


                    CheckBox exradio = (CheckBox) view.findViewById(R.id.cbalarm);
                    if (exradio != null) {
                        exradio.setChecked((((Lista_entrada) entrada).get_alarma()) == 1);
                        exradio.setTag(((Lista_entrada) entrada).get_textoEncima());
                        exradio.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Ajustando Alarma", Toast.LENGTH_SHORT).show();

                                IndiceSQLiteHelper indbha =
                                        new IndiceSQLiteHelper(ActivityPrueba.this, "DBTareas", null, 1);

                                SQLiteDatabase dba = indbha.getWritableDatabase();
                                ContentValues nuevoRegistroa = new ContentValues();
                                ntarea= v.getTag().toString();
                                //String[] args2 = new String[]{ntarea};
                                int ial;
                                if (((CheckBox)v).isChecked()){
                                    String[] cmp = new String[]{"id"};
                                    Cursor ccc;
                                    Integer nid =1;// valor del id de la alarma esperado
                                    Integer vid =1;// valor para comparar y hacer funcionar el loop
                                    do {
                                        ccc = dba.query("Ids", cmp, "id="+nid, null, null, null, null);
                                        if (ccc.moveToFirst()) {
                                            nid=nid+1;
                                            vid=vid+1;
                                        }
                                        else{
                                            vid=vid+1;
                                            ContentValues idval = new ContentValues();
                                            idval.put("id", nid);
                                            dba.insert("Ids", null,idval);
                                        }
                                        ccc.close();
                                    } while (nid.equals(vid));
                                    ial=nid;

                                    cmp = new String[]{"horas","minutos"};
                                    ccc = dba.query(sdate, cmp, "nombre='"+ntarea+"'", null, null, null, null);
                                    int hora =0;
                                    int minn= 0;
                                    if (ccc.moveToFirst()) {
                                        //Recorremos el cursor hasta que no haya más registros
                                        do {
                                            hora=ccc.getInt(0);
                                            minn = ccc.getInt(1);


                                        } while (ccc.moveToNext());
                                    }

                                    String[] parts = sdate.replace("'", "").split("-");
                                    Calendar myAlarmDate = Calendar.getInstance();
                                    myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                                    Calendar cal = Calendar.getInstance();
                                    //int me =1;
                                    int monthInt = 1;
                                    try {
                                        cal.setTime(new SimpleDateFormat("MMM").parse(parts[1]));
                                        monthInt = cal.get(Calendar.MONTH) + 1;
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    myAlarmDate.set(Integer.parseInt(parts[2]), monthInt, Integer.parseInt(parts[0]), hora, minn, 0);
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                    Intent _myIntent = new Intent(ActivityPrueba.this, MyReceiver.class);
                                    _myIntent.setAction("com.tareasto.rob.tareasto.mybroadcast");
                                    _myIntent.putExtra("nombre",ntarea);
                                    _myIntent.putExtra("fecha",sdate.replace("'", ""));
                                    _myIntent.putExtra("horas",hora);
                                    _myIntent.putExtra("minutos",minn);
                                    _myIntent.putExtra("id",ial);
                                    PendingIntent _myPendingIntent = PendingIntent.getBroadcast(ActivityPrueba.this, ial, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);
                                    ccc.close();

                                } else {
                                    ial = 0;
                                    String[] cmp = new String[]{"alarma","horas","minutos"};
                                    Cursor ccc=dba.query(sdate, cmp, "nombre='"+ntarea+"'", null, null, null, null);
                                    int hora =0;
                                    int minn= 0;
                                    if (ccc.moveToFirst()) {
                                        //Recorremos el cursor hasta que no haya más registros
                                        do {
                                            //String fechas= c2.getString(0);
                                            Integer nnn = ccc.getInt(0);
                                            hora =ccc.getInt(1);
                                            minn = ccc.getInt(2);
                                            if (nnn > 0) {
                                                dba.delete("Ids", "id="+nnn, null);
                                            }
                                            ial=nnn;
                                        } while (ccc.moveToNext());
                                    }


                                    ccc.close();
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                    Intent _myIntent = new Intent(ActivityPrueba.this, MyReceiver.class);
                                    _myIntent.setAction("com.tareasto.rob.tareasto.mybroadcast");
                                    _myIntent.putExtra("fecha",sdate.replace("'", ""));
                                    _myIntent.putExtra("horas",hora);
                                    _myIntent.putExtra("minutos",minn);
                                    _myIntent.putExtra("id",ial);
                                    PendingIntent _myPendingIntent = PendingIntent.getBroadcast(ActivityPrueba.this, ial, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    //alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);
                                    alarmManager.cancel(_myPendingIntent);
                                }
                                //dba.insert(sdate, null, nuevoRegistroa);


                                nuevoRegistroa.put("alarma",ial );
                                dba.update(sdate, nuevoRegistroa, "nombre='"+ntarea+"'",null );
                                dba.close();

                                Toast.makeText(getApplicationContext(), "Alarma Ajustada", Toast.LENGTH_SHORT).show();
                            }



                        });
                    }
                    CheckBox ch = (CheckBox) view.findViewById(R.id.checkBox);
                    if (ch != null) {
                        ch.setChecked((((Lista_entrada) entrada).get_listo()) == 1);
                        ch.setTag(((Lista_entrada) entrada).get_textoEncima());

                        ch.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(), "Ajustando Alarma", Toast.LENGTH_SHORT).show();

                                IndiceSQLiteHelper indbhb =
                                        new IndiceSQLiteHelper(ActivityPrueba.this, "DBTareas", null, 1);

                                SQLiteDatabase dbb = indbhb.getWritableDatabase();
                                ContentValues nuevoRegistrob = new ContentValues();
                                ntarea= v.getTag().toString();
                                String estado;
                                if (((CheckBox)v).isChecked()){
                                    nuevoRegistrob.put("listo",1 );
                                    nuevoRegistrob.put("alarma",0 );
                                    estado ="Tarea Finalizada";
                                    //Integer nnn;
                                    String[] cmp = new String[]{"alarma","horas","minutos"};
                                    Cursor ccc=dbb.query(sdate, cmp, "nombre='"+ntarea+"'", null, null, null, null);
                                    if (ccc.moveToFirst()) {
                                        //Recorremos el cursor hasta que no haya más registros
                                        do {
                                            //String fechas= c2.getString(0);
                                            Integer nnn = ccc.getInt(0);
                                            if (nnn > 0) {
                                                dbb.delete("Ids", "id=" + nnn, null);
                                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                                Intent _myIntent = new Intent(ActivityPrueba.this, MyReceiver.class);
                                                _myIntent.setAction("com.tareasto.rob.tareasto.mybroadcast");
                                                _myIntent.putExtra("fecha",sdate.replace("'", ""));
                                                _myIntent.putExtra("horas",ccc.getInt(1));
                                                _myIntent.putExtra("minutos",ccc.getInt(2));
                                                _myIntent.putExtra("id",nnn);
                                                PendingIntent _myPendingIntent = PendingIntent.getBroadcast(ActivityPrueba.this, nnn, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                //alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);
                                                alarmManager.cancel(_myPendingIntent);
                                            }

                                        } while (ccc.moveToNext());
                                    }
                                    ccc.close();


                                }
                                else{
                                    nuevoRegistrob.put("listo",0 );
                                    estado ="Tarea NO Finalizada";
                                }
                                //dbb.insert(sdate, null, nuevoRegistrob);
                                dbb.update(sdate, nuevoRegistrob, "nombre='"+ntarea+"'",null );
                                dbb.close();
                                Toast.makeText(getApplicationContext(), estado, Toast.LENGTH_SHORT).show();
                            }



                        });
                    }

                    }


                    ImageButton clo = (ImageButton)view.findViewById(R.id.imageButtondel);
                    if (clo != null) {
                        if (!(entrada == null)) {
                            clo.setTag(((Lista_entrada) entrada).get_textoEncima());
                        }
                        clo.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityPrueba.this);

                                // Setting Dialog Title
                                alertDialog.setTitle("Confirmación");
                                ntarea= v.getTag().toString();
                                // Setting Dialog Message
                                alertDialog.setMessage("¿Desea eliminar la tarea '"+ntarea+"'?");


                                // Setting Icon to Dialog
                                //alertDialog.setIcon(R.drawable.ic_menu_close_clear_cancel);

                                // Setting Positive "Yes" Button
                                alertDialog.setPositiveButton("SI",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int which) {
                                                // Write your code here to execute after dialog
                                                Toast.makeText(getApplicationContext(), "Elimindando Tarea", Toast.LENGTH_SHORT).show();
                                                // Jugar con la base de datos
                                                IndiceSQLiteHelper indbh =
                                                        new IndiceSQLiteHelper(ActivityPrueba.this, "DBTareas", null, 1);

                                                SQLiteDatabase db = indbh.getWritableDatabase();

                                                String[] cmp = new String[]{"alarma","horas","minutos"};
                                                Cursor ccc=db.query(sdate, cmp, "nombre='"+ntarea+"'", null, null, null, null);
                                                if (ccc.moveToFirst()) {
                                                    //Recorremos el cursor hasta que no haya más registros
                                                    do {
                                                        //String fechas= c2.getString(0);
                                                        Integer nnn = ccc.getInt(0);
                                                        if (nnn > 0) {
                                                            db.delete("Ids", "id=" + nnn, null);
                                                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                                                            Intent _myIntent = new Intent(ActivityPrueba.this, MyReceiver.class);
                                                            _myIntent.setAction("com.tareasto.rob.tareasto.mybroadcast");
                                                            _myIntent.putExtra("fecha",sdate.replace("'", ""));
                                                            _myIntent.putExtra("horas",ccc.getInt(1));
                                                            _myIntent.putExtra("minutos",ccc.getInt(2));
                                                            _myIntent.putExtra("id",nnn);
                                                            PendingIntent _myPendingIntent = PendingIntent.getBroadcast(ActivityPrueba.this, nnn, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                            //alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);
                                                            alarmManager.cancel(_myPendingIntent);
                                                        }

                                                    } while (ccc.moveToNext());
                                                }
                                                ccc.close();




                                                db.delete(sdate, "nombre='"+ntarea+"'",null );
                                                String[] args = new String[] {sdate.replace("'", "")};
                                                String[] campos = new String[] {"items"};
                                                Cursor c2 = db.query("Indice", campos, "fecha=?", args, null, null, null);
                                                //Nos aseguramos de que existe al menos un registro
                                                if (c2.moveToFirst()) {
                                                    //Recorremos el cursor hasta que no haya más registros
                                                    do {
                                                        //String fechas= c2.getString(0);
                                                        Integer num = c2.getInt(0);
                                                        if (num>1) {
                                                            num = num - 1;

                                                            ContentValues valores = new ContentValues();
                                                            valores.put("fecha", sdate.replace("'", ""));
                                                            String[] args2 = new String[]{String.valueOf(num)};

                                                            //Actualizamos el registro en la base de datos
                                                            db.update("Indice", valores, "items=?", args2);
                                                        }
                                                        else{
                                                            db.delete("Indice", "fecha="+sdate,null );
                                                            db.execSQL("DROP TABLE IF EXISTS " +sdate);

                                                        }
                                                    } while(c2.moveToNext());
                                                }
                                                c2.close();


                                                db.close();
                                                startActivity(starterIntent);
                                                finish();
                                            }
                                        });
                                // Setting Negative "NO" Button
                                alertDialog.setNegativeButton("NO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,	int which) {
                                                // Write your code here to execute after dialog
                                                Toast.makeText(getApplicationContext(), "Nada que hacer", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }
                                        });

                                // Showing Alert Message
                                alertDialog.show();
                            }

                        });
                    }

                }

        });


    }


}


