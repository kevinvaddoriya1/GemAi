package com.techbuddys.appui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.techbuddys.appui.R;
import com.techbuddys.appui.adapters.ImageToTextAdapter;
import com.techbuddys.appui.manager.Constants;
import com.techbuddys.appui.model.ImageToTextModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ImageToTextActivity extends AppCompatActivity {

    ImageView btnSelectImage;
    EditText edt_question;
    ImageView btnSendQuestion;
    ImageView imageView;
    private static final int PICK_IMAGE = 1;
    RecyclerView rvImageChat;
    ImageToTextAdapter imageToTextAdapter;
    private List<ImageToTextModel> messageList;

    ImageButton imgtotxtbackarrow;
    Uri imageUri;
    Bitmap bitmap = null;
    GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_text);

        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSendQuestion = findViewById(R.id.btnSendQuestion);
        edt_question = findViewById(R.id.edt_question);
        imageView = findViewById(R.id.image);

        GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-pro-vision", Constants.getGeminiApi());
        model = GenerativeModelFutures.from(gm);

        rvImageChat  = findViewById(R.id.rvImageChat);
        imgtotxtbackarrow  = findViewById(R.id.imgtotxtbackarrow);

        btnSendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateResponse();
            }
        });
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        imgtotxtbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        messageList = new ArrayList<>();
        imageToTextAdapter = new ImageToTextAdapter(messageList,this);
        rvImageChat.setLayoutManager(new LinearLayoutManager(this));
        rvImageChat.setAdapter(imageToTextAdapter);

    }

    private void generateResponse() {

        if (bitmap == null) {
            Toast.makeText(this, "Please Select One Image...", Toast.LENGTH_SHORT).show();
        }
        else {
            String str = edt_question.getText().toString();
            Content content = new Content.Builder()
                    .addText(str)
                    .addImage(bitmap)
                    .build();

            imageView.setVisibility(View.GONE);
            edt_question.setText("");
            sendMessage(bitmap, str);

            Executor executor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                executor = getMainExecutor();
            }
            ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
            assert executor != null;
            Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    String resultText = result.getText();
                    receiveMessage(resultText);
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ImageToTextActivity" , t.getMessage());
                }
            }, executor);
        }
    }

    private void selectImage() {
        Intent intent;
        // Start with PNG
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R && android.os.ext.SdkExtensions.getExtensionVersion(android.os.Build.VERSION_CODES.R) >= 2) {
            intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
        }
        intent.setType("image/png");  // Start with PNG
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
                "image/jpeg",
                "image/webp",
                "image/heic",
                "image/heif"
        });
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            try {
                imageUri = data.getData();
                String mimeType = getContentResolver().getType(imageUri);

                if (mimeType != null &&
                        (mimeType.startsWith("image/png") ||
                                mimeType.startsWith("image/jpeg") ||
                                mimeType.startsWith("image/webp") ||
                                mimeType.startsWith("image/heic") ||
                                mimeType.startsWith("image/heif"))) {
                    // Process the selected image
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageURI(imageUri);
                    Log.d("ImageActivity===", "ImageToBitmap");

                    convertImageToBitmap(imageUri);
                } else {
                    // Handle unsupported image type
                    Toast.makeText(ImageToTextActivity.this, "Unsupported image format", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

//            sendMessage(String.valueOf(selectedImage));
        }
    }

    private void convertImageToBitmap(Uri imageUri) {
        try {
            Log.d("ImageActivity===", "convertImageToBitmap");
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Bitmap bitmap, String message) {
        if (bitmap != null)
        {
            ImageToTextModel model1 = new ImageToTextModel(message, true, bitmap);
            messageList.add(model1);
            imageToTextAdapter.notifyItemInserted(messageList.size() - 1);
            rvImageChat.scrollToPosition(messageList.size() - 1);
//            receiveMessage("Nice Image");
        }
    }

    private void receiveMessage(String messageText) {
        ImageToTextModel model1 = new ImageToTextModel(messageText, false, null);
        messageList.add(model1);
        imageToTextAdapter.notifyItemInserted(messageList.size() - 1);
        rvImageChat.scrollToPosition(messageList.size() - 1);
    }
}