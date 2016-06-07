package com.krstics.watchreminder.Listeners;

import com.krstics.watchreminder.Data.ShowListData;

import java.util.List;

public interface ShowFetchListener {
    void onDeliverAllShows(List<ShowListData> shows);
    void onDeliverShow(ShowListData show);
}
