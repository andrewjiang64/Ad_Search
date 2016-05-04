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
import com.bitTiger.searchAds.help.Help;

public class AdsIndexImplUnitTest {
     
	private AdsInventory adsInventory;
	private CampaignInventory campaignInventory;
	  private AdsInvertedIndex adsInvertedIndex;
	  
	    @Before
	    public void setUp() {
	      
	    	adsInventory = Help.ReturnAdsInventory();
	    	campaignInventory = Help.ReturnCampaignInventory();
	    	adsInvertedIndex = Help.ReturnAdsInvertedIndex();
	       
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
   	    ArrayList<AdsStatsInfo> adsStatsInfoList = Help.ReturnadsStatsInfoList();
   	    assertEquals(4, adsStatsInfoList_value.size());
   	    assertEquals(adsStatsInfoList,adsStatsInfoList_value);
   	  /*  assertEquals(0.6666667f,adsStatsInfoList_value.get(0).getRelevanceScore(),0.00002);
   	    assertEquals(0.33333334f,adsStatsInfoList_value.get(1).getRelevanceScore(),0.00002);
   	    assertEquals(0.33333334f,adsStatsInfoList_value.get(2).getRelevanceScore(),0.00002);
   	    assertEquals(0.25f,adsStatsInfoList_value.get(3).getRelevanceScore(),0.00002);*/
		
	}

}
