package com.bitTiger.searchAds.index;

import java.io.FileNotFoundException;

import org.junit.Test;

public class AdsIndexImplUnitTest {
    
	@Test(expected = FileNotFoundException.class)
	public void ShouldReturnException()
	{
		AdsIndexImpl adsIndexImpl = new AdsIndexImpl();
		adsIndexImpl.buildIndex("ads-data.json", "CamPaign.json");
	}
}
