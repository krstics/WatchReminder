package com.krstics.watchreminder.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private AddedShowsAdapter addedShowsAdapter;
    private Button deleteAllButton;

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
        setUpListeners();
        return view;
    }

    private void setUpListeners() {

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                addedShowsAdapter.deleteAllShows();
                                showsDB.removeAllShows();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to remove all shows from the list?")
                        .setPositiveButton("Yes", dialogClickListener)

                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }

    private void configViews() {
        showsDB = new ShowsDB(getActivity());
        addedShowsAdapter = new AddedShowsAdapter(getActivity(), showsDB, this);
        deleteAllButton = (Button)view.findViewById(R.id.deleteAllButtonFTwo);
        deleteAllButton.setVisibility(View.INVISIBLE);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentTwo);
        mRecyclerView.addItemDecoration(new FragmentsItemDecorator(getResources().getDimensionPixelSize(R.dimen.item_spacing)));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(addedShowsAdapter);
    }

    public void setVisibilityDeleteAllButton(int visibility){
        deleteAllButton.setVisibility(visibility);
    }

    @Override
    public void onDeliverAllShows(List<ShowListData> shows) {

    }

    @Override
    public void onDeliverShow(ShowListData show) {
        addedShowsAdapter.addShow(show);
    }
}
