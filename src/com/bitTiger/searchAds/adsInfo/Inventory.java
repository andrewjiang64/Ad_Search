package com.bitTiger.searchAds.adsInfo;

public class Inventory {
    private final AdsInventory _adsInventory;
    private final CampaignInventory _campaignInventory;

    public Inventory(AdsInventory _adsInventory,
            CampaignInventory _campaignInventory) {
        this._adsInventory = _adsInventory;
        this._campaignInventory = _campaignInventory;
    }

    public AdsInfo findAds(Integer adsId) {
        return _adsInventory.findAds(adsId);
    }

    public CampaignInfo findCampaign(Integer campaignId) {
        return _campaignInventory.findCampaign(campaignId);
    }

    public int size() {
        return _adsInventory.size();
    }
}
