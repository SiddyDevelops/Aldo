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

import com.bumptech.glide.load.Options;
import com.ramotion.circlemenu.CircleMenuView;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.Font;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CodeViewActivity extends AppCompatActivity {

    CodeView codeView;
    Intent intent;
    String code;

    CircleMenuView circleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);

        View overlay = findViewById(R.id.layout_code);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.translucent));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toolbar toolbar = findViewById(R.id.toolbarCode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codeView = findViewById(R.id.code_view);

        circleMenuView = findViewById(R.id.circular_menu_codeView);

        GlobalMenu myMenu;
        myMenu = new GlobalMenu(CodeViewActivity.this);
        myMenu.menuClickListners(circleMenuView);

        intent = getIntent();

        code = intent.getStringExtra("Code");

        codeView.setCode(code.replace(",!","\n"));            //This works
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