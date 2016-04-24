package com.bitTiger.searchAds.adsInfo;

public class Inventory {
    private final AdsInventory _adsInventory;
    private final CampaignInventory _campaignInventory;

    public Inventory(AdsInventory _adsInventory,
            CampaignInventory _campaignInventory) {
        this._adsInventory = _adsInventory;
        this._campaignInventory = _campaignInventory;
    }

    public AdsInventory getAdsInventory() {
        return _adsInventory;
    }

    public CampaignInventory getCampaignInventory() {
        return _campaignInventory;
    }

}
