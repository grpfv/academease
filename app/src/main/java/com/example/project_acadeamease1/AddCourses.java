package com.example.project_acadeamease1;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.ViewGroup;

import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class AddCourses extends DialogFragment {

    private EditText enterSubject, enterInstructor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_courses, container, false);

        enterSubject = view.findViewById(R.id.enter_Subject);
        enterInstructor = view.findViewById(R.id.enter_Instructor);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCourses();
            }
        });

        return view;
    }


    void addToCourses() {
        String subject = enterSubject.getText().toString();
        String instructor = enterInstructor.getText().toString();

        if (subject.isEmpty() || subject == null){
            Toast.makeText(requireContext(), "Please enter Subject", Toast.LENGTH_SHORT).show();
            enterSubject.setError("Subject Name is required");
            return;
        }
        if (instructor.isEmpty() || instructor == null){
            Toast.makeText(requireContext(), "Please enter Subject", Toast.LENGTH_SHORT).show();
            enterSubject.setError("Subject Name is required");
            return;
        }

        CourseModel course = new CourseModel();
        course.setSubject(subject);
        course.setInstructor(instructor);
        course.setTimestamp(Timestamp.now());

        saveCoursesToFirebase(course);
    }

    void saveCoursesToFirebase(CourseModel course){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForCourses().document();

        documentReference.set(course).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(requireContext(),"Course Added",Toast.LENGTH_SHORT).show();
                    dismiss();
                }else{
                    Toast.makeText(requireContext(),"Failed Adding Course",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}