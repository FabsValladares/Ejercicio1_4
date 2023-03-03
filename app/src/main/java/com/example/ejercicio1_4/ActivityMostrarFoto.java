package com.example.ejercicio1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ejercicio1_4.configuracion.SQLiteConexion;
import com.example.ejercicio1_4.transacciones.Transacciones;

import java.io.ByteArrayInputStream;

public class ActivityMostrarFoto extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

    ImageView picture;

    Button regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_foto);
        picture = (ImageView) findViewById(R.id.imageView2);
        regresar = (Button)   findViewById(R.id.verregistros3);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityListaFotos.class);
                startActivity(intent);
            }


        });
        Bitmap retornarFoto = buscarPicture(getIntent().getStringExtra("codigo"));
        picture.setImageBitmap(retornarFoto);
    }
    public Bitmap buscarPicture(String id) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String sql = "SELECT imagen FROM pictures WHERE id ="+ id;
        Cursor cursor = db.rawQuery(sql, new String[] {});
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}