package com.example.heartrateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView welcomeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        welcomeImageView = (ImageView) findViewById(R.id.welcomeImageView);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2500);
        welcomeImageView.startAnimation(animation);
        animation.setAnimationListener(new AnimationIm());
    }

    private class AnimationIm implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            welcomeImageView.setBackgroundResource(R.drawable.welcome);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    private void skip() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
