package com.infodat.easymarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.infodat.easymarket.db.datasources.CodigosDataSource;
import com.infodat.easymarket.db.entities.Codigo;
import com.infodat.zbarscannerexample.R;

import java.util.ArrayList;

public class ListScanAdapter extends BaseAdapter {
    public Context context;
    private CodigosDataSource dataSource;
    public ListScanAdapter(Context context, CodigosDataSource dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }
    public ArrayList<Codigo> productos = new ArrayList<Codigo>();
    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Codigo getItem(int i) {
        return productos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void add(Codigo producto){
        productos.add(producto);
        this.notifyDataSetChanged();
    }

    public void add(ArrayList<Codigo> productos){
        this.productos = productos;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View row;
        row = inflater.inflate(R.layout.row_scanned, viewGroup, false);
        TextView title = (TextView) row.findViewById(R.id.rText);
        Button b = (Button) row.findViewById(R.id.bQuitar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Codigo removed = productos.remove(i);
                dataSource.borrarCodigo(removed);
                ListScanAdapter.this.notifyDataSetChanged();
            }
        });
        String string = getItem(i).toString();
        if ( string.length() > 23 ) {
            string = string.substring(0, 20) + "...";
        }
        title.setText(string);

        return row;
    }
}
