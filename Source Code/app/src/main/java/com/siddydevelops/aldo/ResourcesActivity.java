package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ramotion.circlemenu.CircleMenuView;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterRefBooks;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterResources;

import java.util.ArrayList;
import java.util.List;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class ResourcesActivity extends AppCompatActivity {

    RecyclerView resourceRecyclerView;
    FirebaseFirestore firebaseFirestore;

    CircleMenuView circleMenuView;

    WP10ProgressBar progressBar;

    private List<String> courseName;
    private List<String> instructor;
    private List<String> platform;
    private List<String> price;
    private List<String> f_or_paid;
    private List<String> link;
    private List<String> coverURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        View overlay = findViewById(R.id.layout_resources);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toolbar toolbar = findViewById(R.id.toolbarResources);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resourceRecyclerView = findViewById(R.id.resources_recyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBarRes);

        courseName = new ArrayList<>();
        instructor = new ArrayList<>();
        platform = new ArrayList<>();
        price = new ArrayList<>();
        f_or_paid = new ArrayList<>();
        link = new ArrayList<>();
        coverURL = new ArrayList<>();

        progressBar.showProgressBar();

        firebaseFirestore.collection("RESOURCES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document != null)
                                {
                                    courseName.add(document.getString("CourseName"));
                                    instructor.add(document.getString("Instructor"));
                                    platform.add(document.getString("Platform"));
                                    price.add(document.getString("Price"));
                                    f_or_paid.add(document.getString("ForP"));
                                    link.add(document.getString("Link"));
                                    coverURL.add(document.getString("CoverURL"));
                                }
                            }
                            resourceRecyclerView.setLayoutManager(new LinearLayoutManager(ResourcesActivity.this));
                            resourceRecyclerView.setAdapter(new RecyclerAdapterResources(courseName, instructor, platform, price, f_or_paid, link,coverURL));
                            progressBar.hideProgressBar();
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

        circleMenuView = findViewById(R.id.circular_menu_resources);
        GlobalMenu myMenu;
        myMenu = new GlobalMenu(ResourcesActivity.this);
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