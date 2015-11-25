package com.alankhazam.lazysusan.http;

import android.location.Location;
import android.util.Log;

import com.alankhazam.lazysusan.data.Entree;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.Collection;

import cz.msebera.android.httpclient.Header;

/**
 * Asynchronous HTTP task with callback to get entrees.
 * Created by AK on 11/24/2015.
 */
public class EntreeSearchTask {

    private static final String BASE_URL = "http://lazysusanapi2.herokuapp.com/search";
    private RequestParams mParams;
    private EntreeSearchCallback mCallback;

    public EntreeSearchTask() {
        mParams = new RequestParams();
    }

    public void setParam(String key, String value) {
        mParams.put(key, value);
    }

    public void setQuery(String query) {
        mParams.put("q", query);
    }

    public void setLocation(Location location) {
        setLocation(location.getLatitude(), location.getLongitude());
    }

    public void setLocation(double latitude, double longitude) {
        mParams.put("lat", latitude);
        mParams.put("lon", longitude);
    }

    public void execute() {
        String request = mParams.buildRequest(BASE_URL);
        Log.d(getClass().getName(), "GET " + request);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(request, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (mCallback != null) {
                    Collection<Entree> entrees = Entree.parseArray(response);
                    Log.d(getClass().getName(), "Returned " + entrees.size() + " entrees");
                    mCallback.onEntreeSearchComplete(entrees);
                }
            }
        });
    }

    public interface EntreeSearchCallback {
        public void onEntreeSearchComplete(Collection<Entree> entrees);
    }

    public void setCallback(EntreeSearchCallback callback) {
        mCallback = callback;
    }
}
