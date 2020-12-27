package com.example.camauth2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AuthenticateImage extends AppCompatActivity {

    private FileChooserFragement fragement;
    private Button signImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_image);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragement = (FileChooserFragement) fragmentManager.findFragmentById(R.id.fragment);
        signImage = (Button) findViewById(R.id.signImageButton);

        signImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),fragement.getImagePath(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}