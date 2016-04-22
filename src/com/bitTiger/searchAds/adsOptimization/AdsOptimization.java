package com.bitTiger.searchAds.adsOptimization;

import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;

public interface AdsOptimization {
  AdsOptimization filterAds(List<AdsStatsInfo> candidateAds);

  AdsOptimization rankAdsAndSelectTopK(List<AdsStatsInfo> candidateAds, int K);

  List<String> adsPricingAndAllocation(List<AdsStatsInfo> candidateAds, AdsInventory adsInventory);
}
