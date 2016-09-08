package com.krstics.watchreminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Episode;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class TodayEpisodesAdapter extends RecyclerView.Adapter<TodayEpisodesAdapter.Holder> implements View.OnClickListener{

    private String TAG = TodayEpisodesAdapter.class.getSimpleName();
    private List<Episode> episodeList;
    private Context context;
    private ShowsDB showsDB;

    public TodayEpisodesAdapter(Context cont, ShowsDB db)
    {
        this.context = cont;
        showsDB = db;
        episodeList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_three_item, null, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    public class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
