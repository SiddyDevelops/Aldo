package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ramotion.circlemenu.CircleMenuView;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterDSA;

import java.util.ArrayList;
import java.util.List;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class ContentActivity extends AppCompatActivity {

    Intent intent;

    FirebaseFirestore firebaseFirestore;
    RecyclerView dsaRecyclerView;

    TextView contentTextView;
    String titleActivity;
    String collectionName;

     List<String> dsaName;
     List<String> dsaCoverURL;
     List<String> dsaDescription;
     List<String> dsaNotesURL;
     List<String> dsaWebURL;
     List<String> dsaYTURL;
     List<String> dsaCode;
     List<String> visualization;

     WP10ProgressBar progressBar;

    CircleMenuView circleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        View overlay = findViewById(R.id.layout_content);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.translucent));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbarContent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contentTextView = findViewById(R.id.contentTitle);
        titleActivity = intent.getStringExtra("Content");
        contentTextView.setText(titleActivity);

        progressBar = findViewById(R.id.progressBarContent);
        progressBar.showProgressBar();

        dsaName = new ArrayList<>();
        dsaCoverURL = new ArrayList<>();
        dsaDescription = new ArrayList<>();
        dsaNotesURL = new ArrayList<>();
        dsaWebURL = new ArrayList<>();
        dsaYTURL = new ArrayList<>();
        dsaCode = new ArrayList<>();
        visualization = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        dsaRecyclerView = findViewById(R.id.contentRecyclerView);

        circleMenuView = findViewById(R.id.circular_menu_content);

        GlobalMenu myMenu;
        myMenu = new GlobalMenu(ContentActivity.this);
        myMenu.menuClickListners(circleMenuView);


        getCollection(titleActivity);

    }

    private void getCollection(String contentTitle)
    {
        if(contentTitle.equals("Data Structures"))
        {
            collectionName = "DSA";
        }
        else if(contentTitle.equals("Searching Algorithms"))
        {
            collectionName = "SearchingAlgo";
        }
        else if(contentTitle.equals("Sorting Algorithms"))
        {
            collectionName = "SortingAlgo";
        }
        retriveData();
    }

    private void retriveData() {

        firebaseFirestore.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document != null)
                                {
                                    dsaName.add(document.getString("Name"));
                                    dsaCoverURL.add(document.getString("CoverURL"));
                                    dsaDescription.add(document.getString("Description"));
                                    dsaNotesURL.add(document.getString("NotesURL"));
                                    dsaWebURL.add(document.getString("WebURL"));
                                    dsaYTURL.add(document.getString("YTURL"));
                                    dsaCode.add(document.getString("Code"));
                                    visualization.add(document.getString("VisURL"));
                                }
                            }
                            progressBar.hideProgressBar();
                            dsaRecyclerView.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                            dsaRecyclerView.setAdapter(new RecyclerAdapterDSA(dsaName,dsaCoverURL,dsaDescription,dsaNotesURL,dsaWebURL,dsaYTURL,dsaCode,visualization));
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
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