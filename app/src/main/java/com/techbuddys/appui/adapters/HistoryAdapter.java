package com.techbuddys.appui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techbuddys.appui.R;
import com.techbuddys.appui.activities.BotChatActivity;
import com.techbuddys.appui.manager.SharedPrefManager;
import com.techbuddys.appui.model.TopicModel;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    List<TopicModel> dataModelList;

    public HistoryAdapter(Context context, List<TopicModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.historyTxt.setText(dataModelList.get(position).getName());
        holder.ll_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPrefManager.editor("checkTopicClicked", true);
                String u_id = SharedPrefManager.getUser().getU_id();
                String topic_id = dataModelList.get(position).getId();
                Intent intent = new Intent(context, BotChatActivity.class);
                intent.putExtra("u_id", u_id);
                intent.putExtra("topic_id", topic_id);
                intent.putExtra("isClicked",true);

                context.startActivity(intent);
            }
        });
    }
    public void setData(List<TopicModel> newData) {
        dataModelList = newData;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyTxt;
        ImageView deleteimg;
        LinearLayout ll_topic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyTxt = itemView.findViewById(R.id.historyTxt);
            ll_topic = itemView.findViewById(R.id.ll_topic);
            deleteimg = itemView.findViewById(R.id.deleteimg);
        }
    }
}

