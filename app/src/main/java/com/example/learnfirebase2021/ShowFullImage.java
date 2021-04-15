package com.example.learnfirebase2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ShowFullImage extends AppCompatActivity {

    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_image);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        imageview = findViewById(R.id.fullImage);
        Glide.with(getApplicationContext()).load(url).fitCenter().into(imageview);
    }
}