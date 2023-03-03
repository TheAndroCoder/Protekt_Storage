package com.theandrocoder.protektstorage.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.theandrocoder.protektstorage.R;

import java.util.List;

public class RecentsRecyclerAdapter extends RecyclerView.Adapter<RecentsRecyclerAdapter.MyViewHolder>{

    private static final String TAG = RecentsRecyclerAdapter.class.getCanonicalName();

    private Context context;
    private List<Uri> imageList;

    public RecentsRecyclerAdapter(Context context, List<Uri> imageList){
        this.context=context;
        this.imageList=imageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater=LayoutInflater.from(this.context).inflate(R.layout.recents_recycler_row,parent,false);
        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG,"Trying to load uri="+imageList.get(position));
        //Picasso.get().load(imageList.get(position)).into(holder.image);
        holder.image.setImageURI(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.card_img);
        }
    }
}
