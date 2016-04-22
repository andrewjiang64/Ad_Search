package com.bitTiger.searchAds.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bitTiger.searchAds.adsInfo.AdsCampaignInfo;
import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsInvertedIndex;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;

public class AdsIndexImpl implements AdsIndex {

  private final AdsInventory _adsForwardedIndex;
  private final AdsInvertedIndex _adsInvertedIndex;

  public AdsIndexImpl() {
     _adsForwardedIndex = new AdsInventory();
     _adsInvertedIndex = new AdsInvertedIndex();
  }

  @Override
  public List<AdsStatsInfo> indexMatch(List<String> keyWords) {
    Iterator<String> keywordsIterator = keyWords.iterator();
    Map<Integer, Integer> hitCounts = new HashMap<Integer, Integer>();
    while (keywordsIterator.hasNext()) {
      String keyWord = keywordsIterator.next();
      List<Integer> matchedAdsIds = _adsInvertedIndex.retrieveIndex(keyWord);
      Iterator<Integer> matchedAdsIdsIterator = matchedAdsIds.iterator();
      while (matchedAdsIdsIterator.hasNext()) {
        Integer matchedAdsId = matchedAdsIdsIterator.next();
        hitCounts.put(matchedAdsId, hitCounts.get(matchedAdsId) == null ? 1 : hitCounts.get(matchedAdsId) + 1);
      }
    }
    List<AdsStatsInfo> adsStatsInfoList = new ArrayList<AdsStatsInfo>();
    Iterator<Map.Entry<Integer, Integer>> hitCountsIterator = hitCounts.entrySet().iterator();
    while (hitCountsIterator.hasNext()) {
      Map.Entry<Integer, Integer> hitCountsEntry = hitCountsIterator.next();
      Integer adsId = hitCountsEntry.getKey();
      Integer hitCount = hitCountsEntry.getValue();
      AdsCampaignInfo adsInfo = _adsForwardedIndex.findAds(adsId);
      if (adsInfo.getBudget() > 0) {
        AdsStatsInfo adsStatsInfo = new AdsStatsInfo(adsId);
        adsStatsInfo.setRelevanceScore(hitCount*1.0f/_adsForwardedIndex.findAds(adsId).getAdsKeyWords().size());
        adsStatsInfoList.add(adsStatsInfo);
      }
    }
    return adsStatsInfoList;
  }

  public AdsInventory buildIndex(String fileName) {
    // TODO Auto-generated method stub
    return null;
  }

}
