package com.bitTiger.searchAds.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitTiger.searchAds.adsInfo.AdsInfo;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;

public class IndexMatchResult {
    private final List<AdsStatsInfo> _adsStatsInfoList;
    private final Map<Integer, AdsInfo> _adsInfoMap;

    public IndexMatchResult() {
        _adsStatsInfoList = new ArrayList<AdsStatsInfo>();
        _adsInfoMap = new HashMap<Integer, AdsInfo>();
    }

    public List<AdsStatsInfo> getAdsStatsInfoList() {
        return _adsStatsInfoList;
    }

    public Map<Integer, AdsInfo> getAdsInfoMap() {
        return _adsInfoMap;
    }

    public AdsInfo findAds(int adsId) {
        if (_adsInfoMap.containsKey(adsId)) {
            return _adsInfoMap.get(adsId);
        }
        return null;
    }

    public void insertAds(AdsStatsInfo adsStatsInfo, AdsInfo adsInfo) {
        _adsStatsInfoList.add(adsStatsInfo);
        _adsInfoMap.put(adsInfo.getAdsId(), adsInfo);
    }
}
