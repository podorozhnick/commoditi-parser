package com.podorozhnick.commoditiparser;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommoditiConnector {

    private static final String E_COMMODITY_URL = "http://e-commodity.fbp.com.ua/TradeForwards.aspx";
    private static final String GET = "GET";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8 = "application/x-www-form-urlencoded; charset=UTF-8";
    private static final String CONTENT_LANGUAGE = "Content-Language";
    private static final String CONTENT_LANG_VALUE = "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";
    private static final String CONNECTION = "Connection";
    private static final String CONNECTION_VALUE = "keep-alive";
    private static final String VIEWSTATE_BEGIN_RGX = "id=\"__VIEWSTATE\" value=\"";
    private static final String EVENTVALIDATION_BEGIN_RGX = "id=\"__EVENTVALIDATION\" value=\"";
    private static final String VIEVSTATE_END_RGX = "\"";
    private static final String VIEWSTATE = "__VIEWSTATE";
    private static final String EVENTVALIDATION = "__EVENTVALIDATION";
    private static final String EMPTY_STRING = "";
    private static final String TB_FROM = "ctl00$Content$tbFrom";
    private static final String TB_TO = "ctl00$Content$tbTo";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final int START_PAGE = 2;
    private static final String EVENTARGUMENT = "__EVENTARGUMENT";
    private static final String PAGE = "Page$";
    private static final String UTF_8 = "UTF-8";
    private static final String EQUALS = "=";
    private static final String AND = "&";
    private static final String CTL_00_$_SCRIPT_MANAGER_1 = "ctl00$ScriptManager1";
    private static final String CTL_00_$_SCRIPT_MANAGER_1_VALUE = "ctl00$Content$ctl00|ctl00$Content$GridView1";
    private static final String CTL_00_SCRIPT_MANAGER_1_HIDDEN_FIELD = "ctl00_ScriptManager1_HiddenField";
    private static final String CTL_00_SCRIPT_MANAGER_1_HIDDEN_FIELD_VALUE = ";;AjaxControlToolkit, Version=3.5.50731.0, Culture=neutral, PublicKeyToken=28f01b0e84b6d53e:uk-UA:ec0bb675-3ec6-4135-8b02-a5c5783f45f5:de1feab2:fcf0e993:f2c8e708:720a52bf:f9cec9bc:589eaa30:698129cf:fb9b4c57:ccb96cf9;";
    private static final String EVENTTARGET = "__EVENTTARGET";
    private static final String EVENTTARGET_VALUE = "ctl00$Content$GridView1";
    private static final String CTL_00_$_SEARCH_TEXT = "ctl00$SearchText";
    private static final String CTL_00_$_SEARCH_TEXT_VALUE = "Пошук по сайту ...";
    private static final String CTL_00_$_CONTENT_$_NUMBER_ID = "ctl00$Content$NumberId";
    private static final String ASYNCPOST = "__ASYNCPOST";
    private static final String ASYNCPOST_VALUE = "true";
    private static final String POST = "POST";
    private static final String X_REQUESTED_WITH = "X-Requested-With";
    private static final String XML_HTTP_REQUEST_VALUE = "XMLHttpRequest";
    private static final String X_MICROSOFT_AJAX = "X-MicrosoftAjax";
    private static final String X_MICROSOFT_AJAX_VALUE = "Delta=true";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String CACHE_CONTROL_VALUE = "no-cache";
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_VALUE = "*/*";
    private static final String REFERER = "Referer";
    private static final String REFERER_VALUE = "http://e-commodity.fbp.com.ua/TradeForwards.aspx";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String ACCEPT_ENCODING_VALUE = "gzip, deflate";
    private static final String ORIGIN = "Origin";
    private static final String ORIGIN_VALUE = "http://e-commodity.fbp.com.ua";
    private static final String USER_AGENT = "User-Agent";
    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

    private HttpURLConnection connection;
    private Map<String, String> params;

    private String firstGetRequest() {
        try {
            URL url = new URL(E_COMMODITY_URL);
            connection = (HttpURLConnection) url.openConnection();
            setFirstRequestHeaders(connection);
            String str = getResponce(connection);
            getFirstRequestParams(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return EMPTY_STRING;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    List<String> processDate(Calendar calendar)  {
        System.out.println(String.format("Processing date: %s-%s-%S", calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
        List<String> pages = new ArrayList<>();
        if (params == null) {
            pages.add(firstGetRequest());
        }
        addDateToParams(calendar);
        int page = START_PAGE;
        boolean hasPages = true;
        String prevResp = EMPTY_STRING;
        while (hasPages) {
            addPageToParams(page);
            try {
                String urlParameters = getUrlParameters(params);
                URL url = new URL(E_COMMODITY_URL);
                connection = (HttpURLConnection) url.openConnection();
                setHeaders(connection, urlParameters);
                writeOutputToRequest(urlParameters);
                String str = getResponce(connection);
                if (str.equals(prevResp)) {
                    hasPages = false;
                }
                prevResp = str;
                pages.add(str);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            page++;
        }
        return pages;
    }

    private void writeOutputToRequest(String urlParameters) throws IOException {
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    private void addPageToParams(int page) {
        params.put(EVENTARGUMENT, PAGE + page);
    }

    private void addDateToParams(Calendar calendar) {
        SimpleDateFormat format1 = new SimpleDateFormat(DATE_PATTERN);
        String formatted = format1.format(calendar.getTime());
        params.put(TB_FROM, formatted);
        params.put(TB_TO, formatted);
    }

    private void getFirstRequestParams(String str) {
        params = new HashMap<>();
        String viewState = str.split(VIEWSTATE_BEGIN_RGX)[1].split(VIEVSTATE_END_RGX)[0];
        params.put(VIEWSTATE, viewState);
        String eventValidation = str.split(EVENTVALIDATION_BEGIN_RGX)[1].split(VIEVSTATE_END_RGX)[0];
        params.put(EVENTVALIDATION, eventValidation);
    }

    private void setFirstRequestHeaders(HttpURLConnection connection) throws IOException {
        connection.setRequestMethod(GET);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8);
        connection.setRequestProperty(CONTENT_LANGUAGE, CONTENT_LANG_VALUE);
        connection.setRequestProperty(CONNECTION, CONNECTION_VALUE);
        connection.setUseCaches(false);
        connection.setDoOutput(true);
    }

    private String getResponce(HttpURLConnection connection) throws IOException {
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        return response.toString();
    }

    private String getUrlParameters(Map<String, String> result) throws UnsupportedEncodingException {
        return URLEncoder.encode(CTL_00_$_SCRIPT_MANAGER_1, UTF_8) + EQUALS
                + URLEncoder.encode(CTL_00_$_SCRIPT_MANAGER_1_VALUE, UTF_8) + AND
                + URLEncoder.encode(CTL_00_SCRIPT_MANAGER_1_HIDDEN_FIELD, UTF_8) + EQUALS
                + URLEncoder.encode(CTL_00_SCRIPT_MANAGER_1_HIDDEN_FIELD_VALUE, UTF_8) + AND
                + URLEncoder.encode(VIEWSTATE, UTF_8) + EQUALS
                + URLEncoder.encode(result.get(VIEWSTATE), UTF_8) + AND
                + URLEncoder.encode(EVENTVALIDATION, UTF_8) + EQUALS
                + URLEncoder.encode(result.get(EVENTVALIDATION), UTF_8) + AND
                + URLEncoder.encode(EVENTTARGET, UTF_8) + EQUALS
                + URLEncoder.encode(EVENTTARGET_VALUE, UTF_8) + AND
                + URLEncoder.encode(EVENTARGUMENT, UTF_8) + EQUALS
                + URLEncoder.encode(result.get(EVENTARGUMENT), UTF_8) + AND
                + URLEncoder.encode(CTL_00_$_SEARCH_TEXT, UTF_8) + EQUALS
                + URLEncoder.encode(CTL_00_$_SEARCH_TEXT_VALUE, UTF_8) + AND
                + URLEncoder.encode(CTL_00_$_CONTENT_$_NUMBER_ID, UTF_8) + EQUALS
                + AND
                + URLEncoder.encode(TB_FROM, UTF_8) + EQUALS
                + URLEncoder.encode(result.get(TB_FROM), UTF_8) + AND
                + URLEncoder.encode(TB_TO, UTF_8) + EQUALS
                + URLEncoder.encode(result.get(TB_TO), UTF_8) + AND
                + URLEncoder.encode(ASYNCPOST, UTF_8) + EQUALS
                + URLEncoder.encode(ASYNCPOST_VALUE, UTF_8) + AND;
    }

    private void setHeaders(HttpURLConnection connection, String urlParameters) throws ProtocolException {
        connection.setRequestMethod(POST);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8);
        connection.setRequestProperty(X_REQUESTED_WITH, XML_HTTP_REQUEST_VALUE);
        connection.setRequestProperty(X_MICROSOFT_AJAX, X_MICROSOFT_AJAX_VALUE);
        connection.setRequestProperty(CACHE_CONTROL, CACHE_CONTROL_VALUE);
        connection.setRequestProperty(ACCEPT, ACCEPT_VALUE);
        connection.setRequestProperty(REFERER, REFERER_VALUE);
        connection.setRequestProperty(CONNECTION, CONNECTION_VALUE);
        connection.setRequestProperty(CONTENT_LENGTH, Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty(CONTENT_LANGUAGE, CONTENT_LANG_VALUE);
        connection.setRequestProperty(ACCEPT_ENCODING, ACCEPT_ENCODING_VALUE);
        connection.setRequestProperty(ORIGIN, ORIGIN_VALUE);
        connection.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
        connection.setUseCaches(false);
        connection.setDoOutput(true);
    }

}
