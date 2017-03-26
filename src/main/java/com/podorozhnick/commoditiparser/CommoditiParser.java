package com.podorozhnick.commoditiparser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommoditiParser {

    private static final String TABLE_BEGIN_RGX = "<table class=\"table_rezultat standart_font\".*id=\"ctl00_Content_GridView1\"(.*?)</table>";
    private static final String TR_BEGIN_RGX = "<tr\\b[^>]*>(.*?)</tr>";
    private static final String TD_RGX = "<td\\b[^>]*>[^>]*</td>";
    private static final String TD_BEGIN_RGX = "<td\\b[^>]*>";
    private static final String TD_END_RGX = "</td>";
    private static final String QUOT_HTML = "&quot;";
    private static final String QUOT = "\"";
    private static final String COMMA_HTML = "&#160;";
    private static final String EMPTY_STRING = "";
    private static final String LIVING_FLAT_TYPE = "Продаж майнових прав на нерухоме майно";
    private static final String FLAT_RGX = "(иру №|ира №)( |)";
    private static final String HOUSE_RGX = "будинок ";
    private static final String AREA_RGX = "загальн(а|ою) площ(а|ею) ";
    private static final String FLOOR_RGX = "поверх ";
    private static final String END_RGX = "[\\s,]";
    private static final String COMMA = ",";
    private static final String POINT = ".";

    public Set<FlatSale> parse(String input, String companyName) {
        Set<FlatSale> flatSaleList = new HashSet<>();
        Pattern pattern = Pattern.compile(TABLE_BEGIN_RGX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String table = matcher.group(0);
            flatSaleList = parseTable(table, companyName);
        }
        return flatSaleList;
    }

    private Set<FlatSale> parseTable(String table, String companyName) {
        Pattern pattern = Pattern.compile(TR_BEGIN_RGX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(table);
        List<String> trs = new ArrayList<>();
        while (matcher.find()) {
            trs.add(matcher.group(1));
        }
        trs = trs.stream().filter(el -> el.contains(companyName)).map(String::trim).collect(Collectors.toList());
        Set<FlatSale> flatSales = trs.stream().map(this::parseTr).collect(Collectors.toSet());
        return flatSales;
    }

    private String trimTd(String str) {
        return str.split(TD_BEGIN_RGX)[1].split(TD_END_RGX)[0];
    }

    private FlatSale parseTr(String tr) {
        Pattern pattern = Pattern.compile(TD_RGX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(tr);
        List<String> tds = new ArrayList<>();
        while (matcher.find()) {
            tds.add(matcher.group(0));
        }
        FlatSale flatSale = new FlatSale();
        tds = tds.stream().map(this::trimTd).collect(Collectors.toList());
        String auctionNumber = tds.get(0);
        String lotNumber = tds.get(1);
        String date = tds.get(2);
        String categoryName = tds.get(3);
        String lotName = tds.get(4);
        String auctionDate = tds.get(5);
        String customer = tds.get(6).replace(QUOT_HTML, QUOT);
        String edrpo = tds.get(7);
        String startPrice = tds.get(8).replace(COMMA_HTML, EMPTY_STRING);
        String price = tds.get(9).replace(COMMA_HTML, EMPTY_STRING);
        String contractNumber = tds.get(10);
        boolean isLast = true;
        if (categoryName.equals(LIVING_FLAT_TYPE)) {
            isLast = false;
        }
        int flat = Integer.parseInt(lotName.split(FLAT_RGX)[1].split(END_RGX)[0]);
        int house = Integer.parseInt(lotName.split(HOUSE_RGX)[1].split(END_RGX)[0]);
        double area = Double.parseDouble(lotName.split(AREA_RGX)[1].replace(COMMA, POINT).split(END_RGX)[0]);
        int floor = Integer.parseInt(lotName.split(FLOOR_RGX)[1].split(END_RGX)[0]);
        flatSale.setAuctionNumber(auctionNumber);
        flatSale.setLotNumber(lotNumber);
        flatSale.setDate(date);
        flatSale.setCategoryName(categoryName);
        flatSale.setLast(isLast);
        flatSale.setLotName(lotName);
        flatSale.setAuctionDate(auctionDate);
        flatSale.setCustomer(customer);
        flatSale.setEdrpo(edrpo);
        flatSale.setStartPrice(startPrice);
        flatSale.setPrice(price);
        flatSale.setContractNumber(contractNumber);
        flatSale.setHouse(house);
        flatSale.setFlat(flat);
        flatSale.setArea(area);
        flatSale.setFloor(floor);
        return flatSale;
    }

}
