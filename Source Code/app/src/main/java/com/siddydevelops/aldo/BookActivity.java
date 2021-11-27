package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ramotion.circlemenu.CircleMenuView;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class BookActivity extends AppCompatActivity {

    ImageView bookCoverImage;
    TextView bookName;
    TextView authorName;
    TextView bookDiscription;
    String bookimageUrl;
    int bookNo;

    Button readBook;
    Button downloadBook;

    String nameOfBook;

    String bookURL;

    URL url;
    String fileName;

    CircleMenuView circleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        View overlay = findViewById(R.id.layout_book);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.translucent));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toolbar toolbar = findViewById(R.id.toolbarBooks);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readBook = findViewById(R.id.readBook);
        downloadBook = findViewById(R.id.downloadBook);

        bookCoverImage = findViewById(R.id.bookCoverImageView);
        bookName = findViewById(R.id.bookName);
        authorName = findViewById(R.id.authorName);
        bookDiscription = findViewById(R.id.bookDescription);

        Intent intent = getIntent();
        nameOfBook = intent.getStringExtra("BookName");
        bookName.setText(intent.getStringExtra("BookName"));
        authorName.setText(intent.getStringExtra("AuthorName"));
        bookDiscription.setText(intent.getStringExtra("BookDiscription"));
        bookNo = intent.getIntExtra("BookNo",1);

        bookimageUrl = intent.getStringExtra("ImageUrl");
        bookURL = intent.getStringExtra("BookURL");

        Glide.with(this).load(bookimageUrl).into(bookCoverImage);

        fileName = nameOfBook  + ".pdf";    //".pdf" is required otherwise the pdf wont open

        readBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpdf = new Intent(getApplicationContext(),ViewPdfThroughURL.class);
                intentpdf.putExtra("BookURL",bookURL);
                intentpdf.putExtra("Book_Name",nameOfBook);
                startActivity(intentpdf);
            }
        });

        downloadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BookURL->",bookURL);
                Toast.makeText(BookActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

                try {
                    url = new URL(bookURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url + ""));
                request.setTitle(fileName);
                request.setMimeType("application/pdf");
                request.allowScanningByMediaScanner();
                request.setAllowedOverMetered(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "AldoFiles/" + fileName);
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

            }
        });

        circleMenuView = findViewById(R.id.circular_menu);

        GlobalMenu myMenu;
        myMenu = new GlobalMenu(BookActivity.this);
        myMenu.menuClickListners(circleMenuView);

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