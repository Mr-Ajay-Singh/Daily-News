package com.cyris.dailynews;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    String url;

    ActionBar actionBar;
    SharedPreferences preferences;
    private GlideDrawableImageViewTarget imageViewTarget;
    ImageView loadingInWebView;
    boolean webLoad=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_layout);
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.action_bar_color));
        url = getIntent().getStringExtra("url");
        webView = findViewById(R.id.webView);
        loadingInWebView = findViewById(R.id.loadingInWebView);
        final AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.loading_vector_animated);
        loadingInWebView.setImageDrawable(animationDrawable);
        animationDrawable.start();


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        webLoad = preferences.getBoolean("websiteSpeed",false);
     //   imageViewTarget = new GlideDrawableImageViewTarget(imageView);


       /* Glide.with(this)
                .load(R.drawable.load_13)
                // .placeholder(R.drawable.loading_data)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget); */

        webView.getSettings().setJavaScriptEnabled(webLoad);
        webView.getSettings().setBlockNetworkImage(!webLoad);
        webView.getSettings().setEnableSmoothTransition(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100)
                {
                    loadingInWebView.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settingMenu)
        {
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.getSettings().setJavaScriptEnabled(webLoad);
        webView.getSettings().setBlockNetworkImage(!webLoad);
        webView.reload();
    }
}
