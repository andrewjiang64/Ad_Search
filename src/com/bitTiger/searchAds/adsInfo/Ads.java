package com.bitTiger.searchAds.adsInfo;

import java.util.List;

public class Ads {

	private final List<AdsInfo> _ads;
	
	public Ads(List<AdsInfo> ads)
	{
		_ads = ads;
	}

	public List<AdsInfo> getAds() {
		return _ads;
	}
	

}
