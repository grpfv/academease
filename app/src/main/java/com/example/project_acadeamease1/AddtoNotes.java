package com.example.project_acadeamease1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.Timestamp;

public class AddtoNotes extends AppCompatActivity {

    EditText titleEditText,contentEditText;
    ImageButton saveNoteBtn;
    TextView pageTitleText, deleteNoteBtn;
    String title,content,docId;
    boolean isEditMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_notes);

        titleEditText = findViewById(R.id.notes_title);
        contentEditText = findViewById(R.id.notes_content);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleText = findViewById(R.id.page_title);
        deleteNoteBtn = findViewById(R.id.delete_note);

        //recieve data when a note is clicked
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            isEditMode = true;
        }

        titleEditText.setText(title);
        contentEditText.setText(content);
        if(isEditMode){
            pageTitleText.setText("NOTES");
            deleteNoteBtn.setVisibility(View.VISIBLE);
        }

        saveNoteBtn.setOnClickListener(v-> saveNote());
        deleteNoteBtn.setOnClickListener(v->deleteNoteFromFirebase());
    }

    void saveNote(){
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();

        if(noteTitle==null || noteTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }
        DataClass note = new DataClass();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }

    void saveNoteToFirebase(DataClass note){
        DocumentReference documentReference;
        if(isEditMode){
            //update note
            documentReference = Utility.getCollectionReferenceForNotes("kALPz8E4QdH9EyIUcWch").document(docId);
        }else{
            documentReference = Utility.getCollectionReferenceForNotes("kALPz8E4QdH9EyIUcWch").document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddtoNotes.this, "Note Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddtoNotes.this, "Failed Adding Note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void deleteNoteFromFirebase(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes("kALPz8E4QdH9EyIUcWch").document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddtoNotes.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddtoNotes.this, "Failed Deleting Note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}