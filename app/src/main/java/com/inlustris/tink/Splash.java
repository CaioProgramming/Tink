package com.inlustris.tink;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

public class Splash extends AppCompatActivity {

    private android.widget.LinearLayout background;
    private android.widget.TextView appname;
    private android.widget.TextView question;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Typeface font = Typeface.createFromAsset(this.getAssets(),"fonts/Baloo-Regular.ttf");
        this.question = findViewById(R.id.question);
        this.appname = findViewById(R.id.appname);
        question.setTypeface(font);
        appname.setTypeface(font);
        this.background = findViewById(R.id.back);

        CountDownTimer timer = new CountDownTimer(3000,100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                background.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                int cx = background.getRight();
                int cy = background.getBottom();
                int radius = Math.max(background.getWidth(), background.getHeight());
                Animator anim = ViewAnimationUtils.createCircularReveal(background, cx, cy,
                        50, radius);
                anim.setDuration(1000);
                background.setVisibility(View.VISIBLE);
                anim.start();


                CountDownTimer timer2 = new CountDownTimer(2000,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Intent i = new Intent(activity,MainActivity.class);
                        startActivity(i);
                        activity.finish();
                    }
                }.start();

            }
        }.start();



    }
}
