package com.krstics.watchreminder.Listeners;

import com.krstics.watchreminder.Data.EpisodeListData;

import java.util.List;

public interface NotWatchedEpisodesFetchListener {
    void onDeliverAllNotWatchedEpisodes(List<EpisodeListData> episodes);
    void onDeliverNotWathcedEpisode(EpisodeListData episode);
}
