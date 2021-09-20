package com.cyris.dailynews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.dailynews.Database.TaskLoadSuggested;
import com.cyris.dailynews.LoadSuggested;
import com.cyris.dailynews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuggestedTopicAdapter extends RecyclerView.Adapter<SuggestedTopicAdapter.SuggestedViewHolder> {

    List<TaskLoadSuggested> listSuggested;
    Context context;

    public SuggestedTopicAdapter(Context ctx, List<TaskLoadSuggested> list)
    {
        this.context = ctx;
        this.listSuggested = list;

    }
    @NonNull
    @Override
    public SuggestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.i("helloasd","i am in 2");
        //View view = inflater.inflate(R.layout.swipe_holder,parent,false);
        View view = inflater.inflate(R.layout.suggested_topic_holder,parent,false);
        return new SuggestedTopicAdapter.SuggestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedViewHolder holder, int position) {
        Log.i("helloadsed",listSuggested.get(position).image);
        holder.suggestedText.setText(listSuggested.get(position).title);
        holder.SetImage(listSuggested.get(position).image);
        holder.onClickListner(listSuggested.get(position).link);

    }

    @Override
    public int getItemCount() {
        return listSuggested.size();
    }

    class SuggestedViewHolder extends RecyclerView.ViewHolder {
        ImageView suggestedImage;
        TextView suggestedText;
        public SuggestedViewHolder(@NonNull View itemView) {
            super(itemView);
            suggestedImage = itemView.findViewById(R.id.suggestedImageHolder);
            suggestedText = itemView.findViewById(R.id.suggestedTextHolder);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/zoika.ttf");
            suggestedText.setTypeface(typeface);
        }

        public void SetImage(String url)
        {
            Picasso.with(context).load(url).fit().centerCrop().into(suggestedImage);
            suggestedImage.setPadding(12,12,12,12);
        }

        public void onClickListner(final String data)
        {
            suggestedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoadSuggested.class);
                    intent.putExtra("link",data);
                    context.startActivity(intent);
                }
            });
        }
    }
}
