package com.krstics.watchreminder.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class Next4WeeksPremiersAdapter extends RecyclerView.Adapter<Next4WeeksPremiersAdapter.Holder>{

    private static final int HEADER = 0;
    private static final int CHILD = 1;

    private List<Item> data;

    public Next4WeeksPremiersAdapter(){
        data = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_five_expande, null, false));
            case CHILD:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_five_item, null, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        final Item item = data.get(position);

        switch (item.type) {
            case HEADER:
                holder.refferalItem = item;
                holder.header_title.setText(item.episodeData.getShowName());
                if (item.invisibleChildren == null) {
                    holder.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    holder.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                break;
            case CHILD:
                if(item.episodeData.getShowBanner() != null){
                    holder.posterNotAvailable.setVisibility(View.INVISIBLE);
                    holder.showImage.setImageBitmap(item.episodeData.getShowBanner());
                }
                else if(item.episodeData.getEpisodeBanner() != null){
                    holder.posterNotAvailable.setVisibility(View.INVISIBLE);
                    holder.showImage.setImageBitmap(item.episodeData.getEpisodeBanner());
                }
                else{
                    holder.posterNotAvailable.setVisibility(View.VISIBLE);
                    holder.showImage.setImageBitmap(null);
                }
                holder.showName.setText(item.episodeData.getShowName());
                holder.episodeName.setText(item.episodeData.getEpisodeName());
                holder.seasonNo.setText(item.episodeData.getSeasonNumber());
                holder.episodeNo.setText(item.episodeData.getEpisodeNumber());
                holder.airsDate.setText(item.episodeData.getAirsDate());
                holder.airsTime.setText(item.episodeData.getAirsTime());
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.refferalItem = item;
                if (item.invisibleChildren == null) {
                    item.invisibleChildren = new ArrayList<>();
                    int count = 0;
                    int pos = data.indexOf(holder.refferalItem);
                    while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                        item.invisibleChildren.add(data.remove(pos + 1));
                        count++;
                    }
                    notifyItemRangeRemoved(pos + 1, count);
                    holder.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                } else {
                    int pos = data.indexOf(holder.refferalItem);
                    int index = pos + 1;
                    for (Item i : item.invisibleChildren) {
                        data.add(index, i);
                        index++;
                    }
                    notifyItemRangeInserted(pos + 1, index - pos - 1);
                    holder.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                    item.invisibleChildren = null;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    public void addEpisode(EpisodeListData episode){

        boolean flag = true;
        if(data.size() == 0){
            Item shows = new Item(HEADER, episode);
            shows.invisibleChildren = new ArrayList<>();
            shows.invisibleChildren.add(new Item(CHILD, episode));

            data.add(shows);
            notifyDataSetChanged();
        }
        else{
            for(int i = 0; i < data.size(); ++i){
                if(data.get(i).episodeData.getShowName().compareToIgnoreCase(episode.getShowName()) == 0){
                    data.get(i).invisibleChildren.add(new Item(CHILD, episode));
                    notifyDataSetChanged();
                    flag = false;
                    break;
                }
            }
            if(flag){
                Item shows = new Item(HEADER, episode);
                shows.invisibleChildren = new ArrayList<>();
                shows.invisibleChildren.add(new Item(CHILD, episode));

                data.add(shows);
                notifyDataSetChanged();
            }
        }
    }

    public void deleteAllEpisodes() {
        data.clear();
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

        private TextView header_title;
        private ImageView btn_expand_toggle;

        private Item refferalItem;

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


            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    private class Item {
        int type;
        EpisodeListData episodeData;
        List<Item> invisibleChildren;

        Item(int type, EpisodeListData ed) {
            this.type = type;
            this.episodeData = ed;
        }
    }
}