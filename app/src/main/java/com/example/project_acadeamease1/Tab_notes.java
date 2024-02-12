package com.example.project_acadeamease1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class Tab_notes extends Fragment {

    FloatingActionButton addNoteBtn;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    String courseId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_notes, container, false);

        addNoteBtn = view.findViewById(R.id.add_note_btn); // Use view.findViewById() to find views in the fragment
        recyclerView = view.findViewById(R.id.recycler_view_notes);

        //addNoteBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AddtoNotes.class))); // Use requireActivity() instead of Tab_notes.this
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddtoNotes.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
        setupRecyclerView();

        return view;
    }

    void setupRecyclerView() {
        Query query = Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<DataClass> options = new FirestoreRecyclerOptions.Builder<DataClass>()
                .setQuery(query, DataClass.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // Use requireContext() instead of this

        noteAdapter = new NoteAdapter(options, requireActivity());

        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }
    public void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

}
