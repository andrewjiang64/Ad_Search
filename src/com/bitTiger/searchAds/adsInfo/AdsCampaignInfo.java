package com.bitTiger.searchAds.adsInfo;

import java.util.List;

public class AdsCampaignInfo {
  private final int _adsId;
  private final List<String> _adsKeyWords;
  private final float _pClick;
  private final float _bid;
  private float _budget;

  public AdsCampaignInfo(int adsId, List<String> adsKeyWords, float pClick, float bid, float budget) {
     _adsId = adsId;
     _adsKeyWords = adsKeyWords;
     _pClick = pClick;
     _bid = bid;
     _budget = budget;
  }

  public List<String> getAdsKeyWords() {
    return _adsKeyWords;
  }

  public float getpClick() {
    return _pClick;
  }

  public float getBid() {
    return _bid;
  }

  public int getAdsId() {
    return _adsId;
  }

  public float getBudget() {
    return _budget;
  }

  public void deductBudget(float chargeAmount) {
    _budget -= chargeAmount;
  }

}
