package com.bitTiger.searchAds.index;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.AdsInfo;
import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsInvertedIndex;
import com.bitTiger.searchAds.adsInfo.CampaignInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;

public class AdsIndexImplUnitTest {
     
	private AdsInventory adsInventory;
	private CampaignInventory campaignInventory;
	  private AdsInvertedIndex adsInvertedIndex;
	  
	    @Before
	    public void setUp() {
	       adsInventory = new AdsInventory();
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
	       campaignInventory = new CampaignInventory();
	       CampaignInfo campaignInfo = new CampaignInfo(66,1500);
	       campaignInventory.insertCampaign(campaignInfo);
	       campaignInfo = new CampaignInfo(67,2800);
	       campaignInventory.insertCampaign(campaignInfo);
	       campaignInfo = new CampaignInfo(68,900);
	       campaignInventory.insertCampaign(campaignInfo);
	       adsInvertedIndex = new AdsInvertedIndex();
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
	     
	       
	    }
	@Test
	public void buildIndexTest()
	{
		AdsIndexImpl adsIndexImpl = new AdsIndexImpl();
   	    adsIndexImpl.buildIndex("ads-data.json","CamPaign.json");
   	    AdsInventory adsInventory = adsIndexImpl.get_adsInventory();
   	    AdsInvertedIndex adsInvertedIndex = adsIndexImpl.get_adsInvertedIndex();
   	    CampaignInventory campaignInventory = adsIndexImpl.get_campaignInventory();
   	    assertEquals(adsInventory,adsIndexImpl.get_adsInventory());
   	    assertEquals(campaignInventory,adsIndexImpl.get_campaignInventory());
   	    assertEquals(adsInvertedIndex,adsIndexImpl.get_adsInvertedIndex());
	}
	
	@Test
	public void InvertedIndexTest_ReturnSize0()
	{
		AdsIndexImpl adsIndexImpl = new AdsIndexImpl();
   	    adsIndexImpl.buildIndex("ads-data.json","CamPaign.json");
   	    List<AdsStatsInfo> adsStatsInfo = adsIndexImpl.indexMatch(null);
   	    assertEquals("failure -  adsStatsInfo size is not 0", 0, adsStatsInfo.size());
		
	}
	
	
	@Test
	public void InvertedIndexTest()
	{
		AdsIndexImpl adsIndexImpl = new AdsIndexImpl();
   	    adsIndexImpl.buildIndex("ads-data.json","CamPaign.json");
   	    List<String> matchString = new ArrayList<String>();
   	    matchString.add("nike");
   	    matchString.add("running");
   	    matchString.add("shoes");
   	    
   	    ArrayList<AdsStatsInfo> adsStatsInfoList_value = (ArrayList<AdsStatsInfo>)adsIndexImpl.indexMatch(matchString);
   	    assertEquals(4, adsStatsInfoList_value.size());
   	   
   	    assertEquals(0.6666667f,adsStatsInfoList_value.get(0).getRelevanceScore(),0.00002);
   	    assertEquals(0.33333334f,adsStatsInfoList_value.get(1).getRelevanceScore(),0.00002);
   	    assertEquals(0.33333334f,adsStatsInfoList_value.get(2).getRelevanceScore(),0.00002);
   	    assertEquals(0.25f,adsStatsInfoList_value.get(3).getRelevanceScore(),0.00002);
		
	}

}
