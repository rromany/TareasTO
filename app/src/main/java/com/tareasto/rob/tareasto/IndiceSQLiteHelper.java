package com.tareasto.rob.tareasto;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IndiceSQLiteHelper extends SQLiteOpenHelper{


    //Sentencia SQL para crear la tabla de Indice
    private String sqlCreate = "CREATE TABLE Indice (fecha TEXT, items INTEGER)";
    private String sqlCreate2 = "CREATE TABLE Ids (id INTEGER)";

    public IndiceSQLiteHelper(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        Log.i("UpgradeDB", "Misma version");
        if (versionAnterior < versionNueva){
            Log.i("UpgradeDB", "Upgrading database to version " + versionNueva);
            if (versionAnterior == 1 && versionNueva >= 2){
                //actualizar queries
                versionAnterior = 2;
                //Se elimina la versión anterior de la tabla
                db.execSQL("DROP TABLE IF EXISTS Indice");
                db.execSQL("DROP TABLE IF EXISTS Ids");
                //Se crea la nueva versión de la tabla
                db.execSQL(sqlCreate);
                db.execSQL(sqlCreate2);
            }
            if (versionAnterior == 2 && versionNueva >= 3){
                //actualizar  databases queries
                versionAnterior = 3;
                //Se elimina la versión anterior de la tabla
                db.execSQL("DROP TABLE IF EXISTS Indice");
                db.execSQL("DROP TABLE IF EXISTS Ids");
                //Se crea la nueva versión de la tabla
                db.execSQL(sqlCreate);
                db.execSQL(sqlCreate2);
            }
        }
    }

}
