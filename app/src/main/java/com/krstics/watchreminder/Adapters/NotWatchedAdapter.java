package com.krstics.watchreminder.Adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.Fragments.FragmentFour;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class NotWatchedAdapter extends RecyclerView.Adapter<NotWatchedAdapter.Holder> implements View.OnClickListener{

    private List<EpisodeListData> episodeListDatas;
    private ShowsDB db;
    private FragmentFour fragmentFour;

    public NotWatchedAdapter(ShowsDB db, FragmentFour fragment){
        episodeListDatas = new ArrayList<>();
        this.db = db;
        fragmentFour = fragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_four_item, null, false));
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
        holder.airsTime.setText(episode.getAirsTime());
        holder.removeImageButton.setOnClickListener(NotWatchedAdapter.this);
        holder.removeImageButton.setTag(holder);

    }

    @Override
    public int getItemCount() {
        return episodeListDatas.size();
    }

    @Override
    public void onClick(View v) {
        final NotWatchedAdapter.Holder holder = (NotWatchedAdapter.Holder)v.getTag();

        if(v.getId() == holder.removeImageButton.getId()){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            db.removeEpisode(episodeListDatas.get(holder.getLayoutPosition()).getEpisodeId());
                            deleteEpisode(holder.getLayoutPosition());
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }
    }

    public void addEpisode(EpisodeListData episode){
        episodeListDatas.add(episode);
        if(episodeListDatas.size() > 0)
            fragmentFour.setVisibilityDeleteAllButton(View.VISIBLE);
        notifyDataSetChanged();
    }

    private void deleteEpisode(int position) {
        episodeListDatas.remove(position);
        if(episodeListDatas.size() == 0)
            fragmentFour.setVisibilityDeleteAllButton(View.INVISIBLE);
        notifyDataSetChanged();
    }

    public void deleteAllEpisodes() {
        episodeListDatas.clear();
        fragmentFour.setVisibilityDeleteAllButton(View.INVISIBLE);
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
        private ImageButton removeImageButton;

        Holder(View itemView) {
            super(itemView);

            showImage = (ImageView) itemView.findViewById(R.id.showImageFour);
            posterNotAvailable = (TextView)itemView.findViewById(R.id.posterNotAvailableFour);
            showName = (TextView) itemView.findViewById(R.id.showNameFour);
            episodeName = (TextView) itemView.findViewById(R.id.episodeNameFourText);
            episodeNo = (TextView) itemView.findViewById(R.id.episodeNumberFour);
            airsTime = (TextView) itemView.findViewById(R.id.airsTimeTextFour);
            seasonNo = (TextView) itemView.findViewById(R.id.seasonNumberThreeFour);
            removeImageButton = (ImageButton)itemView.findViewById(R.id.removeImageButtonFour);
        }
    }
}
