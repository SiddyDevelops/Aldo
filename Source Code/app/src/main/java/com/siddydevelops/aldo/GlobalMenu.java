package com.siddydevelops.aldo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.ramotion.circlemenu.CircleMenuView;

import org.jetbrains.annotations.NotNull;

public class GlobalMenu extends AppCompatActivity {

    Context mContext;

    public GlobalMenu(Context context)
    {
        this.mContext = context;
    }

    public void dealyedActivityStart(Class cls)
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mContext.startActivity(new Intent(mContext, cls));
            }
        }, 1000);
    }

    public void menuClickListners(CircleMenuView circleMenuView) {
        circleMenuView.setEventListener(new CircleMenuView.EventListener() {

            @Override
            public void onMenuOpenAnimationStart(@NonNull @NotNull CircleMenuView view) {
                super.onMenuOpenAnimationStart(view);
                circleMenuView.animate().translationX(-370).setStartDelay(0).setDuration(10);
                circleMenuView.animate().translationY(-300).setStartDelay(0).setDuration(10);
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull @NotNull CircleMenuView view) {
                super.onMenuOpenAnimationEnd(view);

            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull @NotNull CircleMenuView view) {
                super.onMenuCloseAnimationStart(view);
                circleMenuView.animate().translationX(10).setStartDelay(0).setDuration(10);
                circleMenuView.animate().translationY(10).setStartDelay(0).setDuration(10);
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull @NotNull CircleMenuView view) {
                super.onMenuCloseAnimationEnd(view);
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationStart(view, buttonIndex);

                switch (buttonIndex) {
                    case 0:
                        dealyedActivityStart(HomePage.class);
                        break;
                    case 1:
                        dealyedActivityStart(QuestionsActivity.class);
                        break;
                    case 2:
                        dealyedActivityStart(ReferenceBookActivity.class);
                        break;
                    case 3:
                        dealyedActivityStart(ResourcesActivity.class);
                        finish();
                        break;
                    case 4:
                        Toast.makeText(mContext, "Developed By Siddharth Singh.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull @NotNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationEnd(view, buttonIndex);
                circleMenuView.animate().translationX(10).setStartDelay(0).setDuration(10);
                circleMenuView.animate().translationY(10).setStartDelay(0).setDuration(10);
            }

        });

    }
}

