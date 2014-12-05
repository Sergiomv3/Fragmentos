package com.example.sergio.fragment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2dam on 25/11/2014.
 */
public class Adaptador extends ArrayAdapter<Inmueble> {
    private static LayoutInflater i;
    private int recurso;
    private List<Inmueble> lista;
    private TextView tipo;
    private TextView direccion;
    private TextView precio;

    public Adaptador(Context context, int resource, List<Inmueble> objects) {
        super(context,resource,objects);

        this.recurso = resource;
        this.lista = objects;
        this.i = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            convertView = i.inflate(recurso,null);

        tipo = (TextView) convertView.findViewById(R.id.tipo);
        direccion = (TextView) convertView.findViewById(R.id.direccion);
        precio = (TextView) convertView.findViewById(R.id.precio);
        tipo.setText(lista.get(position).getTipo());
        direccion.setText(lista.get(position).getDireccion());
        precio.setText(lista.get(position).getPrecio());


        return convertView;

    }
}
