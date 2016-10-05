package com.krstics.watchreminder.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Next4WeeksPremiersAdapter extends RecyclerView.Adapter<Next4WeeksPremiersAdapter.Holder> {

    private List<EpisodeListData> episodeListDatas;
    private ShowsDB db;

    public Next4WeeksPremiersAdapter(ShowsDB db){
        episodeListDatas = new ArrayList<>();
        this.db = db;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_five_item, null, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        EpisodeListData episode = episodeListDatas.get(position);

        if(episode.getShowBanner() != null){
            holder.posterNotAvailable.setVisibility(View.INVISIBLE);
            holder.showImage.setImageBitmap(episode.getShowBanner());
        }
        else if(episode.getEpisodeBanner() != null){
            holder.posterNotAvailable.setVisibility(View.INVISIBLE);
            holder.showImage.setImageBitmap(episode.getEpisodeBanner());
        }
        else{
            holder.posterNotAvailable.setVisibility(View.VISIBLE);
            holder.showImage.setImageBitmap(null);
        }
        holder.showName.setText(episode.getShowName());
        holder.episodeName.setText(episode.getEpisodeName());
        holder.seasonNo.setText(episode.getSeasonNumber());
        holder.episodeNo.setText(episode.getEpisodeNumber());
        holder.airsDate.setText(episode.getAirsDate());
        holder.airsTime.setText(episode.getAirsTime());
    }

    @Override
    public int getItemCount() {
        return episodeListDatas.size();
    }

    public void addEpisode(EpisodeListData episode){
        episodeListDatas.add(episode);
        Collections.sort(episodeListDatas);
        notifyDataSetChanged();
    }


    public void deleteAllEpisodes() {
        episodeListDatas.clear();
        Collections.sort(episodeListDatas);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        private ImageView showImage;
        private TextView posterNotAvailable;
        private TextView showName;
        private TextView episodeName;
        private TextView episodeNo;
        private TextView airsTime;
        private TextView airsDate;
        private TextView seasonNo;

        Holder(View itemView) {
            super(itemView);

            showImage = (ImageView) itemView.findViewById(R.id.showImageFive);
            posterNotAvailable = (TextView)itemView.findViewById(R.id.posterNotAvailableFive);
            showName = (TextView) itemView.findViewById(R.id.showNameFive);
            episodeName = (TextView) itemView.findViewById(R.id.episodeNameFiveText);
            episodeNo = (TextView) itemView.findViewById(R.id.episodeNumberFive);
            airsTime = (TextView) itemView.findViewById(R.id.airsTimeTextFive);
            airsDate = (TextView) itemView.findViewById(R.id.airsDateTextFive);
            seasonNo = (TextView) itemView.findViewById(R.id.seasonNumberTextFive);
        }
    }
}
