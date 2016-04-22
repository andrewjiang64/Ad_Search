package com.bitTiger.searchAds.index;

import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;

public interface AdsIndex {

  AdsInventory buildIndex(String fileName);

  List<AdsStatsInfo> indexMatch(List<String> keyWords);

}
