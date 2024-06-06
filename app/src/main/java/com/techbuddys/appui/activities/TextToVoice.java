package com.techbuddys.appui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.techbuddys.appui.R;
import com.techbuddys.appui.manager.ChatApiManager;
import com.techbuddys.appui.manager.LoadDialog;
import com.techbuddys.appui.manager.VoiceApiManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TextToVoice extends AppCompatActivity {

    public static final int RecordAudioRequestCode = 1;
    ImageView selectAudio;
    ImageButton voicetotxtbackarrow;
LottieAnimationView lottie_voice_siri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_to_text);
        LoadDialog loadDialog = new LoadDialog(this);
        loadDialog.startLoadingdialog();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
        selectAudio = findViewById(R.id.selectAudio);

        voicetotxtbackarrow = findViewById(R.id.voicetotxtbackarrow);
        lottie_voice_siri = findViewById(R.id.lottie_voice_siri);



        selectAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }


        });

        voicetotxtbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak...");

        try {
            startActivityForResult(intent,RecordAudioRequestCode);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RecordAudioRequestCode:{
                if (resultCode == RESULT_OK && null!=data){
                    ArrayList<String> resultText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("@@@@@@",resultText.get(0));
                    ChatApiManager chatApiManager = new ChatApiManager();
                    chatApiManager.sendMessage(resultText.get(0), new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

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
                                    sendMessage(content);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
                break;
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessage(String prompt) {
        VoiceApiManager voiceApiManager = new VoiceApiManager();
        voiceApiManager.sendPrompt(prompt, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                byte[] audioData = response.body().bytes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File tempFile = File.createTempFile("temp", ".wav",getCacheDir() ); // Create a temporary file
                            FileOutputStream fos = new FileOutputStream(tempFile);
                            fos.write(audioData); // Write the audio data to the temporary file
                            fos.close();

                            MediaPlayer mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(tempFile.getAbsolutePath()); // Set the audio data source
                            mediaPlayer.prepare(); // Prepare the MediaPlayer
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                    lottie_voice_siri.setVisibility(View.VISIBLE);
                                    // Start playback when MediaPlayer is prepared
                                }
                            });
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    lottie_voice_siri.setVisibility(View.GONE);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }; // Pass the audio data to receiveMessage method
                    }
                });
            }
        });
    }

}