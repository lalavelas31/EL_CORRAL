package com.example.lauravelasquezcano.el_corral;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Lauris Velasquez on 04/06/2015.
 */
public class DataBaseManager {

    public static final String TABLE_NAME="sedes";
    public static final String CN_ID= "_id";
    public static final String CN_NAME="nombre";
    public static final String CN_LATITUD="latitud";
    public static final String CN_LONGITUD="longitud";

    public static final String CREATE_TABLE= "create table "+TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null,"
            + CN_LATITUD + " text,"
            + CN_LONGITUD + " text);";

    DbHelper helper;
    SQLiteDatabase db;

    public DataBaseManager (Context context){
        helper=new DbHelper(context);
        db=helper.getWritableDatabase();
    }

    public ContentValues generarContentValues (String Nombre, String Latitud, String Longitud){
        ContentValues valores=new ContentValues();
        valores.put(CN_NAME,Nombre);
        valores.put(CN_LATITUD, Latitud);
        valores.put(CN_LONGITUD,Longitud);

        return valores;
    }

    public void insertar (String Nombre, String Latitud, String Longitud){
        db.insert(TABLE_NAME, null, generarContentValues(Nombre, Latitud, Longitud));
    }

    public Cursor cargarCursorSedes(){
        String[] columnas= new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }

    public Cursor buscarSede(String Nombre){
        String[] columnas= new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return db.query(TABLE_NAME,columnas,CN_NAME + "=?", new String[]{Nombre},null,null,null);
    }

    public void eliminar(String nombre){
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void modificar_sede (String Nombre, String NuevaLatitud, String Longitud){
        db.update(TABLE_NAME, generarContentValues(Nombre, NuevaLatitud, Longitud), CN_NAME + "=?", new String[]{Nombre});
    }


}
