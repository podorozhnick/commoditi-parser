package com.podorozhnick.commoditiparser;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommoditiConnector {

    private static final String E_COMMODITY_URL = "http://e-commodity.fbp.com.ua/TradeForwards.aspx";
    private static final int START_PAGE = 2;

    private static final String FIRST_PAGE_DATA_JSON_PATH = "data/refresh_data";
    private static final String PAGE_DATA_JSON_PATH = "data/page_data";
    private static final String PAGE_HEADER_JSON_PATH = "data/page_header";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String EMPTY_STRING = "";
    private static final String PAGE = "Page$";
    private static final String STRAIGHT_RGX = "\\|";
    private static final String PAGE_EQUAL_PATTERN = "\\|hiddenField\\|";

    private HttpURLConnection connection;
    private RequestHeader requestHeader;

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
            if (prevResp.contains(FormData.EVENT_VALIDATION)) {
                pageData.setEventValidation(parseEventValidation(prevResp));
                pageData.setViewState(parseViewState(prevResp));
            }
            page++;
        }
        return pages;
    }

    private String parseEventValidation(String prevResp) {
        String[] arr = prevResp.split(FormData.EVENT_VALIDATION + STRAIGHT_RGX);
        String[] arr2 = arr[1].split(STRAIGHT_RGX);
        return arr2[0];
    }

    private String parseViewState(String prevResp) {
        String[] arr = prevResp.split(FormData.VIEW_STATE + STRAIGHT_RGX);
        String[] arr2 = arr[1].split(STRAIGHT_RGX);
        return arr2[0];
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

    private FormData getPageData() {
        return getFormDataFromFile(PAGE_DATA_JSON_PATH);
    }

    private FormData getFormDataFromFile(String filePath) {
        return new JsonFileReader<FormData>(filePath).read(FormData.class);
    }

    private RequestHeader getHeaders() {
        return new JsonFileReader<RequestHeader>(PAGE_HEADER_JSON_PATH).read(RequestHeader.class);
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
        RequestHeader requestHeader = getRequestHeader();
        requestHeader.setContentLength(Integer.toString(dataLength));
        connection.setRequestMethod(requestHeader.getMethod());
        connection.setRequestProperty(RequestHeader.CONTENT_TYPE, requestHeader.getContentType());
        connection.setRequestProperty(RequestHeader.X_REQUESTED_WITH, requestHeader.getxRequestedWith());
        connection.setRequestProperty(RequestHeader.X_MICROSOFT_AJAX, requestHeader.getxMicrosoftAjax());
        connection.setRequestProperty(RequestHeader.CACHE_CONTROL, requestHeader.getCacheControl());
        connection.setRequestProperty(RequestHeader.ACCEPT, requestHeader.getAccept());
        connection.setRequestProperty(RequestHeader.REFERER, requestHeader.getReferer());
        connection.setRequestProperty(RequestHeader.CONNECTION, requestHeader.getConnection());
        connection.setRequestProperty(RequestHeader.CONTENT_LENGTH, requestHeader.getContentLength());
        connection.setRequestProperty(RequestHeader.CONTENT_LANGUAGE, requestHeader.getContentLanguage());
        connection.setRequestProperty(RequestHeader.ACCEPT_ENCODING, requestHeader.getAcceptEncoding());
        connection.setRequestProperty(RequestHeader.ORIGIN, requestHeader.getOrigin());
        connection.setRequestProperty(RequestHeader.USER_AGENT, requestHeader.getUserAgent());
        connection.setUseCaches(requestHeader.getUseCaches());
        connection.setDoOutput(requestHeader.getDoOutput());
    }

    public RequestHeader getRequestHeader() {
        if (this.requestHeader == null) {
            this.requestHeader = getHeaders();
        }
        return requestHeader;
    }
}
