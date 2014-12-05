package com.example.sergio.fragment2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 05/12/2014.
 */
public class AdaptadorFoto extends ArrayAdapter<Foto> {
    private static LayoutInflater i;
    private int recurso;
    private ArrayList<Foto> lista;
    private ImageView ivF;


    public AdaptadorFoto(Context context, int resource, ArrayList<Foto> objects) {
    super(context,resource,objects);
    this.recurso = resource;
    this.lista = objects;
    this.i = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = i.inflate(recurso,null);
        ivF= (ImageView) convertView.findViewById(R.id.imageFragment);
        ivF.setImageBitmap(BitmapFactory.decodeFile(new File(lista.get(position).getRuta()).getAbsolutePath()));
        return convertView;

    }
}
