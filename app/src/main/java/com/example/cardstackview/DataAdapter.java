package com.example.cardstackview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context mContext;
    private List<Data> mDataList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView titleText;
        TextView majorText;
        TextView deadlineText;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            titleText=(TextView)view.findViewById(R.id.data_title);
            majorText=(TextView)view.findViewById(R.id.data_major);
            deadlineText=(TextView)view.findViewById(R.id.data_deadline);
        }
    }
    public DataAdapter(List<Data> dataList){
        mDataList=dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext==null){
            mContext=viewGroup.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.data_item,
                viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Data data=mDataList.get(i);
        viewHolder.titleText.setText(data.getTitle());
        viewHolder.deadlineText.setText(data.getDeadLine());
        viewHolder.majorText.setText(data.getMajor());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
