package com.example.testbarange;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {
    private DatabaseReference database;

    private Button btUpdate;
    private EditText etKode;
    private EditText etNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etKode = (EditText) findViewById(R.id.editNo);
        etNama = (EditText) findViewById(R.id.editNama);
        btUpdate = (Button) findViewById(R.id.btnUp);

        database =FirebaseDatabase.getInstance().getReference();

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(etKode.getText().toString().isEmpty()) &&
                        !(etNama.getText().toString().isEmpty()))
                    UpdateBrg(new Barang(etKode.getText().toString(),
                            etNama.getText().toString()));
                else
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etKode.getWindowToken(), 0);
            }
        });
    }
    public void UpdateBrg(Barang brg){
        database.child("Barang").push().setValue(brg).addOnSuccessListener(this,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etKode.setText("");
                        etNama.setText("");
                        Toast.makeText(getApplicationContext(),"Data berhasil di Update",
                                Toast.LENGTH_LONG).show();

                    }
                });
    }
    public static Intent getActIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, TambahData.class);
    }
}
