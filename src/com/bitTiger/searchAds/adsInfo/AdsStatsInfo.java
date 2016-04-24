package com.bitTiger.searchAds.adsInfo;

public class AdsStatsInfo {
  private final int _campaignId;
  private float _relevanceScore;
  private float _qualityScore;
  private float _rankScore;
  private float _cpc;

  public AdsStatsInfo(int campaignId) {
     _campaignId = campaignId;
     _relevanceScore = 0;
     _qualityScore = 0;
     _rankScore = 0;
     _cpc = 0;
  }

  public float getRelevanceScore() {
    return _relevanceScore;
  }

  public void setRelevanceScore(float relevanceScore) {
    _relevanceScore = relevanceScore;
  }

  public float getQualityScore() {
    return _qualityScore;
  }

  public void setQualityScore(float qualityScore) {
    _qualityScore = qualityScore;
  }

  public float getRankScore() {
    return _rankScore;
  }

  public void setRankScore(float rankScore) {
    _rankScore = rankScore;
  }

  public float getCpc() {
    return _cpc;
  }

  public void setCpc(float cpc) {
    _cpc = cpc;
  }

  public int getCampaignId() {
    return _campaignId;
  }

}
