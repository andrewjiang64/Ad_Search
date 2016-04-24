package com.bitTiger.searchAds.adsOptimization;

import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.adsInfo.Inventory;

public interface AdsOptimization {
    AdsOptimization filterAds();

    AdsOptimization selectTopK(int K);

    AdsOptimization deDup();

    AdsOptimization adsPricingAndAllocation(Inventory inventory);
}
