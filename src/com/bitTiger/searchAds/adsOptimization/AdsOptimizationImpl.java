package com.bitTiger.searchAds.adsOptimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.adsInfo.Inventory;

public class AdsOptimizationImpl implements AdsOptimization {
    private final List<AdsStatsInfo> _candidateAds;

    public AdsOptimizationImpl(List<AdsStatsInfo> candidateAds) {
        if (candidateAds == null) {
            throw new NullPointerException(
                    "Null parameter: can't create the AdsOptimizationImpl object");
        }
        _candidateAds = candidateAds;
    }

    @Override
    public AdsOptimization filterAds(float minRelevanceScore,
            float minReservePrice) {
        // filter ads whose relevance score < min relevance score;
        Iterator<AdsStatsInfo> iterator = _candidateAds.iterator();
        while (iterator.hasNext()) {
            AdsStatsInfo info = iterator.next();
            if (info.getRelevanceScore() < minRelevanceScore) {
                iterator.remove();
            }
        }
        // to do: filter ads whose bid < min reserve price
        return this;
    }

    /*
     * returns a list that contains top k+1 AdsStatsInfo object from
     * _candidateAds
     */
    @Override
    public AdsOptimization selectTopK(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(
                    "The parameter should be a positive integer.");
        }
        Collections.sort(_candidateAds, new Comparator<AdsStatsInfo>() {
            @Override
            public int compare(AdsStatsInfo ads1, AdsStatsInfo ads2) {
                float diff = ads2.getRankScore() - ads1.getRankScore();
                return diff == 0 ? 0 : (diff > 0 ? 1 : -1);
            }
        });
        if (_candidateAds.size() > k + 1) {
            _candidateAds.subList(k + 1, _candidateAds.size()).clear();
        }
        return this;
    }

    @Override
    public AdsOptimization adsPricingAndAllocation(Inventory inventory,
            float mainlineReservePrice, float minReservePrice) {
        // if only one ads in the list, it pays the minimal
        if (_candidateAds.size() == 1) {
            AdsStatsInfo ads = _candidateAds.get(0);
            ads.setCpc(inventory.findAds(ads.getAdsId()).getBid());
            inventory.findCampaign(ads.getCampaignId()).deductBudget(
                    ads.getCpc());
            ads.setIsMainline(ads.getCpc() >= mainlineReservePrice);
            return this;
        }

        for (int i = 0; i < _candidateAds.size() - 1; i++) {
            AdsStatsInfo currentAds = _candidateAds.get(i);
            AdsStatsInfo nextAds = _candidateAds.get(i + 1);
            float bid = inventory.findAds(nextAds.getAdsId()).getBid();
            float price = nextAds.getQualityScore()
                    / currentAds.getQualityScore() * bid + 0.01f;
            currentAds.setCpc(price);
            inventory.findCampaign(currentAds.getCampaignId())
            .deductBudget(price);
            currentAds.setIsMainline(bid >= mainlineReservePrice);
        }
        _candidateAds.remove(_candidateAds.size() - 1);
        return this;
    }

    @Override
    public String toString() {
        String result = "Ads Id || Quality Score || Rank Score";
        Iterator<AdsStatsInfo> iterator = _candidateAds.iterator();
        while (iterator.hasNext()) {
            AdsStatsInfo info = iterator.next();
            result = result + info.getAdsId() + " " + info.getQualityScore()
                    + " " + info.getRankScore() + "\n";
        }
        return result;
    }

    @Override
    public AdsOptimization deDup() {
        // TODO Auto-generated method stub
        return new AdsOptimizationImpl(_candidateAds);
    }
}
