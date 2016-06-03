package com.krstics.watchreminder.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.krstics.watchreminder.Adapters.ShowListAdapter;
import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Listeners.ShowFetchListener;
import com.krstics.watchreminder.Loaders.SearchLoad;
import com.krstics.watchreminder.R;

import java.util.List;

public class FragmentOne extends Fragment implements ShowFetchListener{

    private Button searchButton;
    private Button deleteAllButton;
    private Button addSelectedButton;
    private EditText searchTextEdit;
    private RecyclerView mRecyclerView;
    private View mView;
    private ShowListAdapter mShowAdapter;
    private SearchLoad searchLoad;

    public FragmentOne(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one, container, false);
        configViews();
        return mView;
    }

    private void configViews(){
        searchButton = (Button)mView.findViewById(R.id.loadShowButton);
        deleteAllButton = (Button)mView.findViewById(R.id.deleteAllButton);
        addSelectedButton = (Button)mView.findViewById(R.id.addSelectedButton);
        searchTextEdit = (EditText)mView.findViewById(R.id.searchShowName);
        mShowAdapter = new ShowListAdapter(getActivity(), this);
        searchLoad = new SearchLoad(getActivity(), mShowAdapter);

        mRecyclerView = (RecyclerView)mView.findViewById(R.id.recyclerView);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mShowAdapter);

        setUpListeners();
    }

    private void setUpListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLoad.callSearch(searchTextEdit.getText().toString().trim());
            }
        });

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                mShowAdapter.deleteAllShows();
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

    @Override
    public void OnDeliverAllShows(List<ShowListData> shows) {

    }

    @Override
    public void onDeliverShow(ShowListData show) {

    }
}
