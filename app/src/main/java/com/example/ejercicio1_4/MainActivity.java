package com.example.ejercicio1_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ejercicio1_4.configuracion.SQLiteConexion;
import com.example.ejercicio1_4.transacciones.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

    EditText nombre, descripcion;
    ImageView picture;
    Bitmap image;
    Button tomarfoto, guardar, verregistros;
    String currentPhotoPath;
    static final int REQUEST_IMAGE = 101;
    static final int PETICION_ACCESS_CAM = 201;

    SQLiteDatabase bsd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.txtnombre);
        descripcion = (EditText) findViewById(R.id.txtdescripcion);
        picture = (ImageView) findViewById(R.id.imageView);
        tomarfoto = (Button) findViewById(R.id.tomarfoto);
        guardar = (Button) findViewById(R.id.guardar);
        verregistros = (Button) findViewById(R.id.verregistros);

        tomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validacion();
            }
        });

    }

    private void validacion() {
        if (nombre.getText().toString().equals(Transacciones.Empty)){
            Toast.makeText(getApplicationContext(), "Debe de escribir un nombre" ,Toast.LENGTH_LONG).show();
        }else if (descripcion.getText().toString().equals(Transacciones.Empty)){
            Toast.makeText(getApplicationContext(), "Debe de escribir un telefono" ,Toast.LENGTH_LONG).show();
        }else {
            GuardarFoto(image);
        }

    }

    private void GuardarFoto(Bitmap image) {
     try{
         conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
         bsd = conexion.getWritableDatabase();
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
         byte[] ArrayImagen  = stream.toByteArray();

         ContentValues valores = new ContentValues();

         valores.put(Transacciones.Nombre, nombre.getText().toString());
         valores.put(Transacciones.Description, descripcion.getText().toString());
         valores.put(String.valueOf(Transacciones.imagen),ArrayImagen);

         Long resultado = bsd.insert(Transacciones.tablafoto, Transacciones.id, valores);

         Toast.makeText(getApplicationContext(), "Registro ingreso con exito, Codigo " + resultado.toString()
                 ,Toast.LENGTH_LONG).show();
         bsd.close();
     }catch (Exception ex){
         Toast.makeText(getApplicationContext(),"Se produjo un error",Toast.LENGTH_LONG).show();
     }
    }

    private void permisos() {
        // Metodo para obtener los permisos requeridos de la aplicacion
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESS_CAM);
        } else {
            dispatchTakePictureIntent();

        }

        ClearScreen();
    }

    private void ClearScreen() {
        nombre.setText("");
        descripcion.setText("");

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.toString();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pm2e100970094.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {

            try {
                File foto = new File(currentPhotoPath);
                image = BitmapFactory.decodeFile(foto.getAbsolutePath());
                picture.setImageURI(Uri.fromFile(foto));
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PETICION_ACCESS_CAM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();

            } else {
                Toast.makeText(getApplicationContext(), "se necesita el permiso de la camara", Toast.LENGTH_LONG).show();
            }
        }
    }
}