package com.bitTiger.searchAds.adsOptimization;

import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;

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
  public AdsOptimization adsPricingAndAllocation(AdsInventory adsInventory) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String toString() {
    return null;
  }


}
