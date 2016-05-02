package com.bitTiger.searchAds.adsOptimization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bitTiger.searchAds.adsInfo.AdsInfo;
import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.adsInfo.Inventory;

public class AdsOptimizationImplTest {
    AdsOptimizationImpl ads;
    Inventory inventory;

    @Before
    public void setUp() throws Exception {
        inventoryGenerator();
    }

    @After
    public void tearDown() throws Exception {
        ads = null;
        inventory = null;
    }

    @Test
    public void filterAdsShouldReturnAValidOutput() {
        System.out.println("filterAdsShouldReturnAValidOutput");
        ads.filterAds(inventory, 0.5f, 5f);

    }

    @Test(expected = IllegalArgumentException.class)
    public void filterAdsShouldThrowIllegalArgumentException_For_MinRelevanceScoreLessThanZero() {
        System.out
        .println("filterAdsShouldThrowIllegalArgumentException_For_MinRelevanceScoreLessThanZero");
        ads.filterAds(inventory, -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterAdsShouldThrowIllegalArgumentException_For_MinRelevanceScoreBiggerOrEquaToOne() {
        System.out
        .println("filterAdsShouldThrowIllegalArgumentException_For_MinRelevanceScoreBiggerOrEquaToOne");
        ads.filterAds(inventory, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterAdsShouldThrowIllegalArgumentException_For_MinRelevancePriceLessThanZero() {
        System.out
        .println("filterAdsShouldThrowIllegalArgumentException_For_MinRelevancePriceLessThanZero");
        ads.filterAds(inventory, 1, -1);
    }

    public void inventoryGenerator(){
        // AdsInfo(int adsId, List<String> adsKeyWords, float pClick, float bid,
        // int campaignId)
        AdsInfo ads1 = new AdsInfo(1, new ArrayList<String>(Arrays
                .asList("nike", "running", "shoe")), 0.03f, 3f, 101);
        AdsInfo ads2 = new AdsInfo(2, new ArrayList<String>(Arrays.asList("nike", "shoe")), 0.03f,
                3.5f, 101);
        AdsInfo ads3 = new AdsInfo(3, new ArrayList<String>(Arrays.asList("adidas", "shoe")),
                0.03f, 4f, 102);
        AdsInfo ads4 = new AdsInfo(4, new ArrayList<String>(Arrays.asList("adidas", "running")),
                0.03f, 4.5f, 102);
        AdsInfo ads5 = new AdsInfo(5, new ArrayList<String>(Arrays.asList("adidas", "running",
                "shoe")), 0.03f, 3f, 103);
        AdsInfo ads6 = new AdsInfo(6, new ArrayList<String>(Arrays
                .asList("nike", "running", "shoe")), 0.03f, 5f, 103);
        AdsInventory adsInventory = new AdsInventory();
        adsInventory.insertAds(ads1);
        adsInventory.insertAds(ads2);
        adsInventory.insertAds(ads3);
        adsInventory.insertAds(ads4);
        adsInventory.insertAds(ads5);
        adsInventory.insertAds(ads6);

        // CampaignInfo(int campaignId, float budget)
        CampaignInfo campaign1 = new CampaignInfo(101, 300);
        CampaignInfo campaign2 = new CampaignInfo(102, 400);
        CampaignInfo campaign3 = new CampaignInfo(103, 500);

        CampaignInventory campaignInventory = new CampaignInventory();
        campaignInventory.insertCampaign(campaign1);
        campaignInventory.insertCampaign(campaign2);
        campaignInventory.insertCampaign(campaign3);

        Inventory inventory = new Inventory(adsInventory, campaignInventory);
        this.inventory = inventory;

        // (int campaignId, int adsId, float relevanceScore, float cpc, boolean
        // isMainline)
        AdsStatsInfo info1 = new AdsStatsInfo(101, 1, 0.2f, 3f, false);
        AdsStatsInfo info2 = new AdsStatsInfo(101, 2, 0.4f, 5f, true);
        AdsStatsInfo info3 = new AdsStatsInfo(102, 3, 0.5f, 6f, true);
        AdsStatsInfo info4 = new AdsStatsInfo(102, 4, 0.6f, 4f, true);
        AdsStatsInfo info5 = new AdsStatsInfo(103, 5, 0.8f, 2f, false);
        AdsStatsInfo info6 = new AdsStatsInfo(103, 6, 0.9f, 1f, false);

        List<AdsStatsInfo> adsList = new ArrayList<AdsStatsInfo>();
        adsList.add(info1);
        adsList.add(info2);
        adsList.add(info3);
        adsList.add(info4);
        adsList.add(info5);
        adsList.add(info6);
        // calculate qualityScore, rankScore
        for (AdsStatsInfo info : adsList) {
            info.setQualityScore(0.75f * 0.03f + 0.25f * info.getRelevanceScore());
            info.setRankScore(info.getQualityScore()
                    * adsInventory.findAds(info.getAdsId()).getBid());
        }

        AdsOptimizationImpl ads = new AdsOptimizationImpl(adsList);
        this.ads = ads;
    }

}
