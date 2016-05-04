package com.bitTiger.searchAds.help;

import java.util.ArrayList;
import java.util.Arrays;

import com.bitTiger.searchAds.adsInfo.AdsInfo;
import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsInvertedIndex;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;

public class Help {
	
	public static AdsInventory ReturnAdsInventory()
	{
		AdsInventory adsInventory = new AdsInventory();
	       AdsInfo adsInfo = new AdsInfo(1231,Arrays.asList("basketball","kobe","shoe","nike"),0.3f,6.0f,66);
	       adsInventory.insertAds(adsInfo);
	       adsInfo = new AdsInfo(1232,Arrays.asList("running","shoe","nike"),0.63f,4.5f,66);
	       adsInventory.insertAds(adsInfo);
	       adsInfo = new AdsInfo(1233,Arrays.asList("soccer","shoe","nike"),0.23f,4.0f,66);
	       adsInventory.insertAds(adsInfo);
	       adsInfo = new AdsInfo(1234,Arrays.asList("running","shoe","adidas"),0.53f,7.5f,67);
	       adsInventory.insertAds(adsInfo);
	       adsInfo = new AdsInfo(1235,Arrays.asList("soccer","shoe","adidas"),0.19f,3.5f,67);
	       adsInventory.insertAds(adsInfo);
	       adsInfo = new AdsInfo(1236,Arrays.asList("basketball","shoe","adidas"),0.29f,5.5f,67);
	       adsInventory.insertAds(adsInfo);
	       return adsInventory;
	}
	public static CampaignInventory ReturnCampaignInventory()
	{
		CampaignInventory campaignInventory = new CampaignInventory();
	       CampaignInfo campaignInfo = new CampaignInfo(66,1500);
	       campaignInventory.insertCampaign(campaignInfo);
	       campaignInfo = new CampaignInfo(67,2800);
	       campaignInventory.insertCampaign(campaignInfo);
	       campaignInfo = new CampaignInfo(68,900);
	       campaignInventory.insertCampaign(campaignInfo);
	       return campaignInventory;
	}
	
	public static AdsInvertedIndex ReturnAdsInvertedIndex()
	{
		
		AdsInvertedIndex adsInvertedIndex = new AdsInvertedIndex();
	       adsInvertedIndex.insertIndex("basketball", 1231);
	       adsInvertedIndex.insertIndex("basketball", 1236);
	       adsInvertedIndex.insertIndex("kobe", 1231);
	       adsInvertedIndex.insertIndex("shoe", 1231);
	       adsInvertedIndex.insertIndex("shoe", 1232);
	       adsInvertedIndex.insertIndex("shoe", 1233);
	       adsInvertedIndex.insertIndex("shoe", 1234);
	       adsInvertedIndex.insertIndex("shoe", 1235);
	       adsInvertedIndex.insertIndex("shoe", 1236);
	       adsInvertedIndex.insertIndex("nike", 1231);
	       adsInvertedIndex.insertIndex("nike", 1232);
	       adsInvertedIndex.insertIndex("nike", 1233);
	       adsInvertedIndex.insertIndex("running", 1232);
	       adsInvertedIndex.insertIndex("running", 1234);
	       adsInvertedIndex.insertIndex("soccer", 1233);
	       adsInvertedIndex.insertIndex("soccer", 1235);
	       adsInvertedIndex.insertIndex("adidas", 1234);
	       adsInvertedIndex.insertIndex("adidas", 1235);
	       adsInvertedIndex.insertIndex("adidas", 1236);
	       return adsInvertedIndex;
	}
	public static ArrayList<AdsStatsInfo> ReturnadsStatsInfoList()
	{
		ArrayList<AdsStatsInfo> adsStatsInfoList = new ArrayList<AdsStatsInfo>();
		 AdsStatsInfo adsStatsInfo = new AdsStatsInfo(66,1232,0.6666667f,0.0f,false);
		 adsStatsInfoList.add(adsStatsInfo);
		 adsStatsInfo = new AdsStatsInfo(66,1233,0.33333334f,0.0f,false);
		 adsStatsInfoList.add(adsStatsInfo);
		 adsStatsInfo = new AdsStatsInfo(67,1234,0.33333334f,0.0f,false);
		 adsStatsInfoList.add(adsStatsInfo);
		 adsStatsInfo = new AdsStatsInfo(66,1231,0.25f,0.0f,false);
		 adsStatsInfoList.add(adsStatsInfo);
		return adsStatsInfoList;
	}
	

}
