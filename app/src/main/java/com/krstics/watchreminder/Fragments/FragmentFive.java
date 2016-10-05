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

import com.krstics.watchreminder.Adapters.Next4WeeksPremiersAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.Decorators.FragmentsItemDecorator;
import com.krstics.watchreminder.Listeners.Next4WeeksPremiersFetchListener;
import com.krstics.watchreminder.R;

import java.util.List;

public class FragmentFive extends Fragment implements Next4WeeksPremiersFetchListener {

    private View view;
    private Next4WeeksPremiersAdapter next4WeeksPremiers;
    private ShowsDB showsDB;

    @Override
    public void onResume() {
        super.onResume();
        next4WeeksPremiers.deleteAllEpisodes();
        showsDB.fetchNext4WeeksPremiers(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        next4WeeksPremiers.deleteAllEpisodes();
    }

    public void refresh(){
        next4WeeksPremiers.deleteAllEpisodes();
        showsDB.fetchNext4WeeksPremiers(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_five, container, false);
        configViews();
        return view;
    }

    private void configViews() {
        showsDB = new ShowsDB(getActivity());
        next4WeeksPremiers = new Next4WeeksPremiersAdapter(showsDB);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentFive);
        mRecyclerView.addItemDecoration(new FragmentsItemDecorator(getResources().getDimensionPixelSize(R.dimen.item_spacing)));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(next4WeeksPremiers);
    }

    @Override
    public void onDeliverAllEpisodes(List<EpisodeListData> episodes) {

    }

    @Override
    public void onDeliverEpisode(EpisodeListData episode) {
        next4WeeksPremiers.addEpisode(episode);
    }
}
