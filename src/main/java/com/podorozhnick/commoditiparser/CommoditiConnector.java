package com.podorozhnick.commoditiparser;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommoditiConnector {

    private static final String E_COMMODITY_URL = "http://e-commodity.fbp.com.ua/TradeForwards.aspx";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8 = "application/x-www-form-urlencoded; charset=UTF-8";
    private static final String CONTENT_LANGUAGE = "Content-Language";
    private static final String CONTENT_LANG_VALUE = "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";
    private static final String CONNECTION = "Connection";
    private static final String CONNECTION_VALUE = "keep-alive";
    private static final String EMPTY_STRING = "";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final int START_PAGE = 2;
    private static final String PAGE = "Page$";
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
    private static final String FIRST_PAGE_DATA_JSON_PATH = "data/refresh_data";
    private static final String PAGE_DATA_JSON_PATH = "data/page_data";
    private static final String PAGE_EQUAL_PATTERN = "\\|hiddenField\\|";

    private HttpURLConnection connection;

    private String firstPageRequest(Calendar calendar) {
        try {
            URL url = new URL(E_COMMODITY_URL);
            connection = (HttpURLConnection) url.openConnection();
            FormData firstPageData = getFirstPageData();
            addDateToData(calendar, firstPageData);
            setHeaders(connection, firstPageData.toString().getBytes().length);
            writeOutputToRequest(firstPageData.toString());
            return getResponce(connection);
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
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
        List<String> pages = new ArrayList<>();
        String firstPage = firstPageRequest(calendar);
        pages.add(firstPage);
        String prevResp = firstPage;
        FormData pageData = getPageData();
        addDateToData(calendar, pageData);
        int page = START_PAGE;
        while (true) {
            addPageToData(page, pageData);
            try {
                URL url = new URL(E_COMMODITY_URL);
                connection = (HttpURLConnection) url.openConnection();
                setHeaders(connection, pageData.toString().getBytes().length);
                writeOutputToRequest(pageData.toString());
                String str = getResponce(connection);
                if (str.split(PAGE_EQUAL_PATTERN)[0].equals(prevResp.split(PAGE_EQUAL_PATTERN)[0])) {
                    break;
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

    private void addDateToData(Calendar calendar, FormData firstPageData) {
        SimpleDateFormat format1 = new SimpleDateFormat(DATE_PATTERN);
        String formatted = format1.format(calendar.getTime());
        firstPageData.setContentTbFrom(formatted);
        firstPageData.setContentTbTo(formatted);
    }

    private FormData getFirstPageData() {
        return getFormDataFromFile(FIRST_PAGE_DATA_JSON_PATH);
    }

    private FormData getFormDataFromFile(String filePath) {
        return new JsonFileReader<FormData>(filePath).read(FormData.class);
    }

    private FormData getPageData() {
        return getFormDataFromFile(PAGE_DATA_JSON_PATH);
    }

    private void writeOutputToRequest(String urlParameters) throws IOException {
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    private void addPageToData(int page, FormData data) {
        data.setEventArgument(PAGE + page);
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

    private void setHeaders(HttpURLConnection connection, int dataLength) throws ProtocolException {
        connection.setRequestMethod(POST);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED_CHARSET_UTF_8);
        connection.setRequestProperty(X_REQUESTED_WITH, XML_HTTP_REQUEST_VALUE);
        connection.setRequestProperty(X_MICROSOFT_AJAX, X_MICROSOFT_AJAX_VALUE);
        connection.setRequestProperty(CACHE_CONTROL, CACHE_CONTROL_VALUE);
        connection.setRequestProperty(ACCEPT, ACCEPT_VALUE);
        connection.setRequestProperty(REFERER, REFERER_VALUE);
        connection.setRequestProperty(CONNECTION, CONNECTION_VALUE);
        connection.setRequestProperty(CONTENT_LENGTH, Integer.toString(dataLength));
        connection.setRequestProperty(CONTENT_LANGUAGE, CONTENT_LANG_VALUE);
        connection.setRequestProperty(ACCEPT_ENCODING, ACCEPT_ENCODING_VALUE);
        connection.setRequestProperty(ORIGIN, ORIGIN_VALUE);
        connection.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
        connection.setUseCaches(false);
        connection.setDoOutput(true);
    }

}
