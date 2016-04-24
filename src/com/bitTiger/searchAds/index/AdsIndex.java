package com.bitTiger.searchAds.index;

import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.adsInfo.Inventory;

public interface AdsIndex {

    Inventory buildIndex(String fileName);

    List<AdsStatsInfo> indexMatch(List<String> keyWords);

}
