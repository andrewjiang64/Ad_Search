package com.bitTiger.searchAds.adsOptimization;

import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;

public class AdsOptimizationImpl implements AdsOptimization {
  private final List<AdsStatsInfo> _candidateAds;

  public AdsOptimizationImpl(List<AdsStatsInfo> candidateAds) {
    _candidateAds = candidateAds;
  }

  @Override
  public AdsOptimization filterAds(float threshold) {
    return null;
  }

  @Override
  public AdsOptimization rankAdsAndSelectTopK(int K) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AdsOptimization adsPricingAndAllocation(CampaignInventory campaignInventory) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public AdsOptimization deDup() {
    // TODO Auto-generated method stub
    return null;
  }


}
