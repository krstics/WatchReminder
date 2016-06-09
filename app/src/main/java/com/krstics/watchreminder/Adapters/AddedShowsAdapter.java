package com.krstics.watchreminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class AddedShowsAdapter extends RecyclerView.Adapter<AddedShowsAdapter.Holder> implements View.OnClickListener {
    private String TAG = AddedShowsAdapter.class.getSimpleName();
    private List<ShowListData> showListData;
    private Context context;

    public AddedShowsAdapter(Context context) {
        this.context = context;
        showListData = new ArrayList<>();
    }

    @Override
    public AddedShowsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_two_item, null, false));
    }

    @Override
    public void onBindViewHolder(AddedShowsAdapter.Holder holder, int position) {
        ShowListData show = showListData.get(position);

        holder.showName.setText(show.getSeriesName());
        holder.airedDate.setText(show.getFirstAired());
        if(show.getBitmap() == null) {
            holder.posterNotAvailable.setVisibility(View.VISIBLE);
            holder.showImage.setImageBitmap(null);
        }
        else {
            holder.showImage.setImageBitmap(show.getBitmap());
            holder.posterNotAvailable.setVisibility(View.INVISIBLE);
        }
        holder.statusTextView.setText(show.getStatus());
        holder.airstTime.setText(show.getAirsTime());
        holder.airsDay.setText(show.getAirsDayOfWeek());
        holder.overview.setText(show.getOverview());
    }

    @Override
    public int getItemCount() {
        return showListData.size();
    }

    @Override
    public void onClick(View v) {

    }

    public void addShow(ShowListData show){
        showListData.add(show);
        notifyDataSetChanged();
    }

    public void deleteAllShows() {
        showListData.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{

        private ImageView showImage;
        private TextView showName;
        private TextView airedDate;
        private TextView posterNotAvailable;
        private TextView statusTextView;
        private TextView airstTime;
        private TextView airsDay;
        private TextView overview;

        public Holder(View itemView) {
            super(itemView);

            showImage = (ImageView)itemView.findViewById(R.id.showImageTWO);
            showName = (TextView)itemView.findViewById(R.id.showNameTWO);
            airedDate = (TextView)itemView.findViewById(R.id.firstAiredTWO);
            posterNotAvailable = (TextView)itemView.findViewById(R.id.posterNotAvailableTWO);
            posterNotAvailable.setVisibility(View.INVISIBLE);
            statusTextView = (TextView)itemView.findViewById(R.id.statusTWO);
            airstTime = (TextView)itemView.findViewById(R.id.airsTimeTextTWO);
            airsDay = (TextView)itemView.findViewById(R.id.airsDayOfWeekTextTWO);
            overview = (TextView)itemView.findViewById(R.id.overviewTextTWO);
        }
    }
}
