package com.krstics.watchreminder.Listeners;

import com.krstics.watchreminder.Data.EpisodeListData;

import java.util.List;

public interface Next4WeeksPremiersFetchListener {
    void onDeliverAllEpisodes(List<EpisodeListData> episodes);
    void onDeliverEpisode(EpisodeListData episode);
}
