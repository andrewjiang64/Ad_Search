package com.bitTiger.searchAds.index;

import java.io.FileNotFoundException;

import org.junit.Test;

public class AdsIndexImplUnitTest {
     
	@Test(expected = FileNotFoundException.class)
     public void ShouldReturnException() throws FileNotFoundException
     {
    	 AdsIndexImpl adsIndexImpl = new AdsIndexImpl();
    	 adsIndexImpl.buildIndex("ad-data.json","CamPaign.json");
    	 
     }

}
