package com.techbuddys.appui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techbuddys.appui.R;
import com.techbuddys.appui.model.ImageToTextModel;

import java.util.List;

public class ImageToTextAdapter extends RecyclerView.Adapter<ImageToTextAdapter.ViewHolder> {

    private List<ImageToTextModel> messageList;
    Context context;

    public ImageToTextAdapter(List<ImageToTextModel> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_sent_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position)==0)
        {
            holder.imageView.setImageBitmap(messageList.get(position).getBitmap());
            if (messageList.get(position).getText().isEmpty()) {
                holder.txt_question.setVisibility(View.GONE);
            } else {
                holder.txt_question.setText(messageList.get(position).getText());
            }
        }
        else
        {
            holder.textViewMessage.setText(messageList.get(position).getText());

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).isSentByCurrentUser()) {
            return 0;
        } else {
            return 1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        TextView txt_question;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.text_view_message);
            txt_question = itemView.findViewById(R.id.txt_question);
            imageView = itemView.findViewById(R.id.img_selected);

        }
    }
}
