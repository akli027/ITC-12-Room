package com.example.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynoteapp.database.NoteDao;
import com.example.mynoteapp.database.NoteDatabase;
import com.example.mynoteapp.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteAdd extends AppCompatActivity {

    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 100;

    private EditText eTitle, eText;
    private Button btnAdd;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tambah");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        noteDao = NoteDatabase.getInstance(this).noteDao();
        eTitle = findViewById(R.id.et_Title);
        eText = findViewById(R.id.et_Text);
        btnAdd = findViewById(R.id.btn_add2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = eTitle.getText().toString();
                String text = eText.getText().toString();
                String date = getCurrentDate();

                Note note = new Note(title, text, date);
                noteDao.insertData(note);

                setResult(RESULT_ADD);
                finish();
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showDialogMessage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showDialogMessage();
    }

    //*****************************************************************
    private void showDialogMessage() {
        String message;
        String title;

        title = "Batal";
        message = "Apakah anda ingin membatalkan penambahan note?";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }
}