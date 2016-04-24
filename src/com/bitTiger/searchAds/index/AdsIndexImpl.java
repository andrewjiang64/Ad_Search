package com.bitTiger.searchAds.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bitTiger.searchAds.adsInfo.AdsInfo;
import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsInvertedIndex;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;

public class AdsIndexImpl implements AdsIndex {

  private final AdsInventory _adsInventory;
  private final CampaignInventory _campaignInventory;
  private final AdsInvertedIndex _adsInvertedIndex;

  public AdsIndexImpl() {
     _adsInventory = new AdsInventory();
     _campaignInventory = new CampaignInventory();
     _adsInvertedIndex = new AdsInvertedIndex();
  }

  @Override
  public List<AdsStatsInfo> indexMatch(List<String> keyWords) {
    List<AdsStatsInfo> adsStatsInfoList = new ArrayList<AdsStatsInfo>();
    if (keyWords != null) {
      Iterator<String> keywordsIterator = keyWords.iterator();
      Map<Integer, Integer> hitCounts = new HashMap<Integer, Integer>();
      while (keywordsIterator.hasNext()) {
        String keyWord = keywordsIterator.next();
        List<Integer> matchedAdsIds = _adsInvertedIndex.retrieveIndex(keyWord);
        if (matchedAdsIds != null) {
          Iterator<Integer> matchedAdsIdsIterator = matchedAdsIds.iterator();
          while (matchedAdsIdsIterator.hasNext()) {
            Integer matchedAdsId = matchedAdsIdsIterator.next();
            hitCounts.put(matchedAdsId, hitCounts.get(matchedAdsId) == null ? 1 : hitCounts.get(matchedAdsId) + 1);
          }
        }
      }
      Iterator<Map.Entry<Integer, Integer>> hitCountsIterator = hitCounts.entrySet().iterator();
      while (hitCountsIterator.hasNext()) {
        Map.Entry<Integer, Integer> hitCountsEntry = hitCountsIterator.next();
        Integer adsId = hitCountsEntry.getKey();
        Integer hitCount = hitCountsEntry.getValue();
        AdsInfo adsInfo = _adsInventory.findAds(adsId);
        if (adsInfo != null) {
          int campaignId = adsInfo.getCampaignId();
          CampaignInfo campaignInfo = _campaignInventory.findCampaign(campaignId);
          if (campaignInfo.getBudget() > 0) {
            AdsStatsInfo adsStatsInfo = new AdsStatsInfo(campaignId);
            adsStatsInfo.setRelevanceScore(hitCount*1.0f/adsInfo.getAdsKeyWords().size());
            adsStatsInfoList.add(adsStatsInfo);
          }
        }
      }
    }
    return adsStatsInfoList;
  }

  @Override
  public CampaignInventory buildIndex(String fileName) {
    return _campaignInventory;
    // TODO Auto-generated method stub
  }

}
