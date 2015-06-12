package com.example.lauravelasquezcano.el_corral;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class Puntos_de_venta extends Fragment implements View.OnClickListener{

    private DataBaseManager manager;
    private ListView lista;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private EditText ednombre;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_puntos_de_venta, container, false);

        manager=new DataBaseManager(getActivity());
        lista=(ListView)view.findViewById(android.R.id.list);
        ednombre=(EditText) view.findViewById(R.id.e_nombre);

        String[] from = new String[]{manager.CN_NAME,manager.CN_LATITUD,manager.CN_LONGITUD};
        int[] to = new int[]{R.id.t_nombre,R.id.t_latitud,R.id.t_longitud};
        cursor = manager.cargarCursorSedes();
        adapter = new SimpleCursorAdapter(getActivity(),R.layout.items,cursor,from,to,0);
        lista.setAdapter(adapter);

        Button boton_buscar=(Button) view.findViewById(R.id.b_buscar);
        boton_buscar.setOnClickListener(this);
        Button boton_cargar=(Button) view.findViewById(R.id.b_cargar);
        boton_cargar.setOnClickListener(this);
        Button boton_almacenar=(Button) view.findViewById(R.id.b_almacenar);
        boton_almacenar.setOnClickListener(this);
        Button boton_eliminar=(Button) view.findViewById(R.id.b_eliminar);
        boton_eliminar.setOnClickListener(this);
        Button boton_actualizar=(Button) view.findViewById(R.id.b_actualizar);
        boton_actualizar.setOnClickListener(this);

        return view;
    }



    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.b_buscar){
            new BuscarTask().execute();
        }

        if(v.getId()==R.id.b_cargar){
            lista=(ListView) view.findViewById(android.R.id.list);
            ednombre=(EditText) view.findViewById(R.id.e_nombre);

            String[] from = new String[]{manager.CN_NAME,manager.CN_LATITUD,manager.CN_LONGITUD};
            int[] to = new int[]{R.id.t_nombre,R.id.t_latitud,R.id.t_longitud};
            cursor = manager.cargarCursorSedes();
            adapter = new SimpleCursorAdapter(getActivity(),R.layout.items,cursor,from,to,0);
            lista.setAdapter(adapter);
        }

        if(v.getId()==R.id.b_almacenar){
            EditText nombre=(EditText) view.findViewById(R.id.e_nombre);
            EditText latitud=(EditText) view.findViewById(R.id.e_latitud);
            EditText longitud=(EditText) view.findViewById(R.id.e_longitud);

            manager.insertar(nombre.getText().toString(),latitud.getText().toString(),longitud.getText().toString());
            nombre.setText("");
            latitud.setText("");
            longitud.setText("");
            Toast.makeText(getActivity(),"Insertado",Toast.LENGTH_SHORT).show();
        }

        if(v.getId()==R.id.b_eliminar){
            EditText nombre=(EditText) view.findViewById(R.id.e_nombre);
            manager.eliminar(nombre.getText().toString());
            Toast.makeText(getActivity(),"Eliminado",Toast.LENGTH_SHORT).show();
            nombre.setText("");
        }

        if (v.getId()==R.id.b_actualizar){
            EditText nombre=(EditText) view.findViewById(R.id.e_nombre);
            EditText latitud=(EditText) view.findViewById(R.id.e_latitud);
            EditText longitud=(EditText) view.findViewById(R.id.e_longitud);

            manager.modificar_sede(nombre.getText().toString(),latitud.getText().toString(),longitud.getText().toString());
            Toast.makeText(getActivity(),"Actualizado",Toast.LENGTH_SHORT).show();
            nombre.setText("");
            latitud.setText("");
            longitud.setText("");
        }
    }

    private class BuscarTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(),"Buscando...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            cursor=manager.buscarSede(ednombre.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(),"Finalizado",Toast.LENGTH_SHORT).show();
            adapter.changeCursor(cursor);
            //obtener();
        }
    }

   /* public void obtener(){
        TextView buscar_nombre= (TextView) view.findViewById(R.id.t_buscar_nombre);
        TextView buscar_latitud=(TextView) view.findViewById(R.id.t_buscar_latitud);
        TextView buscar_longitud=(TextView) view.findViewById(R.id.t_buscar_longitud);

        try{
            String dbnombre=cursor.getString(cursor.getColumnIndex(manager.CN_NAME));
            //buscar_nombre.setText(dbnombre);
            String dblatitud=cursor.getString(cursor.getColumnIndex(manager.CN_LATITUD));
            //buscar_latitud.setText(dblatitud);
            String dblongitud=cursor.getString(cursor.getColumnIndex(manager.CN_LONGITUD));
           // buscar_longitud.setText(dblongitud);

        }catch (CursorIndexOutOfBoundsException e){
           /* buscar_nombre.setText("Not found");
            buscar_latitud.setText("Not found");
            buscar_longitud.setText("Not found");
            Toast.makeText(getActivity(),"No encontrado",Toast.LENGTH_SHORT).show();
        }
    }*/
}
