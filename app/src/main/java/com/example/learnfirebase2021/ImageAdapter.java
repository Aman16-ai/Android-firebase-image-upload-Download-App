package com.example.learnfirebase2021;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    List<ImageModel> imageModels;
    Context context;
    public ImageAdapter(List<ImageModel> imageModels,Context context) {
        this.imageModels = imageModels;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.images_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageModel model = imageModels.get(position);
        holder.imageName.setText(model.getName());
//        Picasso.get().load(model.getUrl()).fit().centerCrop().into(holder.ImageFirebase);
        Glide.with(context).load(model.getUrl()).fitCenter().into(holder.ImageFirebase);
//        holder.ImageFirebase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "clicked on image", Toast.LENGTH_SHORT).show();
//                String imageUrl = model.getUrl();
//                Intent intent = new Intent(context,ShowFullImage.class);
//                intent.putExtra("URL",imageUrl);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageFirebase;
        TextView imageName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageFirebase = itemView.findViewById(R.id.fetchImg);
            imageName = itemView.findViewById(R.id.imgName);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
