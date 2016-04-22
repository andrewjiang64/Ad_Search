package com.bitTiger.searchAds.adsOptimization;

import com.bitTiger.searchAds.adsInfo.AdsInventory;

public interface AdsOptimization {
  AdsOptimization filterAds(float threshold);

  AdsOptimization rankAdsAndSelectTopK(int K);

  AdsOptimization adsPricingAndAllocation(AdsInventory adsInventory);
}
