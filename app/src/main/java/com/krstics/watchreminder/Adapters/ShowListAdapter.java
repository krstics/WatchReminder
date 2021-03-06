package com.krstics.watchreminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Fragments.FragmentOne;
import com.krstics.watchreminder.Helpers.Utils;
import com.krstics.watchreminder.Loaders.EpisodeLoad;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.Holder> implements View.OnClickListener{

    private final String TAG = ShowListAdapter.class.getSimpleName();
    private List<ShowListData> showListData;
    private Context context;
    private FragmentOne fragmentOne;
    private EpisodeLoad episodeLoad;
    private boolean readyToLoad = false;

    public ShowListAdapter(Context context, FragmentOne fragmentOne){
        showListData = new ArrayList<>();
        this.context = context;
        this.fragmentOne = fragmentOne;
        episodeLoad = new EpisodeLoad(fragmentOne.getShowsDB());
    }
    @Override
    public ShowListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_one_item, null, false));
    }

    @Override
    public void onBindViewHolder(ShowListAdapter.Holder holder, int position) {
        ShowListData show = showListData.get(position);

        holder.mShowName.setText(show.getSeriesName());
        holder.mAiredDate.setText(show.getFirstAired());
        if(show.getBitmap() == null) {
            holder.mPosterNotAvailable.setVisibility(View.VISIBLE);
            holder.mShowImage.setImageBitmap(null);
        }
        else {
            holder.mShowImage.setImageBitmap(show.getBitmap());
            holder.mPosterNotAvailable.setVisibility(View.INVISIBLE);
        }
        holder.selectCheckBox.setChecked(false);
        holder.statusTextView.setText(show.getStatus());

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
        if(showListData.size() > 0 && fragmentOne.getDeleteAllButton().getVisibility() != View.VISIBLE
                && fragmentOne.getAddSelectedButton().getVisibility() != View.VISIBLE) {
            fragmentOne.setDeleteAllButtonVisibility(View.VISIBLE);
            fragmentOne.setAddSelectedButtonVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    public void deleteAllShows() {
        showListData.clear();
        fragmentOne.setDeleteAllButtonVisibility(View.INVISIBLE);
        fragmentOne.setAddSelectedButtonVisibility(View.INVISIBLE);
        notifyDataSetChanged();
    }

    public void addSelected(){
        int num = this.getItemCount();
        for(int i = 0; i < num; i++) {
            if (fragmentOne.getmRecyclerView().findViewHolderForLayoutPosition(i) instanceof Holder) {
                Holder childHolder = (Holder) fragmentOne.getmRecyclerView().findViewHolderForLayoutPosition(i);
                if (childHolder.selectCheckBox.isChecked()) {
                    if(fragmentOne.getShowsDB().insertShows(showListData.get(i)) == 1)
                        Toast.makeText(context, "Show " + showListData.get(i).getSeriesName() + " already added!", Toast.LENGTH_SHORT).show();
                    else {
                        episodeLoad.loadEpisodeByAirDate(context);
                        episodeLoad.loadEpisodeByAirDateForNext4Weeks(context);
                        Toast.makeText(context, "Show " + showListData.get(i).getSeriesName() + " added successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        this.deleteAllShows();
    }

    public class Holder extends RecyclerView.ViewHolder{

        private ImageView mShowImage;
        private TextView mShowName;
        private TextView mAiredDate;
        private TextView mPosterNotAvailable;
        private CheckBox selectCheckBox;
        private TextView statusTextView;

        public Holder(View itemView) {
            super(itemView);

            mShowImage = (ImageView)itemView.findViewById(R.id.showImage);
            mShowName = (TextView)itemView.findViewById(R.id.showName);
            mAiredDate = (TextView)itemView.findViewById(R.id.firstAired);
            mPosterNotAvailable = (TextView)itemView.findViewById(R.id.posterNotAvailable);
            mPosterNotAvailable.setVisibility(View.INVISIBLE);
            selectCheckBox = (CheckBox)itemView.findViewById(R.id.selectCheckbox);
            statusTextView = (TextView)itemView.findViewById(R.id.status);
        }
    }
}
