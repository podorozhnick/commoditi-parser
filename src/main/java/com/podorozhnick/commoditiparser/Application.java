package com.podorozhnick.commoditiparser;

import com.google.gson.Gson;

import java.util.*;

public class Application {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Enter parameters: 1 - companyName, 2 - number of days to process, [3 - fileName to save]");
            System.exit(0);
        }
        String companyName = args[0];
        int daysNumber = Integer.parseInt(args[1]);
        CommoditiConnector commoditiConnector = new CommoditiConnector();
        CommoditiParser commoditiParser = new CommoditiParser();
        Set<FlatSale> flatSales = new HashSet<>();
        int days = 1;
        Calendar cal = Calendar.getInstance();
        while (days < daysNumber) {
            List<String> pages = commoditiConnector.processDate(cal);
            Set<FlatSale> flatSaleSet = new HashSet<>();
            for (String page : pages) {
                Set<FlatSale> parsedPage = commoditiParser.parse(page, companyName);
                flatSaleSet.addAll(parsedPage);
                flatSales.addAll(parsedPage);
            }
            printSales(flatSaleSet);
            cal.add(Calendar.DATE, -1);
            days++;
        }
        if (args.length == 3) {
            String fileName = args[2];
            String json = new Gson().toJson(flatSales);
            new FileSaver(json, fileName).save();
        }
    }

    private static void printSales(Collection<FlatSale> flatSales) {
        for (FlatSale flatSale : flatSales) {
            System.out.println(flatSale);
        }
        if (flatSales.size() > 0)
            System.out.println(String.format("Size = %s", flatSales.size()));
    }

}
