package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutorCompletionService;

public class AdapterSub extends RecyclerView.Adapter<AdapterSub.MyViewHolderSub> {
    String TAG = "Adapter";
    private LayoutInflater inflater;
    private ArrayList<ModelSub> imageModelArrayList;


    public AdapterSub(Context ctx, ArrayList<ModelSub> imageModelArrayList){
        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        Log.e(TAG, "Created Adapter");
    }

    @Override
    public MyViewHolderSub onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_item_subscriptions, parent, false);
        MyViewHolderSub holder = new MyViewHolderSub(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderSub holder, int position) {
        holder.round_iv.setImageResource(R.drawable.logo);
        holder.name.setText(imageModelArrayList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolderSub extends RecyclerView.ViewHolder{

        TextView name;
        ImageView round_iv;

        public MyViewHolderSub(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_username);
            round_iv = (ImageView) itemView.findViewById(R.id.round_iv);
            round_iv.setImageResource(R.drawable.logo);
        }

    }
}

