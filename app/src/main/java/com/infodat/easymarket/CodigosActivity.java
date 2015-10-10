package com.infodat.easymarket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.infodat.easymarket.db.datasources.CodigosDataSource;
import com.infodat.easymarket.db.entities.Codigo;
import com.infodat.zbarscannerexample.R;

import java.util.ArrayList;

/*
Esta es el activity que se ejecuta luego de iniciar sesion. Es utilizado
para administrar la lista de los productos escaneados.
*/
public class CodigosActivity extends ActionBarActivity implements View.OnClickListener {

    private Button bAgregar; // Botton de agregar un nuevo codigo
    private ListView lProductos; // Lista de los productos.
    private CodigosDataSource codigosDataSource;
    static final int SCANNING_CODE_REQUEST = 999;

    ListScanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_list);

        // Inicializamos todas las variables que vamos a utilizar
        // como los botones y las listas.
        bAgregar = (Button) findViewById(R.id.bAgregar);
        lProductos = (ListView) findViewById(R.id.lProductos);
        bAgregar.setOnClickListener(this);

        // Creamos un objecto para poder manipular los codigos
        // salvados en la base de datos.
        codigosDataSource = new CodigosDataSource(this);
        codigosDataSource.open();

        adapter = new ListScanAdapter(this, codigosDataSource);
        lProductos.setAdapter(adapter);

        actualizarLista();
    }

    private void actualizarLista() {
        ArrayList<Codigo> codigos = codigosDataSource.getCodigos();
        adapter.add(codigos);
    }

    @Override
    public void onClick(View view) {
        // Cuando hacemos click al boton de agregar, ejecutamos la pantalla/activity
        // ScannerActivity el cual nos ofrece la capacidad de tomar un codigo.
        if (view.getId() == R.id.bAgregar) {
            Intent scanningImage = new Intent(this, ScannerActivity.class);
            startActivityForResult(scanningImage, SCANNING_CODE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Cuando escaneamos un codigo desde el Activity de escaneo, el resultado
        // es enviado aca y lo tomamos desde la variable "data".
        // Luego ese valos es agregado en el adapter, el cual es el manejador de la lista.
        if (requestCode == SCANNING_CODE_REQUEST){
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("scannedCode");
                codigosDataSource.crearCodigo(result);
                actualizarLista();
            }
        }
    }
}
