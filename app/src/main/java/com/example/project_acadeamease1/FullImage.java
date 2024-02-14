package com.example.project_acadeamease1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FullImage extends AppCompatActivity {
    private ImageView fullImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        fullImageView = findViewById(R.id.fullImageView);

        String imageUrl = getIntent().getStringExtra("imageUrl");

        Glide.with(this).load(imageUrl).into(fullImageView);
    }
}

       /* Button deleteButton = findViewById(R.id.deleteButton);
        //String documentId = getIntent().getStringExtra("documentId");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to delete the image from Firestore
                deleteImage(imageUrl);
            }
        });



    }
    /*private void deleteImage(String imageUrl) {
        // Get a reference to the Firestore document corresponding to the image
        CollectionReference imagesCollectionRef = FirebaseFirestore.getInstance().collection("Images");
        DocumentReference imageDocRef = imagesCollectionRef.document(imageUrl);

        // Delete the document from Firestore
        imageDocRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Image deleted successfully
                        Toast.makeText(FullImage.this, "Image deleted", Toast.LENGTH_SHORT).show();
                        // Finish the activity or perform any other action
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete image
                        Toast.makeText(FullImage.this, "Failed to delete image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    in xml file
     <Button
    android:id="@+id/deleteButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_alignParentBottom="true"
    android:layout_centerHorizontal="true"
    android:layout_marginStart="296dp"
    android:layout_marginTop="16dp"
    android:background="@drawable/buttonbg"
    android:textAlignment="center"
    android:textColor="@color/mocha"
    android:textSize="14sp"
    app:backgroundTint="@null"
    android:text="Delete"
    app:layout_constraintStart_toStartOf="@+id/fullImageView"
    app:layout_constraintTop_toTopOf="@+id/fullImageView" />
    */

