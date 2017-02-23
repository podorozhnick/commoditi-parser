package com.podorozhnick.commoditiparser;

public class RequestHeader {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    public static final String X_MICROSOFT_AJAX = "X-MicrosoftAjax";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String ACCEPT = "Accept";
    public static final String REFERER = "Referer";
    public static final String CONNECTION = "Connection";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_LANGUAGE = "Content-Language";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ORIGIN = "Origin";
    public static final String USER_AGENT = "User-Agent";

    private String method;
    private String contentType;
    private String xRequestedWith;
    private String xMicrosoftAjax;
    private String cacheControl;
    private String accept;
    private String referer;
    private String connection;
    private String contentLength;
    private String contentLanguage;
    private String acceptEncoding;
    private String origin;
    private String userAgent;
    private Boolean useCaches;
    private Boolean doOutput;

    public String getMethod() {
        return method;
    }

    public RequestHeader setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public RequestHeader setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getxRequestedWith() {
        return xRequestedWith;
    }

    public RequestHeader setxRequestedWith(String xRequestedWith) {
        this.xRequestedWith = xRequestedWith;
        return this;
    }

    public String getxMicrosoftAjax() {
        return xMicrosoftAjax;
    }

    public RequestHeader setxMicrosoftAjax(String xMicrosoftAjax) {
        this.xMicrosoftAjax = xMicrosoftAjax;
        return this;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public RequestHeader setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
        return this;
    }

    public String getAccept() {
        return accept;
    }

    public RequestHeader setAccept(String accept) {
        this.accept = accept;
        return this;
    }

    public String getReferer() {
        return referer;
    }

    public RequestHeader setReferer(String referer) {
        this.referer = referer;
        return this;
    }

    public String getConnection() {
        return connection;
    }

    public RequestHeader setConnection(String connection) {
        this.connection = connection;
        return this;
    }

    public String getContentLength() {
        return contentLength;
    }

    public RequestHeader setContentLength(String contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public RequestHeader setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
        return this;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public RequestHeader setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public RequestHeader setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public RequestHeader setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public Boolean getUseCaches() {
        return useCaches;
    }

    public RequestHeader setUseCaches(Boolean useCaches) {
        this.useCaches = useCaches;
        return this;
    }

    public Boolean getDoOutput() {
        return doOutput;
    }

    public RequestHeader setDoOutput(Boolean doOutput) {
        this.doOutput = doOutput;
        return this;
    }
}
