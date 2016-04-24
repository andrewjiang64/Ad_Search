package com.bitTiger.searchAds.main;

import java.util.List;
import java.util.Scanner;

import com.bitTiger.searchAds.adsInfo.AdsStatsInfo;
import com.bitTiger.searchAds.adsInfo.CampaignInventory;
import com.bitTiger.searchAds.adsInfo.Inventory;
import com.bitTiger.searchAds.adsOptimization.AdsOptimization;
import com.bitTiger.searchAds.adsOptimization.AdsOptimizationImpl;
import com.bitTiger.searchAds.index.AdsIndex;
import com.bitTiger.searchAds.index.AdsIndexImpl;
import com.bitTiger.searchAds.queryParser.QueryParser;
import com.bitTiger.searchAds.queryParser.QueryParserImpl;


public class SearchAdsStarter {

    public static String FILE_NAME = "ads-data.json";
    public static int K = 3;
    public static float MAINLINE_RESERVE_PRICE = 2f;
    private static float MIN_RESERVE_PRICE = .5f;

    public static void main(String[] args) {
        AdsIndex adsIndex = new AdsIndexImpl();
        Inventory inventory = adsIndex.buildIndex(FILE_NAME);

        QueryParser queryParser = new QueryParserImpl();

        while (true) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Input your query:");
            String query = reader.next();
            List<String> keyWords = queryParser.parseQuery(query);
            List<AdsStatsInfo> candidateAds = adsIndex.indexMatch(keyWords);
            AdsOptimization adsOptimizer = new AdsOptimizationImpl(
                    candidateAds, MAINLINE_RESERVE_PRICE, MIN_RESERVE_PRICE);
            AdsOptimization selectedAds = adsOptimizer.filterAds()
                    .selectTopK(K).deDup()
                    .adsPricingAndAllocation(inventory);
            System.out.println(selectedAds);
            reader.close();
        }
    }

}
