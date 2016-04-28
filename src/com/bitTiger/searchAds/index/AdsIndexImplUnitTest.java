package com.bitTiger.searchAds.index;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;

public class AdsIndexImplUnitTest {
     
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
   	    
   	    List<AdsStatsInfo> adsStatsInfo = adsIndexImpl.indexMatch(matchString);
   	    assertEquals("failure -  adsStatsInfo size is not 0", 4, adsStatsInfo.size());
   	    assertEquals("failure -  adsStatsInfo size is not 0", 0.6666667, adsStatsInfo.get(0).getRelevanceScore());
		
	}

}
