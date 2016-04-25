package com.bitTiger.searchAds.index;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.google.gson.*;
import java.io.*;
import java.net.InetSocketAddress;  
import net.spy.memcached.MemcachedClient;

import com.bitTiger.searchAds.adsInfo.Ads;
import com.bitTiger.searchAds.adsInfo.Campaigns;
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
  private static final int memChacheStoreTime = 3600;

  public AdsIndexImpl() {
     _adsInventory = new AdsInventory();
     _campaignInventory = new CampaignInventory();
     _adsInvertedIndex = new AdsInvertedIndex();
  }

  @Override
  public List<AdsStatsInfo> indexMatch(List<String> keyWords) {
    List<AdsStatsInfo> adsStatsInfoList = new ArrayList<AdsStatsInfo>();
    try {
		MemcachedClient memcacheClient = new MemcachedClient(new InetSocketAddress("192.168.2.28", 11211));
		if (keyWords != null) {
		      Iterator<String> keywordsIterator = keyWords.iterator();
		      Map<Integer, Integer> hitCounts = new HashMap<Integer, Integer>();
		      while (keywordsIterator.hasNext()) {
		        String keyWord = keywordsIterator.next();
		        List<Integer> matchedAdsIds = (List<Integer>)memcacheClient.get(keyWord);
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
		        AdsInfo adsInfo = (AdsInfo)memcacheClient.get(String.valueOf(adsId));
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
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return adsStatsInfoList;
  }

  @Override
  public CampaignInventory buildIndex(String fileName) {
	  Gson gson = new Gson();
	  try {
		Ads ads = gson.fromJson(new FileReader(fileName), Ads.class);
		if(ads != null)
		{
			MemcachedClient memcacheClient = new MemcachedClient(new InetSocketAddress("192.168.2.28", 11211));
			List<AdsInfo> adsInfo = ads.getAds();
			for(AdsInfo adInfo : adsInfo)
			{
				List<String> keyWords = adInfo.getAdsKeyWords();
				for(String keyWord : keyWords)
				{
					if(memcacheClient.get(keyWord) == null)
					{
						int adInfoId = adInfo.getAdsId();
						List<Integer> adInfoIds = new LinkedList<Integer>();
						adInfoIds.add(adInfoId);
						memcacheClient.add(keyWord, memChacheStoreTime , adInfoIds);
					}
					else
					{
						int adInfoId = adInfo.getAdsId();
						List<Integer> adInfoIds = (LinkedList<Integer>)memcacheClient.get(keyWord);
						adInfoIds.add(adInfoId);
						memcacheClient.set(keyWord, memChacheStoreTime , adInfoIds);
					}					
				}
				memcacheClient.add(String.valueOf(adInfo.getAdsId()), memChacheStoreTime, adInfo);
				
			}
			Campaigns campaigns = gson.fromJson(new FileReader("CamPaign.json"), Campaigns.class);
			if(campaigns != null)
			{
				List<CampaignInfo> campaignInfos = campaigns.getCampaigns();
				for(CampaignInfo campaignInfo : campaignInfos)
				{
					_campaignInventory.insertCampaign(campaignInfo);
					
				}
			}
		}
	} catch (JsonSyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonIOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return _campaignInventory;
    // TODO Auto-generated method stub
  }

}
