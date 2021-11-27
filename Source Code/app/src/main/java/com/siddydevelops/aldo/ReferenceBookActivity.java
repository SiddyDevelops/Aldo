package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ramotion.circlemenu.CircleMenuView;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterRefBooks;

import java.util.ArrayList;
import java.util.List;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class ReferenceBookActivity extends AppCompatActivity {

    RecyclerView refernceBookRecyclerView;
    FirebaseFirestore firebaseFirestore;

    List<String> bookName;
    List<String> authorName;
    List<String> bookCover;
    List<String> bookDiscription;

    List<String> bookURL;

    WP10ProgressBar wp10ProgressBar;

    CircleMenuView circleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_book);

        View overlay = findViewById(R.id.layout_referenceBooks);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toolbar toolbar = findViewById(R.id.toolbarRefBooks);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wp10ProgressBar = findViewById(R.id.progressBar2);
        wp10ProgressBar.showProgressBar();

        firebaseFirestore = FirebaseFirestore.getInstance();

        refernceBookRecyclerView = findViewById(R.id.refBookRecyclerView);

        bookName = new ArrayList<>();
        authorName = new ArrayList<>();
        bookCover = new ArrayList<>();
        bookDiscription = new ArrayList<>();
        bookURL = new ArrayList<>();

        firebaseFirestore.collection("ReferenceBooks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document != null)
                                {
                                    bookName.add(document.getString("BookName"));
                                    authorName.add(document.getString("AuthorName"));
                                    bookCover.add(document.getString("BookCover"));
                                    bookDiscription.add(document.getString("BookDiscription"));
                                    bookURL.add(document.getString("BookUrl"));
                                }
                            }
                            wp10ProgressBar.hideProgressBar();
                            refernceBookRecyclerView.setLayoutManager(new LinearLayoutManager(ReferenceBookActivity.this));
                            refernceBookRecyclerView.setAdapter(new RecyclerAdapterRefBooks(bookName, authorName, bookCover, bookDiscription, bookURL));
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

        circleMenuView = findViewById(R.id.circular_menu_refBooks);

        GlobalMenu myMenu;
        myMenu = new GlobalMenu(ReferenceBookActivity.this);
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