package com.example.ejercicio1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ejercicio1_4.configuracion.SQLiteConexion;
import com.example.ejercicio1_4.transacciones.Fotos;
import com.example.ejercicio1_4.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityListaFotos extends AppCompatActivity {

    //Variables Globales

    SQLiteConexion conexion;
    ListView listView;
    Button btnatras;
    ArrayList<Fotos> listafotos;
    ArrayList<String> ArregloFotos;
    private String Dato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fotos);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null,1);
        listView = (ListView) findViewById(R.id.txtlista);
        btnatras= (Button) findViewById(R.id.verregistros2);
        ObtenerListadoFotos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, ArregloFotos);
        listView.setAdapter(adp);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //BOTON DE MOSTRAR Imagen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Dato = "" +listafotos.get(position).getId();
                try {
                    Intent intent = new Intent(getApplicationContext(), ActivityMostrarFoto.class);
                    startActivity(intent);
                    intent.putExtra("codigo", Dato + "");
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Intent intent = new Intent(getApplicationContext(), ActivityMostrarFoto.class);
                    intent.putExtra("codigo", "1");
                    startActivity(intent);
                }

            }

        });
        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }


        });



    }

    private void ObtenerListadoFotos() {
        SQLiteDatabase bsd = conexion.getWritableDatabase();
        Fotos picture = null;
        listafotos = new ArrayList<Fotos>();

        Cursor cursor = bsd.rawQuery("SELECT * FROM pictures", null);
        while(cursor.moveToNext()){
            picture = new Fotos();
            picture.setId(cursor.getInt(0));
            picture.setNombre(cursor.getString(1));
            picture.setDescription(cursor.getString(2));
            listafotos.add(picture);
        }
        cursor.close();
        FillList();
    }

    private void FillList() {
        ArregloFotos = new ArrayList<String>();
        for(int i=0; i<listafotos.size(); i++){
            ArregloFotos.add(listafotos.get(i).getId() + "|" +
                    listafotos.get(i).getNombre() + "|" +
                    listafotos.get(i).getDescription() + "|");

        }
    }
}