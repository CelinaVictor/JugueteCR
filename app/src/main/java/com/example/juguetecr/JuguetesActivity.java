package com.example.juguetecr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juguetecr.models.Juguete;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JuguetesActivity extends Activity {

    DatabaseReference JugueteriaRef;
   // String coleccionJugueteria = "juguetes";
    StorageReference storage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageButton borrar, guardar, modif;
    Button foto, mtr;
    EditText nombre, edad, descp, material, precio;
    ListView lista;

    private Typeface Pacific;
    private TextView titulo;
    ImageView imagen;
    ProgressDialog progress;

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            progress.setMessage("Subiendo Imagen");
            progress.show();

            Uri uri = data.getData();

            StorageReference filepath = storage.child("Juguetes").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(JuguetesActivity.this).load(downloadUri).fit().centerCrop().into (imagen);

                    Toast.makeText(JuguetesActivity.this, "Foto Subida...", Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juguetes);

        guardar = (ImageButton) findViewById(R.id.imageGrabar);
        borrar = (ImageButton) findViewById(R.id.imageDelete);
        modif = (ImageButton) findViewById(R.id.imageModif);

        nombre = (EditText) findViewById(R.id.edtNombre);
        edad = (EditText) findViewById(R.id.edtEdad);
        descp = (EditText) findViewById(R.id.edtDescp);
        material = (EditText) findViewById(R.id.edtMaterial);
        precio = (EditText) findViewById(R.id.edtPrecio);

        progress = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance().getReference();

        imagen =  findViewById(R.id.ivimagen);
        foto = findViewById(R.id.btnfoto);

        String fuente1 = "fuente/Pacific.ttf";
        this.Pacific = Typeface.createFromAsset(getAssets(), fuente1);
        titulo = (TextView) findViewById(R.id.textView);
        titulo.setTypeface(Pacific);

        mtr = (Button)findViewById(R.id.btmostrar);
        mtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enombre = nombre.getText().toString();
                Query q = JugueteriaRef.orderByChild(getString(R.string.campo_nombre)).equalTo(enombre);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int cont = 0;
                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            cont++;
                            Toast.makeText(JuguetesActivity.this, "He encontrado " + cont, Toast.LENGTH_LONG).show();
            }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        JugueteriaRef = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_juguete));
        JugueteriaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Juguete juguete = datasnapshot.getValue(Juguete.class);

                    String nombre = juguete.getNombre();
                    String edad = juguete.getEdad();
                    String descrp = juguete.getDescripcion();
                    String materia = juguete.getMaterial();
                    String precio = juguete.getPrecio();
                    listado.add(nombre);
                    listado.add(edad);
                    listado.add(descrp);
                    listado.add(materia);
                    listado.add(precio);

                } adaptador = new ArrayAdapter<String>(JuguetesActivity.this, android.R.layout.simple_list_item_1, listado);
                lista.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String enombre = nombre.getText().toString();
                String eedad = edad.getText().toString();
                String ede = descp.getText().toString();
                String emat = material.getText().toString();
                String epre = precio.getText().toString();

                if (!TextUtils.isEmpty(enombre)) {
                    if (!TextUtils.isEmpty(eedad)) {
                        if (!TextUtils.isEmpty(ede)) {
                            if (!TextUtils.isEmpty(emat)) {
                                if (!TextUtils.isEmpty(epre)) {

                                    Juguete j = new Juguete(enombre, eedad, ede, emat, epre);
                                    String clave = JugueteriaRef.push().getKey();

                                    JugueteriaRef.child(clave).setValue(j);
                                    Toast.makeText(JuguetesActivity.this, "Juguete añadido", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(JuguetesActivity.this, "Debes de introducir un precio", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(JuguetesActivity.this, "Debes de introducir el Tipo de Material", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(JuguetesActivity.this, "Debes de introducir una Descripcion", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(JuguetesActivity.this, "Debes de introducir una edad", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(JuguetesActivity.this, "Debes de introducir un nombre", Toast.LENGTH_LONG).show();
                }
            }
        });

                modif.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        String enombre = nombre.getText().toString();

                        if (!TextUtils.isEmpty(enombre)) {
                            Query q = JugueteriaRef.orderByChild(getString(R.string.campo_nombre)).equalTo(enombre);
                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                        String clave = datasnapshot.getKey();
                                        JugueteriaRef.child(clave).child(getString(R.string.campo_edad)).setValue(edad.getText().toString());
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(JuguetesActivity.this, "La edad del juguete " + enombre + " se ha modificado con éxito", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(JuguetesActivity.this, "Debes de introducir un nombre", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        borrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String enombre = nombre.getText().toString();

                if (!TextUtils.isEmpty(enombre)) {

                    Query q = JugueteriaRef.orderByChild(getString(R.string.campo_nombre)).equalTo(enombre);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                String clave = datasnapshot.getKey();
                                DatabaseReference ref = JugueteriaRef.child(clave);

                                ref.removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(JuguetesActivity.this, "El Juguete " + enombre + " se ha borrado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(JuguetesActivity.this, "Debes de introducir un nombre", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}