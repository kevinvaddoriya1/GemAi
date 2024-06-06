package com.techbuddys.appui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.techbuddys.appui.R;

public class DocToPdfActivity extends AppCompatActivity {

    ImageView selectgdocFile;
    ImageButton doctopdfbackarrow;
    private static final int PICK_DOC_FILE_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_to_pdf);

        selectgdocFile = findViewById(R.id.selectgdocFile);
        doctopdfbackarrow = findViewById(R.id.doctopdfbackarrow);


        doctopdfbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        selectgdocFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDocFile();

            }
        });



    }
    private void pickDocFile () {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // MIME type for .doc files
        startActivityForResult(intent, PICK_DOC_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_DOC_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();
                if (selectedFileUri != null) {
                    // Check if the selected file has a .doc or .docx extension
                    String fileExtension = getFileExtension(selectedFileUri);
                    if ("doc".equals(fileExtension) || "docx".equals(fileExtension)) {
                        // Handle the selected .doc or .docx file URI here
                        Toast.makeText(this, "Selected .doc/.docx File: " + selectedFileUri.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please select a .doc/.docx file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getFileExtension(Uri uri) {
        String extension = null;
        String mimeType = getContentResolver().getType(uri);
        if (mimeType != null) {
            extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        }
        if (extension == null) {
            extension = uri.getPath();
            int lastDot = extension.lastIndexOf(".");
            if (lastDot != -1) {
                extension = extension.substring(lastDot + 1);
            }
        }
        return extension;
    }
}