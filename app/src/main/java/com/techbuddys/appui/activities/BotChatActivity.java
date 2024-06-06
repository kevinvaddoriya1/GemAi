package com.techbuddys.appui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techbuddys.appui.R;
import com.techbuddys.appui.adapters.ChatAdapter;
import com.techbuddys.appui.manager.ApiManager;
import com.techbuddys.appui.manager.ChatApiManager;
import com.techbuddys.appui.manager.SharedPrefManager;
import com.techbuddys.appui.model.ConversationModel;
import com.techbuddys.appui.model.InsertMessagesModel;
import com.techbuddys.appui.model.InsertTopicModel;
import com.techbuddys.appui.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BotChatActivity extends AppCompatActivity {

    ImageButton chatbotbackarrow;
    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private ImageButton buttonSend;
    private List<Message> messageList;
    private ChatAdapter chatAdapter;
    private boolean isFirstMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_chat);

        isFirstMsg = true;
        boolean isTopicClicked = getIntent().getBooleanExtra("isClicked", false);
        Log.d("isTopicClicked", String.valueOf(isTopicClicked));

        recyclerView = findViewById(R.id.recycler_view_chat);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);
        chatbotbackarrow = findViewById(R.id.chatbotbackarrow);

        chatbotbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (isTopicClicked) {
            isFirstMsg = false;
            String u_id = getIntent().getStringExtra("u_id");
            String topic_id = getIntent().getStringExtra("topic_id");
            retrofit2.Call<List<ConversationModel>> conversationList = ApiManager.getInstance().apiInterface.getConversation(u_id, topic_id);

            conversationList.enqueue(new retrofit2.Callback<List<ConversationModel>>() {
                @Override
                public void onResponse(retrofit2.Call<List<ConversationModel>> call, retrofit2.Response<List<ConversationModel>> response) {
                    List<ConversationModel> conversationModelList = response.body();

                    if (response.isSuccessful()) {
                        for (ConversationModel conversationModel : conversationModelList) {
                            String content = conversationModel.getContent();
                            String bot_response = conversationModel.getBot_response();
                            // Do something with the content
                            if (bot_response.equals("0")) {
                                runOnUiThread(() -> addMessage(content, true));
                            } else {
                                runOnUiThread(() -> addMessage(content, false));
                            }

                        }
                    } else {
                        Log.d("onResponse", "Response unsuccessful");
                    }

                }

                @Override
                public void onFailure(retrofit2.Call<List<ConversationModel>> call, Throwable t) {

                }
            });

            buttonSend.setOnClickListener(view -> ClickedSendMessage(topic_id, u_id));
        } else {

            buttonSend.setOnClickListener(view -> sendMessage());

        }


        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

    }

    private void sendMessage() {

        String message = editTextMessage.getText().toString().trim();


        if (!message.isEmpty() && isFirstMsg) {
            retrofit2.Call<InsertTopicModel> callback = ApiManager.getInstance().apiInterface.insertTopic(message);
            callback.enqueue(new retrofit2.Callback<InsertTopicModel>() {
                @Override
                public void onResponse(retrofit2.Call<InsertTopicModel> call, retrofit2.Response<InsertTopicModel> response) {
                    InsertTopicModel topicResponse = response.body();
                    if (!topicResponse.isError()) {
                        int topicId = topicResponse.getTopicId();
                        SharedPrefManager.editor("tempTopicID", topicId);
                        int topicID = SharedPrefManager.getTempTopicID();
                        String u_id = SharedPrefManager.getUser().getU_id();
                        retrofit2.Call<InsertMessagesModel> InsertMessagesModelCall = ApiManager.getInstance().apiInterface.setMessages(
                                topicID,
                                u_id,
                                message,
                                false);
                        InsertMessagesModelCall.enqueue(new retrofit2.Callback<InsertMessagesModel>() {
                            @Override
                            public void onResponse(retrofit2.Call<InsertMessagesModel> call, retrofit2.Response<InsertMessagesModel> response) {

                            }

                            @Override
                            public void onFailure(retrofit2.Call<InsertMessagesModel> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<InsertTopicModel> call, Throwable t) {

                }
            });

            runOnUiThread(() -> addMessage(message, true));


            receiveMessageResponse(message);
            isFirstMsg = false;
            editTextMessage.getText().clear();
        } else {
            int topicID = SharedPrefManager.getTempTopicID();
            String u_id = SharedPrefManager.getUser().getU_id();
            retrofit2.Call<InsertMessagesModel> InsertMessagesModelCall = ApiManager.getInstance().apiInterface.setMessages(
                    topicID,
                    u_id,
                    message,
                    false);
            InsertMessagesModelCall.enqueue(new retrofit2.Callback<InsertMessagesModel>() {
                @Override
                public void onResponse(retrofit2.Call<InsertMessagesModel> call, retrofit2.Response<InsertMessagesModel> response) {

                }

                @Override
                public void onFailure(retrofit2.Call<InsertMessagesModel> call, Throwable t) {

                }
            });


            runOnUiThread(() -> addMessage(message, true));


            receiveMessageResponse(message);
            editTextMessage.getText().clear();
        }
    }
    private void receiveMessageResponse(String messageText) {
        Log.e("receiveMessageResponse", "receiveMessageResponse");
        ChatApiManager chatService = new ChatApiManager();
        chatService.sendMessage(messageText, new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("onFailure", e.getMessage());
                String response = "Failed to load response due to " + e.getMessage();

                runOnUiThread(() -> addMessage(response, false));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray choicesArray = jsonObject.getJSONArray("choices");
                        JSONObject choice = choicesArray.getJSONObject(0);
                        JSONObject messageObj = choice.getJSONObject("message");
                        String content = messageObj.getString("content");
                        Log.d("Response", "Received response content: " + content);

                        runOnUiThread(() -> addMessage(content, false));

                        int topicID = SharedPrefManager.getTempTopicID();
                        String u_id = SharedPrefManager.getUser().getU_id();
                        retrofit2.Call<InsertMessagesModel> InsertMessagesModelCall = ApiManager.getInstance().apiInterface.setMessages(
                                topicID,
                                u_id,
                                content,
                                true);
                        InsertMessagesModelCall.enqueue(new retrofit2.Callback<InsertMessagesModel>() {
                            @Override
                            public void onResponse(retrofit2.Call<InsertMessagesModel> call, retrofit2.Response<InsertMessagesModel> response) {
                                if (response.isSuccessful()){

                                    Log.d("onResponse", response.body().getMessage());

                                } else {
                                    Log.e("onResponse", "Response Error");
                                }
                            }

                            @Override
                            public void onFailure(retrofit2.Call<InsertMessagesModel> call, Throwable t) {
                                Log.e("onFailure", t.getMessage());
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("JSONError", "Failed to parse JSON response: " + e.getMessage());
                    }
                } else {
                    Log.e("ResponseError", "Unsuccessful response or empty body");
                }
            }
        });

    }

    private void ClickedSendMessage(String topicId,String uid) {
        String message = editTextMessage.getText().toString().trim();

        retrofit2.Call<InsertMessagesModel> InsertMessagesModelCall = ApiManager.getInstance().apiInterface.setMessages(
                Integer.parseInt(topicId),
                uid,
                message,
                false);
        InsertMessagesModelCall.enqueue(new retrofit2.Callback<InsertMessagesModel>() {
            @Override
            public void onResponse(retrofit2.Call<InsertMessagesModel> call, retrofit2.Response<InsertMessagesModel> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<InsertMessagesModel> call, Throwable t) {

            }
        });
        runOnUiThread(() -> addMessage(message, true));

        ClickedSendMessageForChat(message,topicId,uid);
        editTextMessage.getText().clear();
    }

    private void ClickedSendMessageForChat(String message, String topicId, String uid) {
        ChatApiManager chatService = new ChatApiManager();
        chatService.sendMessage(message, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("onFailure", e.getMessage());
                String response = "Failed to load response due to " + e.getMessage();

                runOnUiThread(() -> addMessage(response, false));

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray choicesArray = jsonObject.getJSONArray("choices");
                        JSONObject choice = choicesArray.getJSONObject(0);
                        JSONObject messageObj = choice.getJSONObject("message");
                        String content = messageObj.getString("content");
                        Log.d("Response", "Received response content: " + content);

                        runOnUiThread(() -> addMessage(content, false));

                        retrofit2.Call<InsertMessagesModel> InsertMessagesModelCall = ApiManager.getInstance().apiInterface.setMessages(
                                Integer.parseInt(topicId),
                                uid,
                                content,
                                true);
                        InsertMessagesModelCall.enqueue(new retrofit2.Callback<InsertMessagesModel>() {
                            @Override
                            public void onResponse(retrofit2.Call<InsertMessagesModel> call, retrofit2.Response<InsertMessagesModel> response) {

                                if (response.isSuccessful()){

                                    Log.d("onResponse", response.body().getMessage());

                                } else {
                                    Log.e("onResponse", "Response Error");
                                }
                            }

                            @Override
                            public void onFailure(retrofit2.Call<InsertMessagesModel> call, Throwable t) {
                                Log.e("onFailure", t.getMessage());
                            }
                        });
                    } catch (JSONException e) {
                        Log.e("JSONError", "Failed to parse JSON response: " + e.getMessage());
                    }
                } else {
                    Log.e("ResponseError", "Unsuccessful response or empty body");
                }
            }
        });
    }


    private void addMessage(String messageText, boolean isUser) {
        Message message = new Message(messageText, isUser);
        messageList.add(message);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.scrollToPosition(messageList.size() - 1);
    }
}