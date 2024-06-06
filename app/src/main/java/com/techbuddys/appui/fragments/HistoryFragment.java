package com.techbuddys.appui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techbuddys.appui.R;
import com.techbuddys.appui.adapters.HistoryAdapter;
import com.techbuddys.appui.manager.ApiManager;
import com.techbuddys.appui.manager.SharedPrefManager;
import com.techbuddys.appui.model.TopicModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {
    HistoryAdapter historyAdapter;
    RecyclerView recycler_History;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recycler_History = view.findViewById(R.id.rvHistory);

        fetchDataAndDisplay();

        return view;
    }


    private void fetchDataAndDisplay() {
        String uid = SharedPrefManager.getUser().getU_id();
        Call<List<TopicModel>> listCall = ApiManager.getInstance().apiInterface.getTopics(uid);

        listCall.enqueue(new Callback<List<TopicModel>>() {
            @Override
            public void onResponse(Call<List<TopicModel>> call, Response<List<TopicModel>> response) {
                if (response.isSuccessful()) {
                    List<TopicModel> topicModelList = response.body();
                    Log.d("onResponse", topicModelList.toString());

                    // Update the adapter data and notify the change
                    updateAdapterData(topicModelList);
                } else {
                    Log.d("onResponse", "Response error");
                }
            }

            @Override
            public void onFailure(Call<List<TopicModel>> call, Throwable t) {
                Log.e("onFailure", "Something went wrong: " + t.getMessage());
            }
        });
    }

    // Method to update adapter data and notify the change
    private void updateAdapterData(List<TopicModel> topicModelList) {
        if (historyAdapter == null) {
            // If adapter is null, create a new one
            historyAdapter = new HistoryAdapter(getActivity(), topicModelList);
            recycler_History.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
            recycler_History.setAdapter(historyAdapter);
        } else {
            // If adapter exists, update the data and notify the change
            historyAdapter.setData(topicModelList);
            historyAdapter.notifyDataSetChanged();
        }
    }

    public void refreshFragment() {
        // Call method to fetch and display data
        fetchDataAndDisplay();
    }
}