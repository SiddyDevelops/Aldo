package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerView;

public class SpecificContentActivity extends AppCompatActivity {

    TextView specificContentTitle;
    TextView name;
    TextView description;
    Intent intent;

    ImageView googleVec;
    ImageView ytVec;
    ImageView notesVec;
    ImageView codeVec;

    String webURL;
    String ytURL;
    String visURL;
    String notesURL;

    String code;

    Button visButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_content);

        View overlay = findViewById(R.id.layout_specific_content);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.translucent));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toolbar toolbar = findViewById(R.id.toolbarSpecificContent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        name = findViewById(R.id.name);
        specificContentTitle = findViewById(R.id.specificContentTitle);
        description = findViewById(R.id.description);

        googleVec = findViewById(R.id.googleVec);
        ytVec = findViewById(R.id.ytVec);
        notesVec = findViewById(R.id.notesVec);
        codeVec = findViewById(R.id.codeVec);
        visButton = findViewById(R.id.visButton);

        name.setText(intent.getStringExtra("Name"));
        specificContentTitle.setText(intent.getStringExtra("Name"));
        description.setText(intent.getStringExtra("Description"));

        webURL = intent.getStringExtra("WebURL");
        ytURL = intent.getStringExtra("YTURL");
        visURL = intent.getStringExtra("VisLink");
        notesURL = intent.getStringExtra("NotesURL");

        code = intent.getStringExtra("Code");

        if(visURL.equals("NULL"))
        {
            visButton.setVisibility(View.INVISIBLE);
        }

        onClickMethods();

    }

    private void onClickMethods() {

        googleVec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentWV = new Intent(getApplicationContext(), WebViewActivity.class);
                intentWV.putExtra("Link",webURL);
                startActivity(intentWV);
            }
        });

        ytVec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentYP = new Intent(getApplicationContext(), YoutubePlayerActivity.class);
                intentYP.putExtra("Link",ytURL);
                startActivity(intentYP);

            }
        });

        visButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentWV = new Intent(getApplicationContext(), WebViewActivity.class);
                intentWV.putExtra("Link",visURL);
                startActivity(intentWV);
            }
        });

        notesVec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPdf = new Intent(getApplicationContext(), ViewPdfThroughURL.class);
                intentPdf.putExtra("BookURL",notesURL);
                intentPdf.putExtra("Book_Name",intent.getStringExtra("Name"));
                startActivity(intentPdf);
                //Intent intentWV = new Intent(getApplicationContext(), WebViewActivity.class);
                //intentWV.putExtra("Link",notesURL);
                //startActivity(intentWV);
            }
        });

        codeVec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCode = new Intent(getApplicationContext(),CodeViewActivity.class);
                intentCode.putExtra("Code",code);
                startActivity(intentCode);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}