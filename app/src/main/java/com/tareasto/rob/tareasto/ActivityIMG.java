package com.tareasto.rob.tareasto;

//import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
//import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class ActivityIMG extends ActionBarActivity {

    private Integer uriimg;
    protected ListView lista;
    // private ImageButton imageButton_a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        setTitle("Seleccione imagen");
        uriimg=R.drawable.education_application5;
        lista = (ListView) findViewById(R.id.ListView_listadoi);
        ArrayList<Lista_entradai> datos = new ArrayList<Lista_entradai>();
        // ver los drawables
        Field[] drawables = com.tareasto.rob.tareasto.R.drawable.class.getFields();
        String name;
        int resource;
        //extraerlos los campos
        for (Field f : drawables) {
            try {
                name = f.getName();
                resource = getResources().getIdentifier(name, "drawable", "com.tareasto.rob.tareasto");

                datos.add(new Lista_entradai(resource, name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // sigue lo demas
        lista.setAdapter(new Lista_adaptador(this, R.layout.entradai, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto__entrada = (TextView) view.findViewById(R.id.textViewsel);
                    if (texto__entrada != null)
                        texto__entrada.setText(((Lista_entradai) entrada).get_texto());

                    ImageButton ib_entrada = (ImageButton) view.findViewById(R.id.imageButtonsel);
                    if (ib_entrada != null) {
                        ib_entrada.setImageResource(((Lista_entradai) entrada).get_idImagen());
                        ib_entrada.setTag(((Lista_entradai) entrada).get_texto());
                        ib_entrada.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //ImageButton imageButton_a = (ImageButton) v.findViewById(R.id.imageButtonsel);
                                String imageName = v.getTag().toString();
                                uriimg = getResources().getIdentifier(imageName, "drawable", "com.tareasto.rob.tareasto");
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result",uriimg);
                                setResult(RESULT_OK,returnIntent);
                                finish();
                            }

                        });

                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_img, menu);
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

}
