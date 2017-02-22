package com.podorozhnick.commoditiparser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.sun.xml.internal.stream.writers.WriterUtility.UTF_8;

public class FormData {

    public static final String CTL_SCRIPT_MANAGER = "ctl00$ScriptManager1";
    public static final String CTL_SCRIPT_MANAGER_HIDDEN_FIELD = "ctl00_ScriptManager1_HiddenField";
    public static final String SEARCH_TEXT = "ctl00$SearchText";
    public static final String CONTENT_NUMBER_ID = "ctl00$Content$NumberId";
    public static final String CONTENT_EDRPOUINN = "ctl00$Content$EDRPOUINN";
    public static final String CONTENT_TB_FROM = "ctl00$Content$tbFrom";
    public static final String CONTENT_TB_TO = "ctl00$Content$tbTo";
    public static final String EVENT_TARGET = "__EVENTTARGET";
    public static final String EVENT_ARGUMENT = "__EVENTARGUMENT";
    public static final String VIEW_STATE = "__VIEWSTATE";
    public static final String EVENT_VALIDATION = "__EVENTVALIDATION";
    public static final String ASYNC_POST = "__ASYNCPOST";
    public static final String CTL_BTN_REFRESH = "ctl00$Content$btnRefresh";

    private static final String EQUALS = "=";
    private static final String AND = "&";
    private static final String EMPTY_STRING = "";

    private String ctlScriptManager;
    private String ctlScriptManagerHiddenField;
    private String searchText;
    private String contentNumberId;
    private String contentEDRPOUINN;
    private String contentTbFrom;
    private String contentTbTo;
    private String eventTarget;
    private String eventArgument;
    private String viewState;
    private String eventValidation;
    private String asyncPost;
    private String ctlContentBtnRefresh;

    public String getCtlScriptManager() {
        return ctlScriptManager == null ? EMPTY_STRING : ctlScriptManager;
    }

    public FormData setCtlScriptManager(String ctlScriptManager) {
        this.ctlScriptManager = ctlScriptManager;
        return this;
    }

    public String getCtlScriptManagerHiddenField() {
        return ctlScriptManagerHiddenField == null ? EMPTY_STRING : ctlScriptManagerHiddenField;
    }

    public FormData setCtlScriptManagerHiddenField(String ctlScriptManagerHiddenField) {
        this.ctlScriptManagerHiddenField = ctlScriptManagerHiddenField;
        return this;
    }

    public String getSearchText() {
        return searchText == null ? EMPTY_STRING : searchText;
    }

    public FormData setSearchText(String searchText) {
        this.searchText = searchText;
        return this;
    }

    public String getContentNumberId() {
        return contentNumberId == null ? EMPTY_STRING : contentNumberId;
    }

    public FormData setContentNumberId(String contentNumberId) {
        this.contentNumberId = contentNumberId;
        return this;
    }

    public String getContentEDRPOUINN() {
        return contentEDRPOUINN == null ? EMPTY_STRING : contentEDRPOUINN;
    }

    public FormData setContentEDRPOUINN(String contentEDRPOUINN) {
        this.contentEDRPOUINN = contentEDRPOUINN;
        return this;
    }

    public String getContentTbFrom() {
        return contentTbFrom == null ? EMPTY_STRING : contentTbFrom;
    }

    public FormData setContentTbFrom(String contentTbFrom) {
        this.contentTbFrom = contentTbFrom;
        return this;
    }

    public String getContentTbTo() {
        return contentTbTo == null ? EMPTY_STRING : contentTbTo;
    }

    public FormData setContentTbTo(String contentTbTo) {
        this.contentTbTo = contentTbTo;
        return this;
    }

    public String getEventTarget() {
        return eventTarget == null ? EMPTY_STRING : eventTarget;
    }

    public FormData setEventTarget(String eventTarget) {
        this.eventTarget = eventTarget;
        return this;
    }

    public String getViewState() {
        return viewState == null ? EMPTY_STRING : viewState;
    }

    public FormData setViewState(String viewState) {
        this.viewState = viewState;
        return this;
    }

    public String getEventValidation() {
        return eventValidation == null ? EMPTY_STRING : eventValidation;
    }

    public FormData setEventValidation(String eventValidation) {
        this.eventValidation = eventValidation;
        return this;
    }

    public String getAsyncPost() {
        return asyncPost == null ? EMPTY_STRING : asyncPost;
    }

    public FormData setAsyncPost(String asyncPost) {
        this.asyncPost = asyncPost;
        return this;
    }

    public String getEventArgument() {
        return eventArgument == null ? EMPTY_STRING : eventArgument;
    }

    public FormData setEventArgument(String eventArgument) {
        this.eventArgument = eventArgument;
        return this;
    }

    public String getCtlContentBtnRefresh() {
        return ctlContentBtnRefresh;
    }

    public FormData setCtlContentBtnRefresh(String ctlContentBtnRefresh) {
        this.ctlContentBtnRefresh = ctlContentBtnRefresh;
        return this;
    }

    @Override
    public String toString() {
        String str = null;
        try {
            str = URLEncoder.encode(CTL_SCRIPT_MANAGER, UTF_8) + EQUALS
                    + URLEncoder.encode(getCtlScriptManager(), UTF_8) + AND
                    + URLEncoder.encode(CTL_SCRIPT_MANAGER_HIDDEN_FIELD, UTF_8) + EQUALS
                    + URLEncoder.encode(getCtlScriptManagerHiddenField(), UTF_8) + AND
                    + URLEncoder.encode(SEARCH_TEXT, UTF_8) + EQUALS
                    + URLEncoder.encode(getSearchText(), UTF_8) + AND
                    + URLEncoder.encode(CONTENT_NUMBER_ID, UTF_8) + EQUALS
                    + URLEncoder.encode(getContentNumberId(), UTF_8) + AND
                    + URLEncoder.encode(CONTENT_EDRPOUINN, UTF_8) + EQUALS
                    + URLEncoder.encode(getContentEDRPOUINN(), UTF_8) + AND
                    + URLEncoder.encode(CONTENT_TB_FROM, UTF_8) + EQUALS
                    + URLEncoder.encode(getContentTbFrom(), UTF_8) + AND
                    + URLEncoder.encode(CONTENT_TB_TO, UTF_8) + EQUALS
                    + URLEncoder.encode(getContentTbTo(), UTF_8) + AND
                    + URLEncoder.encode(EVENT_TARGET, UTF_8) + EQUALS
                    + URLEncoder.encode(getEventTarget(), UTF_8) + AND
                    + URLEncoder.encode(EVENT_ARGUMENT, UTF_8) + EQUALS
                    + URLEncoder.encode(getEventArgument(), UTF_8) + AND
                    + URLEncoder.encode(VIEW_STATE, UTF_8) + EQUALS
                    + URLEncoder.encode(getViewState(), UTF_8) + AND
                    + URLEncoder.encode(EVENT_VALIDATION, UTF_8) + EQUALS
                    + URLEncoder.encode(getEventValidation(), UTF_8) + AND
                    + URLEncoder.encode(ASYNC_POST, UTF_8) + EQUALS
                    + URLEncoder.encode(getAsyncPost(), UTF_8) + AND
                    + (getCtlContentBtnRefresh() != null ? (URLEncoder.encode(CTL_BTN_REFRESH, UTF_8) + EQUALS
                    + URLEncoder.encode(getCtlContentBtnRefresh(), UTF_8) + AND) : EMPTY_STRING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
