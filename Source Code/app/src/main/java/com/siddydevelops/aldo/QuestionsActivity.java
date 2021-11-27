package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ramotion.circlemenu.CircleMenuView;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterQuestions;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterRefBooks;

import java.util.ArrayList;
import java.util.List;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class QuestionsActivity extends AppCompatActivity {

    RecyclerView questionsRecyclerView;
    FirebaseFirestore firebaseFirestore;

    List<String> question;
    List<String> answerURL;

    WP10ProgressBar wp10ProgressBar;

    CircleMenuView circleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        View overlay = findViewById(R.id.layout_questions);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toast.makeText(this, "Swipe Right To View More Questions!", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbarQuestions);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        question = new ArrayList<>();
        answerURL = new ArrayList<>();

        wp10ProgressBar = findViewById(R.id.progressBarQuestions);
        wp10ProgressBar.showProgressBar();

        firebaseFirestore = FirebaseFirestore.getInstance();
        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsRecyclerView);

        firebaseFirestore.collection("QUESTIONS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document != null)
                                {
                                    question.add(document.getString("Question"));
                                    answerURL.add(document.getString("AnsURL"));
                                }
                            }

                            wp10ProgressBar.hideProgressBar();
                            questionsRecyclerView.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            questionsRecyclerView.setAdapter(new RecyclerAdapterQuestions(question,answerURL));
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

        circleMenuView = findViewById(R.id.circular_menu_refBooks);

        GlobalMenu myMenu;
        myMenu = new GlobalMenu(QuestionsActivity.this);
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