package com.infodat.easymarket.db.datasources;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.infodat.easymarket.db.entities.Codigo;
import com.infodat.easymarket.helpers.SQLiteHelper;

/**
 * Este es el dataSource (Fuente de datos) para manipular los registros
 * de la tabla de codigos. Basicamente este se utilizara para buscar/borrar/agregar
 * codigos a la base de datos. En pocas palabras, este es como un asistente.
 */
public class CodigosDataSource {

    // Aca definimos los diferentes campos de la base de datos y los helper (SQLiteHelper).
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] columnas = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_CODIGO };

    public CodigosDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Funcion para salvar un codigo a la base de datos.
     * Este se encarga de tomar el objecto para luego crear el insert.
     * */
    public Codigo crearCodigo(String codigo) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_CODIGO, codigo);
        long insertId = database.insert(SQLiteHelper.TABLE_CODIGOS, null, values);
        Cursor cursor = database.query(
                SQLiteHelper.TABLE_CODIGOS,
                columnas, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        Codigo nuevoCodigo = cursorToCodigo(cursor);
        cursor.close();
        return nuevoCodigo;
    }

    public void borrarCodigo(Codigo codigo) {
        long id = codigo.getId();
        System.out.println("Codigo borrado id: " + id);
        database.delete(SQLiteHelper.TABLE_CODIGOS, SQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public ArrayList<Codigo> getCodigos() {
        ArrayList<Codigo> codigos = new ArrayList<Codigo>();

        /**
         * Aca se genera el query para buscar los registros a la base de datos.
         * */
        Cursor cursor = database.query(SQLiteHelper.TABLE_CODIGOS,
                columnas, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Codigo codigo = cursorToCodigo(cursor);
            codigos.add(codigo);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return codigos;
    }

    /**
     * Esta funcion se encarga de tomar un cursor y convertirlo a un objecto Codigo.
     * Normalmente el cursor es un registro con dos valores sin identificacion. Por
     * esa razon se manda el cursor aca para convertirlo a un objecto Codigo.
     * */
    private Codigo cursorToCodigo(Cursor cursor) {
        Codigo codigo = new Codigo();
        codigo.setId(cursor.getLong(0));
        codigo.setCodigo(cursor.getString(1));
        return codigo;
    }
}