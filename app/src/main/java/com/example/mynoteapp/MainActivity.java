package com.example.mynoteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mynoteapp.adapter.Adapter;
import com.example.mynoteapp.database.NoteDao;
import com.example.mynoteapp.database.NoteDatabase;
import com.example.mynoteapp.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private NoteDao noteDao;
    private RecyclerView notes;
    private ArrayList<Note> listNotes = new ArrayList<>();
    private Adapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDao = NoteDatabase.getInstance(this).noteDao();
        noteAdapter = new Adapter(this);
        notes = findViewById(R.id.note);
        notes.setHasFixedSize(true);
        notes.setLayoutManager(new LinearLayoutManager(this));
        notes.setAdapter(noteAdapter);
        loadData();

        fabAdd = findViewById(R.id.btn_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteAdd.class);
                startActivityForResult(intent, NoteAdd.REQUEST_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NoteAdd.REQUEST_ADD) {
            if (resultCode == NoteAdd.RESULT_ADD) {
                loadData();
                showSnackbar("Data Berhasil Ditambahkan");
            }
        } else if (requestCode == NoteActivity.REQUEST_EDIT) {
            if (resultCode == NoteActivity.RESULT_EDIT) {
                loadData();
                showSnackbar("Data Berhasil Diedit");
            } else if (resultCode == NoteActivity.RESULT_DELETE) {
                loadData();
                showSnackbar("Data Berhasil Dihapus");
            }
        }
    }

    void loadData() {
        List<Note> data = noteDao.getAllData();
        listNotes.clear();
        listNotes.addAll(data);
        noteAdapter.setListNotes(listNotes);
        if (data.size() == 0) {
            showSnackbar("tak ono");
        }
    }

    private void showSnackbar(String messege) {
        Snackbar.make(notes, messege, Snackbar.LENGTH_LONG).show();
    }
}