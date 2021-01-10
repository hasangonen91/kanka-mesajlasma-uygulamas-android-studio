package com.example.kanka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    List<String> list;
    Activity activity;
    String userName;


    public UserAdapter(Context context, List<String> list, Activity activity,String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName = userName;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(list.get(position).toString());
        holder.userAnaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ChatActivity.class);
                intent.putExtra("username",userName);
                intent.putExtra("othername",list.get(position).toString());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView textView;
        LinearLayout userAnaLayout;
        public ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.userName);
            userAnaLayout = itemView.findViewById(R.id.userAnaLayout);

        }

    }
}
