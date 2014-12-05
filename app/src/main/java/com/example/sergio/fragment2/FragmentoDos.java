package com.example.sergio.fragment2;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoDos extends Fragment {
    private ArrayList<Foto> alF;
    private AdaptadorFoto adF;
    private Foto constructor;
    private ListView lvF;
    private View v;
    private TextView tv;
    public FragmentoDos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_fragmento_dos, container, false);

        return v;
    }

    public void setImagen(int idI){

        tv = (TextView)v.findViewById(R.id.tv2);
        Integer id = idI;

        FragmentoDos fdos = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment);
        //fdos.setTexto(rutas);
        alF = new ArrayList<Foto>();

        File file = new File( getActivity().getExternalFilesDir(null),"");
        File []archivos = file.listFiles();
        ArrayList<String> direcciones = new ArrayList<String>();
        for (int i = 0; i<archivos.length;i++){
            direcciones.add(archivos[i].getName());
            //System.out.println( archivos[i].getName());
        }

        for(int i = 0; i<direcciones.size(); i++) {
            char idC =direcciones.get(i).charAt(0);
            if(String.valueOf(idC).equals("d")){

            }else {
                int idC2 = Integer.parseInt(String.valueOf(idC));
                if (idC2 == id) {
                    System.out.println("pasa por aqui");
                    Foto f = new Foto();
                    f.setRuta(getActivity().getExternalFilesDir(null) + "/" + direcciones.get(i).toString());
                    alF.add(f);
                } else {
                    System.out.println(idC2 + " - " + id);

                }
            }
        }

        lvF = (ListView) getView().findViewById(R.id.listView2);
        adF = new AdaptadorFoto(getView().getContext(), R.layout.detallefoto, alF);
        lvF.setAdapter(adF);
        registerForContextMenu(lvF);
    }


}
