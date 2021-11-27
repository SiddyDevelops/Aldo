package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class ViewPdfThroughURL extends AppCompatActivity {

    PDFView pdfView;
    WP10ProgressBar progressBar;

    String book_pdfURL;
    FloatingActionButton fabDownload;

    FirebaseFirestore firebaseFirestore;

    //Reema Theraja = http://www.gbv.de/dms/tib-ub-hannover/687881390.pdf
    //Algorithms fourth = http://index-of.es/Varios-2/Algorithms%204th%20Edition.pdf
    //DataStructures and Algorithms made easy = https://www.techtud.com/sites/default/files/public/share/algorithm-book-by-karumanchi.pdf

    //Get https:// File ONLY
    //String pdfURL = "http://dspace.mit.edu/bitstream/handle/1721.1/41487/AI_WP_316.pdf";
    //String pdfURL = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
    //String pdfURL = "https://www.cet.edu.in/noticefiles/280_DS%20Complete.pdf";
    String fileName;
    private URL url = null;

    int RequestCode = 656;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_through_url);

        View overlay = findViewById(R.id.layout_pdfView);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toast.makeText(this, "PDF is loading please be Patient!", Toast.LENGTH_LONG).show();

        firebaseFirestore = FirebaseFirestore.getInstance();
        fabDownload = findViewById(R.id.fabDownload);

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPdfThroughURL.this, "Downloading...", Toast.LENGTH_SHORT).show();
                downloadPdf();
                Log.i("URLL__>",book_pdfURL);
            }
        });

        Intent intent = getIntent();
        book_pdfURL = intent.getStringExtra("BookURL");
        fileName = intent.getStringExtra("Book_Name")  + ".pdf";    //".pdf" is required otherwise the pdf wont open

        progressBar = findViewById(R.id.progressBar3);
        pdfView = findViewById(R.id.idPDFView);
        //Get URL for book;
        progressBar.showProgressBar();

        getPdf();
    }

    private void downloadPdf() {

        try {
            url = new URL(book_pdfURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},RequestCode);
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

    private void getPdf()
    {
        new RetrivePDFfromUrl().execute(book_pdfURL);
    }

    // create an async task class for loading pdf file from URL.
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                Log.i("ERROR",e.getMessage());
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();

            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.hideProgressBar();
                }
            }, 3000);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            //Request permission again
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RequestCode);
        }
    }
}