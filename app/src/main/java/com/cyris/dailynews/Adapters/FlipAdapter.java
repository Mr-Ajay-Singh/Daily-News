package com.cyris.dailynews.Adapters;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.dailynews.AsyncTasks.DeleteStorageAsyncTask;
import com.cyris.dailynews.AsyncTasks.StorageAsyncTask;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Fragments.BookmarekShowPage;
import com.cyris.dailynews.LoadSuggested;
import com.cyris.dailynews.MainActivity;
import com.cyris.dailynews.R;
import com.cyris.dailynews.WebViewActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.BlurTransformation;


public class FlipAdapter extends RecyclerView.Adapter<FlipAdapter.FlipViewHolder> {


    List<TaskEntity> newsList;
    Context context;
    LinearLayout bottomSetting;
    TabLayout newsTypeTab;
    StorageAsyncTask bookMarkStorage;
    Fragment f;
    ImageView scrollToTop;
    View view;


    public FlipAdapter(List<TaskEntity> newsGetterSetters, Context ctx,View view1) {
        this.newsList = newsGetterSetters;
        this.context = ctx;
        this.view = view1;
        newsTypeTab = ((Activity)context).findViewById(R.id.tabLayoutInMain);
        bottomSetting = ((Activity)context).findViewById(R.id.bottomSetting);
        scrollToTop = view.findViewById(R.id.scrollToTop);
        bookMarkStorage = new StorageAsyncTask(context, ConstantsDatabaseName.BookMark);


    }
    @NonNull
    @Override
    public FlipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View view = inflater.inflate(R.layout.swipe_holder,parent,false);
        View view = inflater.inflate(R.layout.flip_holder,parent,false);

        return new FlipAdapter.FlipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlipViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).title);
        holder.description.setText(newsList.get(position).description);
        holder.loadImage(newsList.get(position).urlToImage);
        holder.OnClickData(newsList.get(position).url);
        holder.source.setText(newsList.get(position).source);
        holder.clickTextFlip.setText(newsList.get(position).source);
        holder.BookMarkData(newsList.get(position));
        holder.OnHoldCardView(newsList.get(position).url);
        if(!(context instanceof LoadSuggested))
            holder.onSettingDataClick(newsList.get(position).url);






    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class FlipViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,source,clickTextFlip;
        LinearLayout clickFlip;
        ImageView saveArticleData;
        ImageView image,miniImage;
        CardView cardView;


        public FlipViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleFlipHolder);
            description = itemView.findViewById(R.id.descriptionFlipHolder);
            image = itemView.findViewById(R.id.imageFlipHolder);
            cardView = itemView.findViewById(R.id.flipHolderCard);
            source = itemView.findViewById(R.id.flipSource);
            clickFlip = itemView.findViewById(R.id.clickFlip);
            clickTextFlip = itemView.findViewById(R.id.clickTextFlip);
            miniImage = itemView.findViewById(R.id.imageFlipHolderDown);

            //saveArticleData = ((Activity)context).findViewById(R.id.saveArticleData);


            Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/raleway_medium.ttf");
            title.setTypeface(typeface);
            Typeface typeface2 = Typeface.createFromAsset(context.getAssets(),"fonts/rabelo_regular.ttf");
            description.setTypeface(typeface2);
            Typeface typeface3 = Typeface.createFromAsset(context.getAssets(),"fonts/zoika.ttf");
            source.setTypeface(typeface3);
        }

        public void loadImage(String url)
        {
            if(!url.equals(null)&& !url.isEmpty())
            {

                Log.i("urlofimage",url+"hello");
                Picasso.with(context).load(url).into(image);
                Picasso.with(context).load(url).fit().transform(new BlurTransformation(context,25,1)).into(miniImage);
            }

        }
        public void OnClickData(final String url)
        {

            clickFlip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                }
            });

        }



        public void onSettingDataClick(final String url) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if(actionBar.isShowing())
                        actionBar.hide();
                  //  else
                       // actionBar.show(); */
                    if(newsTypeTab.getVisibility() != View.INVISIBLE)
                    {
                        AnimationSet animationSet = new AnimationSet(context,null);
                        animationSet.addAnimation(new AlphaAnimation(1,(float)0.1));
                        animationSet.addAnimation(new TranslateAnimation(0,0,0,-newsTypeTab.getHeight()));
                        animationSet.setDuration(300);
                        newsTypeTab.startAnimation(animationSet);
                        newsTypeTab.setVisibility(View.INVISIBLE);


                        AnimationSet animationSetBottom = new AnimationSet(context,null);
                        animationSetBottom.addAnimation(new AlphaAnimation(1,(float)0.1));
                        animationSetBottom.addAnimation(new TranslateAnimation(0,0,0,bottomSetting.getHeight()));
                        animationSetBottom.setDuration(300);
                        bottomSetting.startAnimation(animationSetBottom);
                        bottomSetting.setVisibility(View.INVISIBLE);

                        AnimationSet animationSetScroll = new AnimationSet(context,null);
                        animationSetScroll.addAnimation(new AlphaAnimation((float)0.1,1));
                        animationSetScroll.addAnimation(new TranslateAnimation(0,0,scrollToTop.getHeight(),0));
                        animationSetScroll.setDuration(300);
                        scrollToTop.startAnimation(animationSetBottom);
                        scrollToTop.setVisibility(View.INVISIBLE);

                    }
                    else {
                        AnimationSet animationSet = new AnimationSet(context,null);
                        animationSet.addAnimation(new AlphaAnimation((float)0.1,1));
                        animationSet.addAnimation(new TranslateAnimation(0,0,-newsTypeTab.getHeight(),0));
                        animationSet.setDuration(300);
                        newsTypeTab.startAnimation(animationSet);
                        newsTypeTab.setVisibility(View.VISIBLE);


                        AnimationSet animationSetBottom = new AnimationSet(context,null);
                        animationSetBottom.addAnimation(new AlphaAnimation((float)0.1,1));
                        animationSetBottom.addAnimation(new TranslateAnimation(0,0,bottomSetting.getHeight(),0));
                        animationSetBottom.setDuration(300);
                        bottomSetting.startAnimation(animationSetBottom);
                        bottomSetting.setVisibility(View.VISIBLE);


                        AnimationSet animationSetScroll = new AnimationSet(context,null);
                        animationSetScroll.addAnimation(new AlphaAnimation((float)0.1,1));
                        animationSetScroll.addAnimation(new TranslateAnimation(0,0,scrollToTop.getHeight(),0));
                        animationSetScroll.setDuration(300);
                        scrollToTop.startAnimation(animationSetBottom);
                        scrollToTop.setVisibility(View.VISIBLE);

                    }

                }
            });




        }

        public void OnHoldCardView(final String url)
        {
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                    return true;
                }
            });
        }


        public void HideBar()
        {
            if(newsTypeTab.getVisibility()!=View.INVISIBLE)
            {
                AnimationSet animationSet = new AnimationSet(context,null);
                animationSet.addAnimation(new AlphaAnimation(1,(float)0.1));
                animationSet.addAnimation(new TranslateAnimation(0,0,0,-newsTypeTab.getHeight()));
                animationSet.setDuration(300);
                newsTypeTab.startAnimation(animationSet);
                newsTypeTab.setVisibility(View.INVISIBLE);


                AnimationSet animationSetBottom = new AnimationSet(context,null);
                animationSetBottom.addAnimation(new AlphaAnimation(1,(float)0.1));
                animationSetBottom.addAnimation(new TranslateAnimation(0,0,0,bottomSetting.getHeight()));
                animationSetBottom.setDuration(300);
                bottomSetting.startAnimation(animationSetBottom);
                bottomSetting.setVisibility(View.INVISIBLE);
            }

        }

        public void BookmarkRemove(final TaskEntity entity)
        {
            title.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    title.setTextColor(context.getColor(R.color.black));
                    DeleteStorageAsyncTask task = new DeleteStorageAsyncTask(context,ConstantsDatabaseName.BookMark);
                    List<TaskEntity> taskEntities = new ArrayList<>();
                    taskEntities.add(entity);
                    task.execute(taskEntities);
                    Toast.makeText(context,"Bookmarked Canceled",Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void BookMarkData(final TaskEntity entity)
        {
            title.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if ((title.getCurrentTextColor() != context.getResources().getColor(R.color.skyBlue)))
                    {
                        title.setTextColor(context.getColor(R.color.skyBlue));
                        StorageAsyncTask task = new StorageAsyncTask(context,ConstantsDatabaseName.BookMark);
                        List<TaskEntity> taskEntities = new ArrayList<>();
                        taskEntities.add(entity);
                        task.execute(taskEntities);
                        Toast.makeText(context,"Bookmarked",Toast.LENGTH_SHORT).show();
                    }
                    else if (title.getCurrentTextColor() == context.getResources().getColor(R.color.skyBlue))
                    {
                        title.setTextColor(context.getColor(R.color.black));
                        DeleteStorageAsyncTask task = new DeleteStorageAsyncTask(context,ConstantsDatabaseName.BookMark);
                        List<TaskEntity> taskEntities = new ArrayList<>();
                        taskEntities.add(entity);
                        task.execute(taskEntities);
                        Toast.makeText(context,"Bookmark Removed",Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
    }




}
