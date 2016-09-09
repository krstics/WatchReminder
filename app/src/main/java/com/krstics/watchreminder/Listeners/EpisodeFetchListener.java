package com.krstics.watchreminder.Listeners;

import com.krstics.watchreminder.Data.EpisodeListData;

import java.util.List;

public interface EpisodeFetchListener {
    void onDeliverAllEpisodes(List<EpisodeListData> episodes);
    void onDeliverEpisode(EpisodeListData episode);
}
