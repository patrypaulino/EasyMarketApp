package com.infodat.easymarket.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Este sera el "Helper" el cual se encarga de crear la base de datos
 * cuando se instala la aplicacion por primera vez o cuando se actualiza
 * con una nueva version.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    /**
     * Se define los valores generales como el nombre de la tabla
     * y el nombre de las diferentes columnas que va a tener la tabla.
     * */
    public static final String TABLE_CODIGOS = "codigos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODIGO = "codigo";

    /**
     * Tambien se define el nombre que va a tener la base de datos
     * y ademas, la version de la base de datos.
     * */
    private static final String DATABASE_NAME = "easymarket.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Aca se general el query SQL para crear la tabla.
     * Este es un query comun de todo SQL, similar a este:
     * create table nombre_de_la_tabla (
     *      columna1 integer,
     *      columna2 text
     * )
     * */
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CODIGOS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_CODIGO
            + " text not null);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    /**
     * Este es el metodo el cual se encarga de borrar la vieja base de datos
     * y actualizarla por la nueva cuando se requiera.
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODIGOS);
        onCreate(db);
    }
}
