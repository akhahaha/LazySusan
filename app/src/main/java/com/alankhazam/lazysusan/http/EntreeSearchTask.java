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
 * Asynchronous HTTP task with callback to get Entrees.
 * Created by AK on 11/24/2015.
 */
public class EntreeSearchTask extends RequestTask {

    private static final String URL = "http://lazysusanapi2.herokuapp.com/search";
    private EntreeSearchCallback mCallback;

    public EntreeSearchTask() {
        super(URL);
    }

    public void setLocation(Location location) {
        setLocation(location.getLatitude(), location.getLongitude());
    }

    public void setLocation(double latitude, double longitude) {
        setParam("lat", latitude);
        setParam("lon", longitude);
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
                    Collection<Entree> entrees = Entree.parseArray(response);
                    Log.d(getClass().getName(), "Returned " + entrees.size() + " entrees");
                    mCallback.onEntreeSearchComplete(entrees);
                }
            }
        });
    }

    public interface EntreeSearchCallback extends RequestCallback {
        void onEntreeSearchComplete(Collection<Entree> entrees);
    }

    public RequestTask setCallback(EntreeSearchCallback callback) {
        mCallback = callback;
        return this;
    }
}
