package com.example.project_acadeamease1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_acadeamease1.AddtoAlbum;
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

    // Request code for startActivityForResult
    private static final int DELETE_IMAGE_REQUEST_CODE = 1;

    String courseId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = getCourseId(requireContext()); // Retrieve courseId
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_album, container, false);

        fab = view.findViewById(R.id.fab);
        gridView = view.findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        adapter = new AlbumAdapter(requireContext(), dataList);
        gridView.setAdapter(adapter);

        CollectionReference firestoreReference = Utility.getCollectionReferenceForAlbum(courseId);
        firestoreReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Tab_album", "Error fetching images", e);
                    return;
                }

                dataList.clear();
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
                Intent intent = new Intent(requireContext(), AddtoAlbum.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            String deletedImageUrl = data.getStringExtra("deletedImageUrl");
            if (deletedImageUrl != null) {
                // Remove the deleted image from dataList and update UI
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getImageURL().equals(deletedImageUrl)) { // Accessing imageURL using getImageURL() method
                        dataList.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }


    private String getCourseId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("courseId", "");
    }
}
