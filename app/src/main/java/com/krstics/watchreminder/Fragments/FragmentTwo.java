package com.krstics.watchreminder.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krstics.watchreminder.Adapters.AddedShowsAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Decorators.FragmentsItemDecorator;
import com.krstics.watchreminder.Listeners.ShowFetchListener;
import com.krstics.watchreminder.R;

import java.util.List;

public class FragmentTwo extends Fragment implements ShowFetchListener{

    private ShowsDB showsDB;
    private View view;
    private RecyclerView mRecyclerView;
    private AddedShowsAdapter addedShowsAdapter;

    @Override
    public void onResume() {
        super.onResume();
        addedShowsAdapter.deleteAllShows();
        showsDB.fetchShows(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        configViews();
        return view;
    }

    private void configViews() {
        showsDB = new ShowsDB(getActivity());
        addedShowsAdapter = new AddedShowsAdapter(getActivity());

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewFragmentTwo);
        mRecyclerView.addItemDecoration(new FragmentsItemDecorator(getResources().getDimensionPixelSize(R.dimen.item_spacing)));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(addedShowsAdapter);
    }

    @Override
    public void onDeliverAllShows(List<ShowListData> shows) {

    }

    @Override
    public void onDeliverShow(ShowListData show) {
        addedShowsAdapter.addShow(show);
    }
}
