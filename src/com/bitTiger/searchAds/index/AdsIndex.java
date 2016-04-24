package com.bitTiger.searchAds.index;

import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;

public interface AdsIndex {

  CampaignInventory buildIndex(String fileName);

  List<AdsStatsInfo> indexMatch(List<String> keyWords);

}
