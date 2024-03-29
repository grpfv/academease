    package com.example.project_acadeamease1;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.webkit.WebView;
    import android.webkit.WebViewClient;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;

    import com.firebase.ui.firestore.FirestoreRecyclerOptions;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.firestore.CollectionReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.Query;

    import java.util.ArrayList;
    import java.util.List;

    public class Tab_files extends Fragment {
        ListView listView;
        WebView webView;
        FloatingActionButton floatingActionButton;

        List<pdfClass> uploads;
        String courseId;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            courseId = getCourseId(requireContext()); // Retrieve courseId
        }

        private String getCourseId(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            return sharedPreferences.getString("courseId", "");
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tab_files, container, false);
            floatingActionButton = view.findViewById(R.id.float_btn);

            listView = view.findViewById(R.id.listview);
            webView = view.findViewById(R.id.Webview);

            uploads = new ArrayList<>();

            viewAllFiles();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pdfClass pdfUpload = uploads.get(i);
                    String url = pdfUpload.getUrl();

                    loadPDF(url);
                }
            });

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(requireContext(), AddtoFiles.class);
                    intent.putExtra("courseId", courseId); // Pass courseId to AddtoFiles activity
                    startActivity(intent);
                }
            });

            return view;
        }

        private void viewAllFiles() {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                    .collection("Courses")
                    .document(currentUser.getUid())
                    .collection("my_Courses")
                    .document(courseId)
                    .collection("Files");

            collectionReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    uploads.clear(); // Clear the list before adding new data
                    for (DocumentSnapshot document : task.getResult()) {
                        pdfClass pdf = document.toObject(pdfClass.class);
                        if (pdf != null) {
                            uploads.add(pdf);
                        }
                    }

                    if (uploads.isEmpty()) {
                        // Show a message indicating no files found
                        Toast.makeText(requireContext(), "No files found", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update the list view with the retrieved files
                        updateListView();
                    }
                } else {
                    // Show a message indicating failure to retrieve files
                    Toast.makeText(requireContext(), "Failed to retrieve files", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void updateListView() {
            String[] fileNames = new String[uploads.size()];
            for (int i = 0; i < fileNames.length; i++) {
                fileNames[i] = uploads.get(i).getName();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fileNames);
            listView.setAdapter(adapter);
        }

        private void loadPDF(String url) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setVisibility(View.VISIBLE);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

            String viewerUrl = "file:///android_asset/pdfjs/web/viewer.html?file=" + url;

            webView.loadUrl(viewerUrl);

        }


    }
