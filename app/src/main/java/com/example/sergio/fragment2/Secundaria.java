package com.example.sergio.fragment2;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Secundaria extends ActionBarActivity {
    private ArrayList<Foto> alF;
    private AdaptadorFoto adF;
    private Foto constructor;
    private ListView lvF;
    TextView tv;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        Integer id = getIntent().getExtras().getInt("id");

        FragmentoDos fdos = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment);
        //fdos.setTexto(rutas);
        alF = new ArrayList<Foto>();

        File file = new File(getExternalFilesDir(null),"");
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
                    f.setRuta(getExternalFilesDir(null) + "/" + direcciones.get(i).toString());
                    alF.add(f);
                } else {
                    System.out.println(idC2 + " - " + id);

                }
            }
        }

        lvF = (ListView) findViewById(R.id.listView2);
        adF = new AdaptadorFoto(this, R.layout.detallefoto, alF);
        lvF.setAdapter(adF);
        registerForContextMenu(lvF);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual2, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secundaria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_borrar) {
            borrarfoto(info);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void borrarfoto(AdapterView.AdapterContextMenuInfo info){
        int identi = info.position;
        File file = new File(alF.get(identi).getRuta());
        file.delete();
        alF.remove(identi);
        Toast.makeText(this, "Borrada", Toast.LENGTH_SHORT).show();
    }
}
