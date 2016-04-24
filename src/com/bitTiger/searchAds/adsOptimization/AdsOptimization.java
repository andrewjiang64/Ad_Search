package com.bitTiger.searchAds.adsOptimization;

import com.bitTiger.searchAds.adsInfo.CampaignInventory;

public interface AdsOptimization {
  AdsOptimization filterAds(float threshold);

  AdsOptimization rankAdsAndSelectTopK(int K);

  AdsOptimization deDup();

  AdsOptimization adsPricingAndAllocation(CampaignInventory campaignInventory);
}
