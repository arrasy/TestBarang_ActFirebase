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

public class Delete extends AppCompatActivity {
    private DatabaseReference database;

    private Button btDelete;
    private EditText etKode;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        etKode = (EditText) findViewById(R.id.editNo);
        etName = (EditText) findViewById(R.id.editName);
        btDelete = (Button) findViewById(R.id.btnDel);

        database =FirebaseDatabase.getInstance().getReference();

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(etKode.getText().toString().isEmpty()) &&
                        !(etName.getText().toString().isEmpty()))
                    onDeleteData(new Barang(etKode.getText().toString(),
                            etName.getText().toString()));
                else
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etKode.getWindowToken(), 0);
            }
        });
    }

    public void onDeleteData(Barang brg) {
        database.child("Barang").removeValue().addOnSuccessListener(this,
                new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            etKode.setText("");
                            etName.setText("");
                            Toast.makeText(Delete.this, "Data Berhasil Dihapus",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
    }
    public static Intent getActIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, TambahData.class);
    }
}
