package com.bitTiger.searchAds.main;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.bitTiger.searchAds.adsInfo.AdsInventory;
import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsOptimization.AdsOptimization;
import com.bitTiger.searchAds.adsOptimization.AdsOptimizationImpl;
import com.bitTiger.searchAds.index.AdsIndex;
import com.bitTiger.searchAds.index.AdsIndexImpl;
import com.bitTiger.searchAds.queryParser.QueryParser;
import com.bitTiger.searchAds.queryParser.QueryParserImpl;

public class SearchAdsStarter {

  public static String FILE_NAME = "ads-data.json";
  public static int K = 2;
  public static float FILTER_THRESHOLD= .5f;

  public static void main(String[] args) {
    AdsIndex adsIndex = new AdsIndexImpl();
    AdsInventory adsInventory = adsIndex.buildIndex(FILE_NAME);

    QueryParser queryParser = new QueryParserImpl();

    while (true) {
      Scanner reader = new Scanner(System.in);
      System.out.println("Input your query:");
      String query = reader.next();
      List<String> keyWords = queryParser.parseQuery(query);
      List<AdsStatsInfo> candidateAds = adsIndex.indexMatch(keyWords);
      AdsOptimization adsOptimizer = new AdsOptimizationImpl(candidateAds);
      AdsOptimization selectedAds = adsOptimizer.filterAds(FILTER_THRESHOLD)
                                                .rankAdsAndSelectTopK(K)
                                                .adsPricingAndAllocation(adsInventory);
      System.out.println(selectedAds);
      reader.close();
    }
  }

}
