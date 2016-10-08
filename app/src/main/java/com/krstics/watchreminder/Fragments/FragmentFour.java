package com.krstics.watchreminder.Fragments;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private ShowsDB showsDB;
    Button deleteAllButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    public void refresh() {
        notWatchedAdapter.deleteAllEpisodes();
        showsDB.fetchNotWatchedEpisodes(this);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragmentFourSwipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
