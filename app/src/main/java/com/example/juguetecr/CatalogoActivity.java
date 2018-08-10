package com.example.juguetecr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juguetecr.models.Juguete;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatalogoActivity extends Activity {

   // DatabaseReference JugueteriaRef;

   // EditText text_nombre;
    //Button mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        //lista = (ListView) findViewById(R.id.listView);
       /// JugueteriaRef = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_juguete));
       /* JugueteriaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

    });

        mostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nombre = text_nombre.getText().toString();
                Query q = JugueteriaRef.orderByChild(getString(R.string.campo_nombre)).equalTo(nombre);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int cont = 0;
                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                            cont++;
                            Toast.makeText(CatalogoActivity.this, "He encontrado " + cont, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });*/
}
}
