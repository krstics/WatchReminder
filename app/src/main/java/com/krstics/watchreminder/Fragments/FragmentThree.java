package com.krstics.watchreminder.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krstics.watchreminder.Adapters.TodayEpisodesAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Decorators.FragmentsItemDecorator;
import com.krstics.watchreminder.R;

public class FragmentThree extends Fragment {

    private View view;
    private ShowsDB showsDB;
    private TodayEpisodesAdapter todayEpisodesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, container, false);
        configViews();
        return view;
    }

    private void configViews()
    {
        showsDB = new ShowsDB(getActivity());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentThree);
        recyclerView.addItemDecoration(new FragmentsItemDecorator(10));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(todayEpisodesAdapter);
    }
}
