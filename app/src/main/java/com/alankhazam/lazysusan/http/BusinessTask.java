package com.alankhazam.lazysusan.http;

import android.util.Log;

import com.alankhazam.lazysusan.data.Business;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Asynchronous HTTP task with callback to get Businesses.
 * Created by AK on 11/24/2015.
 */
public class BusinessTask extends RequestTask {

    private static final String URL = "http://lazysusanapi2.herokuapp.com/business";
    private BusinessTaskCallback mCallback;

    public BusinessTask(String id) {
        super(URL + "/" + id);
    }

    @Override
    public void execute() {
        String request = getRequest();
        Log.d(getClass().getName(), "GET " + request);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(request, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (mCallback != null) {
                    List<Business> results = Business.parseArray(response);
                    if (results != null && !results.isEmpty()) {
                        mCallback.onBusinessTaskComplete(results.get(0));
                    }
                }
            }
        });
    }

    public interface BusinessTaskCallback extends RequestCallback {
        void onBusinessTaskComplete(Business business);
    }

    public BusinessTask setCallback(BusinessTaskCallback callback) {
        mCallback = callback;
        return this;
    }
}
