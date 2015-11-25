package com.alankhazam.lazysusan.http;

/**
 * RequestTask abstract base class.
 * Created by Alan on 11/25/2015.
 */
public abstract class RequestTask {

    private String mURL;
    private RequestParams mParams;

    public RequestTask() {
        mParams = new RequestParams();
    }

    public RequestTask(String url) {
        this();
        setURL(url);
    }

    public void setURL(String url) {
        mURL = url;
    }

    public String getURL() {
        return mURL;
    }

    public void setParam(String key, String value) {
        mParams.put(key, value);
    }

    public void setParam(String key, int value) {
        mParams.put(key, value);
    }

    public void setParam(String key, double value) {
        mParams.put(key, value);
    }

    public void setQuery(String query) {
        mParams.put("q", query);
    }

    public RequestParams getParams() {
        return mParams;
    }

    public String getRequest() {
        return mParams.buildRequest(mURL);
    }

    public abstract void execute();

    public abstract interface RequestCallback {};
}
