package com.krstics.watchreminder.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.krstics.watchreminder.Adapters.NotWatchedAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.Decorators.FragmentsItemDecorator;
import com.krstics.watchreminder.Listeners.NotWatchedEpisodesFetchListener;
import com.krstics.watchreminder.R;

import java.util.List;

public class FragmentFour extends Fragment implements NotWatchedEpisodesFetchListener{

    private View view;
    private NotWatchedAdapter notWatchedAdapter;
    private Button deleteAllEpisodes;
    private ShowsDB showsDB;
    Button deleteAllButton;

    @Override
    public void onResume() {
        super.onResume();
        notWatchedAdapter.deleteAllEpisodes();
        showsDB.fetchNotWatchedEpisodes(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        notWatchedAdapter.deleteAllEpisodes();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_four, container, false);
        configViews();
        return view;
    }

    private void configViews() {
        showsDB = new ShowsDB(getActivity());
        notWatchedAdapter = new NotWatchedAdapter(showsDB, this);
        deleteAllButton = (Button)view.findViewById(R.id.deleteAllButtonFFour);
        deleteAllButton.setVisibility(View.INVISIBLE);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentFour);
        mRecyclerView.addItemDecoration(new FragmentsItemDecorator(getResources().getDimensionPixelSize(R.dimen.item_spacing)));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(notWatchedAdapter);
    }

    public void setVisibilityDeleteAllButton(int visibility){
        deleteAllButton.setVisibility(visibility);
    }

    @Override
    public void onDeliverAllNotWatchedEpisodes(List<EpisodeListData> episodes) {

    }

    @Override
    public void onDeliverNotWathcedEpisode(EpisodeListData episode) {
        notWatchedAdapter.addEpisode(episode);
    }
}
