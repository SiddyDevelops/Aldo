package com.siddydevelops.aldo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterDSA;
import com.siddydevelops.aldo.RecyclerViewAD.RecyclerAdapterRefBooks;

import java.util.ArrayList;
import java.util.List;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class HomePage extends AppCompatActivity {

    ImageView logout;
    LinearLayout referenceBookButton;
    LinearLayout resourcesButton;
    LinearLayout questionBankButton;
    LinearLayout aboutUsButton;

    CardView ds_view;
    CardView searching_view;
    CardView sorting_view;
    CardView dbms_view;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        View overlay = findViewById(R.id.layout_home_page);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.translucent));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        dialog = new Dialog(HomePage.this);
        dialog.setContentView(R.layout.about_us_item);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.inset_bg));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation_for_dia;
        Button closeButton = dialog.findViewById(R.id.dialogButton);
        ImageView mePhoto = dialog.findViewById(R.id.mePhoto);
        ImageView github = dialog.findViewById(R.id.githubIV);
        ImageView linkedin = dialog.findViewById(R.id.linkIV);
        ImageView instagram = dialog.findViewById(R.id.instagramIV);
        mePhoto.setClipToOutline(true);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Thank you for using my App.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Hold On!", Toast.LENGTH_SHORT).show();
                String url = "https://github.com/SiddyDevelops";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try{
                    startActivity(intent);
                }catch (Exception e)
                {
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Hold On!", Toast.LENGTH_SHORT).show();
                String url = "https://www.instagram.com/_siddy_08_/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try{
                    startActivity(intent);
                }catch (Exception e)
                {
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Hold On!", Toast.LENGTH_SHORT).show();
                String url = "https://www.linkedin.com/in/siddharth-singh-08/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try{
                    startActivity(intent);
                }catch (Exception e)
                {
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });



        logout = findViewById(R.id.logout);

        referenceBookButton = findViewById(R.id.referenceBookButton);
        resourcesButton = findViewById(R.id.resourcesLinearLayout);
        questionBankButton = findViewById(R.id.questions);
        aboutUsButton = findViewById(R.id.aboutUsLinearLayout);

        ds_view = findViewById(R.id.ds_view);
        searching_view = findViewById(R.id.searching_view);
        sorting_view = findViewById(R.id.sorting_view);
        dbms_view = findViewById(R.id.dbms_view);

        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),SplashScreenActivity.class));
            }
        });

        referenceBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReferenceBookActivity.class));
            }
        });

        resourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResourcesActivity.class));
            }
        });

        questionBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QuestionsActivity.class));
            }
        });

        ds_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                intent.putExtra("Content","Data Structures");
                startActivity(intent);
            }
        });

        searching_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                intent.putExtra("Content","Searching Algorithms");
                startActivity(intent);
            }
        });

        sorting_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                intent.putExtra("Content","Sorting Algorithms");
                startActivity(intent);
            }
        });

        dbms_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Coming Soon!", Toast.LENGTH_LONG).show();
            }
        });

    }
}