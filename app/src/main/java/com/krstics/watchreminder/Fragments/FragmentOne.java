package com.krstics.watchreminder.Fragments;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.krstics.watchreminder.Adapters.ShowListAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Decorators.FragmentsItemDecorator;
import com.krstics.watchreminder.Loaders.SearchLoad;
import com.krstics.watchreminder.R;

public class FragmentOne extends Fragment{

    private Button searchButton;
    private Button deleteAllButton;
    private Button addSelectedButton;
    private EditText searchTextEdit;
    private RecyclerView mRecyclerView;
    private View mView;
    private ShowListAdapter mShowAdapter;
    private SearchLoad searchLoad;
    private ShowsDB showsDB;

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
        setUpListeners();
        hideSoftInput();
        return mView;
    }

    private void configViews(){
        searchButton = (Button)mView.findViewById(R.id.loadShowButton);
        deleteAllButton = (Button)mView.findViewById(R.id.deleteAllButton);
        deleteAllButton.setVisibility(View.INVISIBLE);
        addSelectedButton = (Button)mView.findViewById(R.id.addSelectedButton);
        addSelectedButton.setVisibility(View.INVISIBLE);
        searchTextEdit = (EditText)mView.findViewById(R.id.searchShowName);
        showsDB = new ShowsDB(getActivity());
        mShowAdapter = new ShowListAdapter(getActivity(), this);
        searchLoad = new SearchLoad(getActivity(), mShowAdapter);

        mRecyclerView = (RecyclerView)mView.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new FragmentsItemDecorator(getResources().getDimensionPixelSize(R.dimen.item_spacing)));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mShowAdapter);
    }

    private void setUpListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchTextEdit.getText().toString().trim().equals("")) {
                    searchLoad.callSearch(searchTextEdit.getText().toString().trim());
                    searchTextEdit.setText("");
                }
                else
                    Toast.makeText(getActivity(), "Please enter show name.", Toast.LENGTH_SHORT).show();
                hideSoftInput();
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

        addSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowAdapter.addSelected();
            }
        });
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    private void hideSoftInput() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchTextEdit.getWindowToken(), 0);
        }
    }

    public void setDeleteAllButtonVisibility(int value){
        deleteAllButton.setVisibility(value);
    }

    public void setAddSelectedButtonVisibility(int value){
        addSelectedButton.setVisibility(value);
    }

    public Button getAddSelectedButton() {
        return addSelectedButton;
    }

    public Button getDeleteAllButton() {
        return deleteAllButton;
    }

    public ShowsDB getShowsDB() {
        return showsDB;
    }
}
