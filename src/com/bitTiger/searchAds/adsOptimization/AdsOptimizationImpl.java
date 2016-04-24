package com.bitTiger.searchAds.adsOptimization;

import java.util.Iterator;
import java.util.List;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.index.IndexMatchResult;

public class AdsOptimizationImpl implements AdsOptimization {
    private final IndexMatchResult _candidateAds;

    public AdsOptimizationImpl(IndexMatchResult candidateAds) {
        _candidateAds = candidateAds;
    }

    @Override
    public AdsOptimization filterAds(float threshold) {
        if (_candidateAds == null) {
            return null;
        }
        Iterator<AdsStatsInfo> iterator = _candidateAds.iterator();
        while (iterator.hasNext()) {
            AdsStatsInfo info = iterator.next();
            if (info.getRelevanceScore() < threshold) {
                iterator.remove();
            }
        }
        return new AdsOptimizationImpl(_candidateAds);
    }

    @Override
    public AdsOptimization filterAdsWithLowPrice(float threshold) {
        if (_candidateAds == null) {
            return null;
        }
        Iterator<AdsStatsInfo> iterator = _candidateAds.iterator();
        while (iterator.hasNext()) {
            AdsStatsInfo info = iterator.next();
            if (info.getRelevanceScore() < threshold) {
                iterator.remove();
            }
        }
        return new AdsOptimizationImpl(_candidateAds);
    }

    @Override
    public AdsOptimization rankAdsAndSelectTopK(int K) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AdsOptimization adsPricingAndAllocation() {
        if (_candidateAds == null) {
            return null;
        }
        float MINIMAL = 3; // if only one ads in the list, it pays the minimal
        // value
        if (_candidateAds.size() == 1) {
            _candidateAds.get(0).setCpc(MINIMAL);
            return new AdsOptimizationImpl(_candidateAds);
        }
        for (int i = 0; i < _candidateAds.size() - 1; i++) {

        }
        return new AdsOptimizationImpl(_candidateAds);

    }

    @Override
    public String toString() {
        if (_candidateAds == null) {
            return "";
        }
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
        return null;
    }
}
