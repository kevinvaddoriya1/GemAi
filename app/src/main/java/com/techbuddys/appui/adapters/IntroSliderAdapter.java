package com.techbuddys.appui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techbuddys.appui.R;

public class IntroSliderAdapter extends RecyclerView.Adapter<IntroSliderAdapter.SliderViewHolder> {


    int[] slideImages;
    String[] slideTexts;


    public IntroSliderAdapter(int[] slideImages, String[] slideTexts) {
        this.slideImages = slideImages;
        this.slideTexts = slideTexts;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introducation, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.imageView.setImageResource(slideImages[position]);
        holder.textView.setText(slideTexts[position]);
    }

    @Override
    public int getItemCount() {
        return slideImages.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}