package com.krstics.watchreminder.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

public class FragmentFour extends Fragment {

    private View view;
    private TodayEpisodesAdapter todayEpisodesAdapter;
    private ShowsDB showsDB;

    public void refresh(){
        todayEpisodesAdapter.deleteAllEpisodes();
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
        todayEpisodesAdapter = new TodayEpisodesAdapter(getActivity(), showsDB);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentFour);
        mRecyclerView.addItemDecoration(new FragmentsItemDecorator(getResources().getDimensionPixelSize(R.dimen.item_spacing)));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(todayEpisodesAdapter);
    }

}
