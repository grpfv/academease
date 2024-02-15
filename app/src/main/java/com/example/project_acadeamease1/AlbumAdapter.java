package com.example.project_acadeamease1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {


    private ArrayList<DataClass> dataList;
    private Context context;
    final private CollectionReference databaseReference = FirebaseFirestore.getInstance().collection("Images");

    public AlbumAdapter(Context context, ArrayList<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView gridImage = view.findViewById(R.id.gridImage);
        TextView gridCaption = view.findViewById(R.id.gridCaption);


        DataClass item = dataList.get(i);
        gridCaption.setText(item.getCaption());

        Glide.with(context).load(item.getImageURL()).into(gridImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImage.class);
                intent.putExtra("imageUrl", item.getImageURL());

                context.startActivity(intent);
            }
        });


        return view;
    }


}
