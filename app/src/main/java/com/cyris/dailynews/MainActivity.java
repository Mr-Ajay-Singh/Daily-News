package com.cyris.dailynews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyris.dailynews.ViewPager.BookmarkAdapter;
import com.cyris.dailynews.ViewPager.SuggestedAdapter;
import com.cyris.dailynews.ViewPager.ViewPagerClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    ViewPager mainPager;
    ViewPagerClass adapter;
    SuggestedAdapter suggestedAdapter;
    BookmarkAdapter bookmarkAdapter;
    ImageView shareScreenShot,settingMenuBottom,suggestedTopicBottom,homeScreen,showBookmark,loadingImage;

    TabLayout tabLayout;
    LinearLayout bottomSetting;
    private int STORAGE_REQUEST_CODE =5;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AllWork();
    }



    private void AllWork() {
        actionBar = getSupportActionBar();
        actionBar.hide();



        bottomSetting = findViewById(R.id.bottomSetting);
        showBookmark = findViewById(R.id.showBookmark);
        shareScreenShot = findViewById(R.id.shareScreenShot);
        settingMenuBottom = findViewById(R.id.settingMenuBottom);
        suggestedTopicBottom = findViewById(R.id.suggestedTopicBottom);
        homeScreen = findViewById(R.id.homeScreen);


        loadingImage = findViewById(R.id.loadingImage);
        mainPager = findViewById(R.id.mainActivityViewPager);
        tabLayout = findViewById(R.id.tabLayoutInMain);
        tabLayout.setupWithViewPager(mainPager);
        tabLayout.setElevation(10);
        tabLayout.setTabIndicatorFullWidth(false);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        bookmarkAdapter = new BookmarkAdapter(getSupportFragmentManager());
        suggestedAdapter = new SuggestedAdapter(getSupportFragmentManager());
        adapter = new ViewPagerClass(getSupportFragmentManager());
        mainPager.setAdapter(adapter);

        getSupportFragmentManager().beginTransaction().add(R.id.mainActivityViewPager,adapter.getItem(mainPager.getCurrentItem()))
                .setCustomAnimations(R.animator.fragment_in,R.animator.fragment_out)
                .commit();

        final AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.loading_vector_animated);
        loadingImage.setImageDrawable(animationDrawable);
        animationDrawable.start();

        // CallPageChangeListner()
        CallMainPage();
        ShareScreenShot();
        CallSetting();
        CallSuggestedTopicBottom();
        CallBookmarkButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void CallMainPage() {


        homeScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {

                    homeScreen.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.home_svg_light));
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    loadingImage.setVisibility(View.VISIBLE);
                    homeScreen.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.home_svg));
                    if(!mainPager.getAdapter().equals(adapter))
                        mainPager.setAdapter(adapter);
                    tabLayout.setVisibility(View.VISIBLE);
                }


                return true;
            }


        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPager.setAdapter(adapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void ShareScreenShot()
    {


        shareScreenShot.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //HideBar();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    shareScreenShot.setImageDrawable(getDrawable(R.drawable.share_svg_light));

                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        shareScreenShot.setImageDrawable(getDrawable(R.drawable.share_svg));
                        ScreenShot.ShareBitmap( mainPager,MainActivity.this);
                    }
                    else
                    {
                        shareScreenShot.setImageDrawable(getDrawable(R.drawable.share_svg));
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
                    }

                }


                return true;
            }
        });
    }



    @SuppressLint("ClickableViewAccessibility")
    public void CallSetting()
    {

        settingMenuBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    settingMenuBottom.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.setting_svg_light));
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    settingMenuBottom.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.setting_svg));
                    Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                    startActivity(intent);
                }

                return true;
        }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public void CallSuggestedTopicBottom()
    {

        suggestedTopicBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                loadingImage.setElevation(1);
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    suggestedTopicBottom.setImageDrawable(getDrawable(R.drawable.suggested_svg_light));

                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    suggestedTopicBottom.setImageDrawable(getDrawable(R.drawable.suggested_svg));
                    mainPager.setAdapter(suggestedAdapter);
                    tabLayout.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void CallBookmarkButton()
    {
        showBookmark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    showBookmark.setImageDrawable(getDrawable(R.drawable.bookmark_svg_light));
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    showBookmark.setImageDrawable(getDrawable(R.drawable.bookmark_svg));
                    mainPager.setAdapter(bookmarkAdapter);
                    tabLayout.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }


    public void HideBar()
    {

            AnimationSet animationSet = new AnimationSet(this,null);
            animationSet.addAnimation(new AlphaAnimation(1,(float)0.1));
            animationSet.addAnimation(new TranslateAnimation(0,0,0,-tabLayout.getHeight()));
            animationSet.setDuration(300);
            tabLayout.startAnimation(animationSet);
            tabLayout.setVisibility(View.INVISIBLE);


            AnimationSet animationSetBottom = new AnimationSet(this,null);
            animationSetBottom.addAnimation(new AlphaAnimation(1,(float)0.1));
            animationSetBottom.addAnimation(new TranslateAnimation(0,0,0,bottomSetting.getHeight()));
            animationSetBottom.setDuration(300);
            bottomSetting.startAnimation(animationSetBottom);
            bottomSetting.setVisibility(View.INVISIBLE);




    }
}
