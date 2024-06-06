package com.techbuddys.appui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.techbuddys.appui.R;
import com.techbuddys.appui.model.Image;
import java.util.List;

public class LikedImagesAdapter extends RecyclerView.Adapter<LikedImagesAdapter.ViewHolder> {

    private Context context;
    private List<Image> likedImages;

    public LikedImagesAdapter(Context context, List<Image> likedImages) {
        this.context = context;
        this.likedImages = likedImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = likedImages.get(position);
        if (image.getImg_path() != null) {
            Picasso.get().load("http://192.168.29.112/gemai/" + image.getImg_path()).into(holder.imageView);
        } else {
            // Handle the case when image path is null, for example, set a placeholder image
//            holder.imageView.setImageResource(R.drawable.delete);
        }
    }

    @Override
    public int getItemCount() {
        return likedImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.images);
        }
    }
}
