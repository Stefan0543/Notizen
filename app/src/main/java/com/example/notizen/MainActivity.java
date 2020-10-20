package com.example.notizen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_save, btn_delete, btn_edit;
    private EditText edt_text;
    private TextView tv_output;

    SharedPreferences note;
    SharedPreferences.Editor noteEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save   = (Button) findViewById(R.id.btn_save);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_edit   = (Button) findViewById(R.id.btn_edit);

        btn_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_edit.setOnClickListener(this);

        btn_edit.setVisibility(View.INVISIBLE);
        btn_delete.setVisibility(View.INVISIBLE);

        edt_text = (EditText) findViewById(R.id.edt_text);
        tv_output = (TextView) findViewById(R.id.tv_output);

        note = getSharedPreferences("note", MODE_PRIVATE);
        noteEdit = note.edit();

        if(!getNote().equals("")) {
            tv_output.setText(getNote());
            btn_edit.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
        }
    }

    public String getNote() {
        return note.getString("note", "");
    }

    public void showNote() {
        if(!getNote().equals("")) {
           tv_output.setText(getNote());
           btn_edit.setVisibility(View.VISIBLE);
           btn_delete.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(getApplicationContext(), "Keine Notizen zum Anzeigen verfügbar", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveNote(String note) {
        if(!note.equals("")){
            noteEdit.putString("note", note);
            noteEdit.commit();
            edt_text.setText("");
            edt_text.setHint("Notiz eingeben");
            showNote();
        }
        else {
            Toast.makeText(getApplicationContext(), "Notiz hat keinen Inhalt", Toast.LENGTH_SHORT).show();
        }
    }

    public void editNote() {
        edt_text.setText(getNote());
        tv_output.setText("");
        btn_edit.setVisibility(View.INVISIBLE);
        btn_delete.setVisibility(View.INVISIBLE);
    }

    public void deleteNote() {
        noteEdit.putString("note", "");
        noteEdit.commit();
        tv_output.setText("");
        btn_edit.setVisibility(View.INVISIBLE);
        btn_delete.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_save:
                saveNote(edt_text.getText().toString());
                break;
            case R.id.btn_edit:
                editNote();
                break;
            case R.id.btn_delete:
                deleteNote();
                break;
        }
    }
}