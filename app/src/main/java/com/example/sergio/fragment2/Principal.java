package com.example.sergio.fragment2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class Principal extends ActionBarActivity {
    boolean horizontal = false;
    private ArrayList<Inmueble> al;
    private Adaptador ad;
    private Inmueble constructor;
    private EditText etT;
    private EditText etD;
    private EditText etP;
    private ListView lv;
    private int indiceA;
    private final int IDACTIVIDADFOTO=1;
    private int lastIndice;
    private ImageView iv;
    private ImageView iv2;
    private ImageView iv3;
    private int maxImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        /*ArrayList<String> l =new ArrayList<String>();
        l.add("Pepe");
        l.add("Juan");
        l.add("Maria");
        l.add("Ana");
        l.add("Nerea");
        */
        al = new ArrayList<Inmueble>();
        al.add(constructor = new Inmueble(1,"Casa", "Calle Placa Base, 12", "14500" + " €"));
        lv = (ListView) findViewById(R.id.listView);
        ad = new Adaptador(this, R.layout.detalle, al);
        lv.setAdapter(ad);
        final FragmentoDos fdos = (FragmentoDos) getFragmentManager().findFragmentById(R.id.fragmento);
        registerForContextMenu(lv);
        if (fdos != null && fdos.isInLayout()) {//shortcut - cortocircuito
            horizontal = true;
            int v = getResources().getConfiguration().orientation;
            switch (v) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    break;
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Inmueble im = (Inmueble) lv.getItemAtPosition(position);
                Toast.makeText(Principal.this, im.getDireccion(), Toast.LENGTH_SHORT).show();
                if (horizontal) {
                    fdos.setImagen(im.getId());
                } else {
                    Intent intent = new Intent(Principal.this, Secundaria.class);
                    intent.putExtra("id", im.getId());
                    startActivityForResult(intent, ACTIVIDADDOS);
                }
            }
        });
        leer();
    }

    // EVITAR PERDIDAS EN ROTACIONES!!


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("array", al);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        al = (ArrayList<Inmueble>)savedInstanceState.getSerializable("array");
        ad = new Adaptador(this, R.layout.detalle, al);
        lv.setAdapter(ad);
        ad.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == R.id.action_borrar) {
            return del(info);
        } else if (id == R.id.action_edit) {
            return edit(info);

        }
        return super.onContextItemSelected(item);
    }

    public boolean add() {
        maxImages = 0;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.tv_titulodialogo);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogoadd, null);
        iv =(ImageView) vista.findViewById(R.id.imageView2);
        iv2 =(ImageView) vista.findViewById(R.id.imageView3);
        iv3 =(ImageView) vista.findViewById(R.id.imageView4);
        alert.setView(vista);

        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etT = (EditText) vista.findViewById(R.id.etTipo);
                etD = (EditText) vista.findViewById(R.id.etDireccion);
                etP = (EditText) vista.findViewById(R.id.etPrecio);
                if (etD.getText().toString().equals("") || etP.getText().toString().equals("") || etT.getText().toString().equals("")) {
                    Toast.makeText(Principal.this, "Faltan datos", Toast.LENGTH_SHORT).show();

                } else {
                    Inmueble aniadir = new Inmueble(indiceA,etT.getText().toString(), etD.getText().toString(), etP.getText().toString());
                    al.add(aniadir);
                    ad.notifyDataSetChanged();
                    Toast.makeText(Principal.this, "El indice es "+indiceA, Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alert.show();

        return true;

    }

    private final int ACTIVIDADDOS = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            indiceA = 0;
            for (int i = 0; i<al.size();i++){
                if(indiceA<=al.get(i).getId()){
                    indiceA = al.get(i).getId()+1;
                }
            }
            lastIndice = indiceA;
            add();
            return true;
        }else if (id == R.id.action_guardar){
            crear();
        }else if(id==R.id.action_cargar){
            leer();

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean del(AdapterView.AdapterContextMenuInfo info) {
        int indice = info.position;
        int id = al.get(indice).getId();
        File file = new File(getExternalFilesDir(null),"");
        File []archivos = file.listFiles();
        ArrayList<String> direcciones = new ArrayList<String>();
        for (int i = 0; i<archivos.length;i++){
            direcciones.add(archivos[i].getName());
            //System.out.println( archivos[i].getName());
        }

        for(int j = 0; j<direcciones.size(); j++) {
            System.out.println(id);
            char idC =direcciones.get(j).charAt(0);
            System.out.println(idC);
            if(String.valueOf(idC).equals("d")){

            }else if(String.valueOf(idC).equals(String.valueOf(id))){

                    System.out.println(idC+" - "+indice);
                    File fileB = new File(getExternalFilesDir(null) + "/" + direcciones.get(j).toString());
                    fileB.delete();

                } else {
            }
        }
        al.remove(indice);
        ad.notifyDataSetChanged();
        return true;
    }

    public boolean edit(AdapterView.AdapterContextMenuInfo info) {
        final int indice = info.position;
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.tv_titulodialogoEdit);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogoadd, null);
        alert.setView(vista);
        alert.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etT = (EditText) vista.findViewById(R.id.etTipo);
                etD = (EditText) vista.findViewById(R.id.etDireccion);
                etP = (EditText) vista.findViewById(R.id.etPrecio);

                if (etD.getText().toString().equals("") || etP.getText().toString().equals("") || etT.getText().toString().equals("")) {
                    Toast.makeText(Principal.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                } else {
                    al.get(indice).setTipo(etT.getText().toString());
                    al.get(indice).setDireccion(etD.getText().toString());
                    al.get(indice).setPrecio(etP.getText().toString());
                    ad.notifyDataSetChanged();
                }

            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
        return true;
    }
    public void foto(View v){

        Intent i = new Intent ("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, IDACTIVIDADFOTO);
    }
    @Override
    public void onActivityResult(int pet, int res, Intent i) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);
        if (res == RESULT_OK && pet == IDACTIVIDADFOTO) {
            if(maxImages<4) {
                maxImages++;
                Bitmap foto = (Bitmap) i.getExtras().get("data");
                FileOutputStream salida;
                try {
                    salida = new FileOutputStream(getExternalFilesDir(null) + "/" + lastIndice + "_inmueble_" + hora + "_" + minutos + "_" + segundos + ".jpg");
                    foto.compress(Bitmap.CompressFormat.JPEG, 90, salida);
                } catch (FileNotFoundException e) {
                }
            }
            if(maxImages == 1) {
                File imgFile = new File(getExternalFilesDir(null) + "/" + lastIndice + "_inmueble_" + hora + "_" + minutos + "_" + segundos + ".jpg");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv.setImageBitmap(myBitmap);
            }else if(maxImages == 2){
                File imgFile = new File(getExternalFilesDir(null) + "/" + lastIndice + "_inmueble_" + hora + "_" + minutos + "_" + segundos + ".jpg");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv2.setImageBitmap(myBitmap);
            }else if(maxImages ==3){
                File imgFile = new File(getExternalFilesDir(null) + "/" + lastIndice + "_inmueble_" + hora + "_" + minutos + "_" + segundos + ".jpg");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv3.setImageBitmap(myBitmap);
            }else{
                Toast.makeText(Principal.this,"No se permiten mas imágenes", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void crear() {

        try {

            FileOutputStream fosxml = new FileOutputStream(new File(getExternalFilesDir(null),"datos.xml"));
            System.out.println(getExternalFilesDir(null));
            XmlSerializer docxml = Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            docxml.startTag(null, "inmobiliaria");
            for (int i = 0; i < al.size(); i++) {

                    docxml.startTag(null, "inmueble");
                    docxml.attribute(null, "tipo", al.get(i).getTipo());
                    docxml.attribute(null, "id", String.valueOf(al.get(i).getId()));

                    docxml.attribute(null, "precio",al.get(i).getPrecio());
                    docxml.attribute(null, "direccion",al.get(i).getDireccion());

                    docxml.endTag(null, "inmueble");

            }
            docxml.endTag(null, "inmobiliaria");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
            Toast.makeText(Principal.this,"Guardado correctamente",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IllegalStateException e) {
            e.printStackTrace();

        }

    }
    public void leer (){
        int idI = 0;
        for (int i = 0; i<al.size();i++){
            al.remove(i);
        }
        ad.notifyDataSetChanged();

        XmlPullParser lectorxml = Xml.newPullParser();

        try {
            lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null),"datos.xml")),"utf-8");
            int evento = lectorxml.getEventType();
            while (evento != XmlPullParser.END_DOCUMENT){
                if(evento == XmlPullParser.START_TAG){

                    //String etiqueta = lectorxml.getName();

                        String tipo = lectorxml.getAttributeValue(null,"tipo");
                        try {
                            idI = Integer.valueOf(lectorxml.getAttributeValue(null, "id"));
                        }catch (NumberFormatException e){

                        }

                        String precio = lectorxml.getAttributeValue(null,"precio");
                        String direccion =lectorxml.getAttributeValue(null,"direccion");
                        Inmueble inm = new Inmueble(idI,tipo,direccion,precio);
                        if(inm.getId() == 0){

                        }else{
                            al.add(inm);
                        }

                }
                evento = lectorxml.next();
            }
            ad.notifyDataSetChanged();
            Toast.makeText(Principal.this,"Cargado correctamente",Toast.LENGTH_SHORT).show();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}