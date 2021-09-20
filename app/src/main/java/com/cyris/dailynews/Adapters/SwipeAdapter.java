package com.cyris.dailynews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.R;
import com.cyris.dailynews.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder> {

    List<TaskEntity> newsList;
    Context context;

    public SwipeAdapter(List<TaskEntity> newsGetterSetters, Context ctx) {
        this.newsList = newsGetterSetters;
        this.context = ctx;
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View view = inflater.inflate(R.layout.swipe_holder,parent,false);
        View view = inflater.inflate(R.layout.swipe_holder,parent,false);

        return new SwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).title);
        holder.description.setText(newsList.get(position).description);
        holder.loadImage(newsList.get(position).urlToImage);
        holder.OnClickData(newsList.get(position).url);
        holder.source.setText(newsList.get(position).source);
       // Log.i("helloWorld",newsList.get(position).publishedAt);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class SwipeViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,source;
        ImageView image;
        CardView cardView;
        public SwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleSwipeHolder);
            description = itemView.findViewById(R.id.descriptionSwipeHolder);
            image = itemView.findViewById(R.id.imageSwipeHolder);
            cardView = itemView.findViewById(R.id.swipeHolderCard);
            source = itemView.findViewById(R.id.swipeSource);
        }

        public void loadImage(String url)
        {
        if(!url.equals(null)&& !url.isEmpty())
            {
                Log.i("urlofimage",url+"hello");
                Picasso.with(context).load(url).into(image);
            }

        }
        public void OnClickData(final String url)
        {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                }
            });

        }
    }
}
