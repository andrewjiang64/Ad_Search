package com.bitTiger.searchAds.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.google.gson.*;
import java.io.*;


import com.bitTiger.searchAds.adsInfo.AdsInfo;
import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsInvertedIndex;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.adsInfo.Inventory;

public class AdsIndexImpl implements AdsIndex {
;
    private final AdsInventory _adsInventory;
    private final CampaignInventory _campaignInventory;
    private final AdsInvertedIndex _adsInvertedIndex;

    public AdsIndexImpl() {
        _adsInventory = new AdsInventory();
        _campaignInventory = new CampaignInventory();
        _adsInvertedIndex = new AdsInvertedIndex();
    }
  
  public AdsInventory get_adsInventory() {
	return _adsInventory;
}

public void set_adsInventory(AdsInventory _adsInventory) {
	this._adsInventory = _adsInventory;
}

public CampaignInventory get_campaignInventory() {
	return _campaignInventory;
}

public void set_campaignInventory(CampaignInventory _campaignInventory) {
	this._campaignInventory = _campaignInventory;
}

public AdsInvertedIndex get_adsInvertedIndex() {
	return _adsInvertedIndex;
}

public void set_adsInvertedIndex(AdsInvertedIndex _adsInvertedIndex) {
	this._adsInvertedIndex = _adsInvertedIndex;
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
    

  @Override
  public CampaignInventory buildIndex(String fileName, String campaignFileName){
	  Gson gson = new Gson();
	  try {
		Ads ads = gson.fromJson(new FileReader(fileName), Ads.class);
		if(ads != null)
		{
			List<AdsInfo> adsInfo = ads.getAds();
			for(AdsInfo adInfo : adsInfo)
			{ 
				if(adInfo != null)
				{
					List<String> keyWords = adInfo.getAdsKeyWords();
					int adInfoId = adInfo.getAdsId();
					for(String keyWord : keyWords)
					{
						_adsInvertedIndex.insertIndex(keyWord, adInfoId);
					}	
					_adsInventory.insertAds(adInfo);
				}
				
			}
			Campaigns campaigns = gson.fromJson(new FileReader(campaignFileName), Campaigns.class);
			if(campaigns != null)
			{
				List<CampaignInfo> campaignInfos = campaigns.getCampaigns();
				for(CampaignInfo campaignInfo : campaignInfos)
				{
					if(campaignInfo != null)
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
	}
    return _campaignInventory;
    // TODO Auto-generated method stub
  }
  
    class Ads {

		private final List<AdsInfo> _ads;
		
		public Ads(List<AdsInfo> ads)
		{
			_ads = ads;
		}

		public List<AdsInfo> getAds() {
			return _ads;
		}
		

	}
    class Campaigns {
    	private final List<CampaignInfo> _campaigns;
    	public Campaigns(List<CampaignInfo> campaigns)
    	{
    		_campaigns = campaigns;
    	}
    	public List<CampaignInfo> Campaigns() {
    		return _campaigns;
    	}
    	public List<CampaignInfo> getCampaigns() {
    		return _campaigns;
    	}
    }
    	

}
