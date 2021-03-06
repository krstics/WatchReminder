package com.krstics.watchreminder.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krstics.watchreminder.Adapters.TodayEpisodesAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.Decorators.FragmentsItemDecorator;
import com.krstics.watchreminder.Listeners.EpisodeFetchListener;
import com.krstics.watchreminder.Loaders.EpisodeLoad;
import com.krstics.watchreminder.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentThree extends Fragment implements EpisodeFetchListener {

    private View view;
    private ShowsDB showsDB;
    private TodayEpisodesAdapter todayEpisodesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);
        configViews();
        return view;
    }

    public void refresh(){
        todayEpisodesAdapter.deleteAllEpisodes();
        showsDB.fetchEpisodes(this);
    }

    private void configViews()
    {
        showsDB = new ShowsDB(getActivity());
        todayEpisodesAdapter = new TodayEpisodesAdapter();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragmentThreeSwipeContainer);
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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentThree);
        recyclerView.addItemDecoration(new FragmentsItemDecorator(10));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(todayEpisodesAdapter);
    }

    @Override
    public void onDeliverAllEpisodes(List<EpisodeListData> episodes) {

    }

    @Override
    public void onDeliverEpisode(EpisodeListData episode) {
        todayEpisodesAdapter.addEpisode(episode);
    }
}
