package com.krstics.watchreminder.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Fragments.FragmentTwo;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class AddedShowsAdapter extends RecyclerView.Adapter<AddedShowsAdapter.Holder> implements View.OnClickListener {
    private String TAG = AddedShowsAdapter.class.getSimpleName();
    private List<ShowListData> showListData;
    private Context context;
    private ShowsDB mDB;
    private FragmentTwo fragmentTwo;

    public AddedShowsAdapter(Context context, ShowsDB db, FragmentTwo fragmentTwo) {
        this.context = context;
        showListData = new ArrayList<>();
        mDB = db;
        this.fragmentTwo = fragmentTwo;
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
        //holder.overview.setText(show.getOverview());
        holder.overview.setBackgroundColor(Color.TRANSPARENT);
        holder.overview.loadData("<html><body>"
                            + "<p align=\"justify\">"
                            + show.getOverview()
                            + "</p> "
                            + "</body></html>", "text/html", "utf-8");

        holder.removeImageButton.setOnClickListener(AddedShowsAdapter.this);
        holder.removeImageButton.setTag(holder);
    }


    @Override
    public int getItemCount() {
        return showListData.size();
    }

    @Override
    public void onClick(View v) {
        final Holder holder = (Holder)v.getTag();

        if(v.getId() == holder.removeImageButton.getId()){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            mDB.removeShow(showListData.get(holder.getLayoutPosition()).getSeriesid());
                            deleteShow(holder.getLayoutPosition());
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
        }
    }

    private void deleteShow(int position) {
        showListData.remove(position);
        if(showListData.size() == 0)
            fragmentTwo.setVisibilityDeleteAllButton(View.INVISIBLE);
        notifyDataSetChanged();
    }

    public void addShow(ShowListData show){
        showListData.add(show);
        if(showListData.size() > 0)
            fragmentTwo.setVisibilityDeleteAllButton(View.VISIBLE);
        notifyDataSetChanged();
    }

    public void deleteAllShows() {
        showListData.clear();
        fragmentTwo.setVisibilityDeleteAllButton(View.INVISIBLE);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        private ImageView showImage;
        private TextView showName;
        private TextView airedDate;
        private TextView posterNotAvailable;
        private TextView statusTextView;
        private TextView airstTime;
        private TextView airsDay;
        private TextView overview1;
        private WebView overview;
        private ImageButton removeImageButton;

        Holder(View itemView) {
            super(itemView);

            showImage = (ImageView)itemView.findViewById(R.id.showImageTWO);
            showName = (TextView)itemView.findViewById(R.id.showNameTWO);
            airedDate = (TextView)itemView.findViewById(R.id.firstAiredTWO);
            posterNotAvailable = (TextView)itemView.findViewById(R.id.posterNotAvailableTWO);
            posterNotAvailable.setVisibility(View.INVISIBLE);
            statusTextView = (TextView)itemView.findViewById(R.id.statusTWO);
            airstTime = (TextView)itemView.findViewById(R.id.airsTimeTextTWO);
            airsDay = (TextView)itemView.findViewById(R.id.airsDayOfWeekTextTWO);
            overview = (WebView) itemView.findViewById(R.id.overviewTextTWO);
            removeImageButton = (ImageButton)itemView.findViewById(R.id.removeImageButton);
        }
    }
}
