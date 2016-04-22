package com.bitTiger.searchAds.adsInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AdsInventory {
  private final List<AdsCampaignInfo> _adsInfo;

  public AdsInventory() {
    _adsInfo = new ArrayList<AdsCampaignInfo>();
  }

  public void insertAds(AdsCampaignInfo adsCampaignInfo) {
    _adsInfo.add(adsCampaignInfo);
  }

  public AdsCampaignInfo findAds(int adsId) {
    Iterator<AdsCampaignInfo> itr = _adsInfo.iterator();
    while(itr.hasNext()) {
      AdsCampaignInfo currentAds = itr.next();
      if (currentAds.getAdsId() == adsId) {
        return currentAds;
      }
    }
    return null;
  }

}
