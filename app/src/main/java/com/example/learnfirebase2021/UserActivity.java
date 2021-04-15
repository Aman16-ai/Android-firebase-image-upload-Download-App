package com.example.learnfirebase2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    Button uploadBtn;
    Button selectBtn;
    Button logoutBtn;
    EditText ImageNameEt;
    TextView ShowImagesTv;
    ImageView PreviewImg;
    Uri mImageUri;
    StorageReference storageRef;
    DatabaseReference databaseRef;
    String ImageName;
    String url;
    ProgressBar bar;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        uploadBtn = findViewById(R.id.UploadBtn);
        selectBtn = findViewById(R.id.selectBtn);
        PreviewImg = findViewById(R.id.Img);
        ImageNameEt = findViewById(R.id.ImageNameEt);
        ShowImagesTv = findViewById(R.id.showtv);
        bar = findViewById(R.id.progress);
        bar.setVisibility(View.GONE);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserActivity.this,MainActivity.class));
            }
        });
        ShowImagesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowImages.class));
            }
        });
    }
    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(PreviewImg);
        }
    }

    private String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void UploadImage() {
        bar.setVisibility(View.VISIBLE);
        ImageName = ImageNameEt.getText().toString();
        if(ImageName.length() > 0) {
            storageRef = FirebaseStorage.getInstance().getReference().child("uploads/").child(System.currentTimeMillis() + "." +GetFileExtension(mImageUri));
            databaseRef = FirebaseDatabase.getInstance().getReference("ImageInfo");
            storageRef.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                                ImageModel model = new ImageModel(ImageName,url);
                                String id = databaseRef.push().getKey();
                                databaseRef.child(id).setValue(model);
                                bar.setVisibility(View.GONE);
                                Log.d("url", "onSuccess: "+url);
                            }
                        });
                    }
                }
            });

        }
        else {
            Toast.makeText(this, "Please enter the name of image", Toast.LENGTH_SHORT).show();
        }
    }

}