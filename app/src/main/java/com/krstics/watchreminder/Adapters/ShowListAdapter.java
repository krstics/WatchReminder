package com.krstics.watchreminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Fragments.FragmentOne;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.Holder> implements View.OnClickListener{

    private static final String TAG = ShowListAdapter.class.getSimpleName();
    private List<ShowListData> showListData;
    private Context context;
    private FragmentOne fragmentOne;

    public ShowListAdapter(Context context, FragmentOne fragmentOne){
        showListData = new ArrayList<>();
        this.context = context;
        this.fragmentOne = fragmentOne;
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
            holder.mShowImage.setVisibility(View.INVISIBLE);
        }
        else
            holder.mShowImage.setImageBitmap(show.getBitmap());

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
