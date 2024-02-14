package com.example.project_acadeamease1;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Tab_album extends Fragment {
    FloatingActionButton fab;
    private GridView gridView;
    private ArrayList<DataClass> dataList;
    private AlbumAdapter adapter;
    private final CollectionReference databaseReference = Utility.getCollectionReferenceForAlbum("kALPz8E4QdH9EyIUcWch");
        //FirebaseFirestore.getInstance().collection("Images");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_album, container, false);

        fab = view.findViewById(R.id.fab);
        gridView = view.findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        adapter = new AlbumAdapter(requireContext(), dataList); // Pass albumCollection to the adapter
        gridView.setAdapter(adapter);

        // Add ValueEventListener to update the grid when data changes in Firestore
        databaseReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Tab_album", "Error fetching images", e);
                    return;
                }

                dataList.clear(); // Clear the list before adding new data
                for (QueryDocumentSnapshot document : querySnapshot) {
                    DataClass dataClass = document.toObject(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddtoAlbum.class); // use requireContext() instead of "Tab_album.this"
                startActivity(intent);
            }
        });

        return view;
    }
}
