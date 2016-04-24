package com.bitTiger.searchAds.adsOptimization;

import java.util.ArrayList;
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
    private static float MAINLINE_RESERVE_PRICE;
    private static float MIN_RESERVE_PRICE;

    public AdsOptimizationImpl(List<AdsStatsInfo> candidateAds,
            float threshold1, float threshold2) {
        _candidateAds = candidateAds;
        MAINLINE_RESERVE_PRICE = threshold1;
        MIN_RESERVE_PRICE = threshold2;
    }

    public AdsOptimizationImpl(List<AdsStatsInfo> candidateAds) {
        _candidateAds = candidateAds;
    }

    /*
     * filter ads with low relevance score;
     */
    @Override
    public AdsOptimization filterAds() {
        if (_candidateAds == null) {
            return null;
        }
        Iterator<AdsStatsInfo> iterator = _candidateAds.iterator();
        final float MINIMAL = 0.01f; // threshold of lowest relevance score
        while (iterator.hasNext()) {
            AdsStatsInfo info = iterator.next();
            if (info.getRelevanceScore() < MINIMAL) {
                iterator.remove();
            }
        }
        return this;
    }

    @Override
    public AdsOptimization selectTopK(int K) {
        if (_candidateAds == null) {
            return null;
        }
        PriorityQueue<AdsStatsInfo> adsHeap = new PriorityQueue<AdsStatsInfo>(
                K, new Comparator<AdsStatsInfo>() {
                    @Override
                    public int compare(AdsStatsInfo ad1, AdsStatsInfo ad2) {
                        float diff = ad1.getRankScore() - ad2.getRankScore();
                        if (diff == 0) {
                            return 0;
                        }
                        return diff < 0 ? -1 : 1;
                    }
                });
        List<AdsStatsInfo> candidateAds = new ArrayList<AdsStatsInfo>();
        Iterator<AdsStatsInfo> iterator = _candidateAds.iterator();
        while (iterator.hasNext()) {
            AdsStatsInfo adsStatsInfo = iterator.next();
            if (adsHeap.size() < K) {
                adsHeap.offer(adsStatsInfo);
            } else {
                if (adsHeap.peek().getRankScore() <= adsStatsInfo
                        .getRankScore()) {
                    adsHeap.poll();
                    adsHeap.offer(adsStatsInfo);
                }
            }
        }
        while (adsHeap.size() > 0) {
            candidateAds.add(adsHeap.poll());
        }
        return new AdsOptimizationImpl(candidateAds);
    }

    @Override
    public AdsOptimization adsPricingAndAllocation(Inventory inventory) {
        if (_candidateAds == null) {
            return null;
        }
        // if only one ads in the list, it pays the minimal
        if (_candidateAds.size() == 1) {
            _candidateAds.get(0).setCpc(MIN_RESERVE_PRICE);
            return this;
        }

        AdsInventory adsInventory = inventory.getAdsInventory();
        CampaignInventory campaignInventory = inventory.getCampaignInventory();
        List<AdsStatsInfo> candidateAds = new ArrayList<AdsStatsInfo>();
        for (int i = 0; i < _candidateAds.size() - 1; i++) {
            AdsStatsInfo currentAds = _candidateAds.get(i);
            AdsStatsInfo nextAds = _candidateAds.get(i + 1);
            float bid = adsInventory.findAds(nextAds.getAdsId()).getBid();
            float price = nextAds.getQualityScore()
                    / currentAds.getQualityScore() * bid + 0.01f;
            float budget = campaignInventory.findCampaign(
                    currentAds.getCampaignId()).getBudget();
            if (budget >= price) {
                campaignInventory.findCampaign(currentAds.getCampaignId())
                .deductBudget(price);
                currentAds.setCpc(price);
                if (bid >= MAINLINE_RESERVE_PRICE) {
                    currentAds.setIsMainline(true);
                    candidateAds.add(currentAds);
                } else if (bid >= MIN_RESERVE_PRICE) {
                    candidateAds.add(currentAds);
                } // filter ads with bid price lower than minReservePrice
            }
        }
        return new AdsOptimizationImpl(candidateAds);
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
        if (_candidateAds == null) {
            return null;
        }
        // TODO Auto-generated method stub
        return new AdsOptimizationImpl(_candidateAds);
    }
}
