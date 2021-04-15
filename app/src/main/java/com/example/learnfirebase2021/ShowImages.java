package com.example.learnfirebase2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowImages extends AppCompatActivity {

//    ProgressBar bar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ImageInfo");
    RecyclerView recyclerView;
    ImageAdapter adapter;
    List<ImageModel> imageModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bar = findViewById(R.id.myprogress);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Images");
        imageModels = new ArrayList<>();
        setContentView(R.layout.activity_show_images);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                imageModels.clear();
                for(DataSnapshot snapshots: snapshot.getChildren()) {
//                    ImageModel model =  snapshots.getValue(ImageModel.class);
                    ImageModel model = new ImageModel();
                    model.setName(snapshots.child("name").getValue().toString());
                    model.setUrl(snapshots.child("url").getValue().toString());
                    Log.d("modelinfo", "Name" + model.getName() + "Url "+model.getUrl());
//                    Toast.makeText(ShowImages.this, model.getName(), Toast.LENGTH_SHORT).show();
                    imageModels.add(model);
                }
//                bar.setVisibility(View.GONE);

//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowImages.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                bar.setVisibility(View.GONE);
            }
        });

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(imageModels,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}