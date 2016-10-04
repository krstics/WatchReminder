package com.krstics.watchreminder.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class TodayEpisodesAdapter extends RecyclerView.Adapter<TodayEpisodesAdapter.Holder> implements View.OnClickListener{

    private List<EpisodeListData> episodeListData;

    public TodayEpisodesAdapter()
    {
        episodeListData = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_three_item, null, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        EpisodeListData episode = episodeListData.get(position);

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
        holder.airsTime.setText(episode.getAirsTime());
        /*holder.overview.setBackgroundColor(Color.TRANSPARENT);
        holder.overview.loadData("<html><body>"
                + "<p align=\"justify\">"
                + episode.getOverview()
                + "</p> "
                + "</body></html>", "text/html", "utf-8");*/

    }

    @Override
    public int getItemCount() {
        return episodeListData.size();
    }

    public void deleteAllEpisodes()
    {
        episodeListData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    public void addEpisode(EpisodeListData episode) {
        episodeListData.add(episode);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        private ImageView showImage;
        private TextView posterNotAvailable;
        private TextView showName;
        private TextView episodeName;
        private TextView episodeNo;
        private TextView airsTime;
        private TextView seasonNo;
        private WebView overview;

        Holder(View itemView) {
            super(itemView);

            showImage = (ImageView) itemView.findViewById(R.id.showImageThree);
            posterNotAvailable = (TextView)itemView.findViewById(R.id.posterNotAvailableThree);
            showName = (TextView) itemView.findViewById(R.id.showNameTHREE);
            episodeName = (TextView) itemView.findViewById(R.id.episodeNameThreeText);
            episodeNo = (TextView) itemView.findViewById(R.id.episodeNumberThree);
            airsTime = (TextView) itemView.findViewById(R.id.airsTimeTextThree);
            seasonNo = (TextView) itemView.findViewById(R.id.seasonNumberThreeText);
            //overview = (WebView) itemView.findViewById(R.id.overviewTextThree);
        }
    }
}
