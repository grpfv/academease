package com.example.project_acadeamease1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Tab_files extends Fragment {
    FloatingActionButton floatingActionButton;
    ListView listView;
    List<pdfClass> uploads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_files, container, false);

        floatingActionButton = view.findViewById(R.id.float_btn);
        listView = view.findViewById(R.id.listview);
        uploads = new ArrayList<>();

        viewAllFiles();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdfClass pdfUpload = uploads.get(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdfUpload.getUrl()));
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddtoFiles.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void viewAllFiles() {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("my_Courses").document("kALPz8E4QdH9EyIUcWch").collection("Files");
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                uploads.clear(); // Clear the list before adding new data
                for (DocumentSnapshot document : task.getResult()) {
                    pdfClass pdfClass = document.toObject(pdfClass.class);
                    if (pdfClass != null) {
                        uploads.add(pdfClass);
                    }
                }

                String[] fileNames = new String[uploads.size()];
                for (int i = 0; i < fileNames.length; i++) {
                    fileNames[i] = uploads.get(i).getName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, fileNames) {
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        text.setTextSize(22);
                        return view;
                    }
                };

                listView.setAdapter(adapter);

            }
        });
    }
}
