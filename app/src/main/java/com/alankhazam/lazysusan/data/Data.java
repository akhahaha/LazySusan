package com.alankhazam.lazysusan.data;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Abstract base class for data objects.
 * Created by Alan on 11/24/2015.
 */
public abstract class Data implements Parcelable {
    /**
     * Retrieve a string array list with a given key from a JSON object.
     * @param jsonObject JSON object containing the array
     * @param key Array key
     * @return ArrayList<String> array with the key; null if not found
     */
    public static ArrayList<String> getJSONStringList(JSONObject jsonObject,
                                                      String key) {
        // Get the JSON array from the JSON object.
        JSONArray jsonArray = jsonObject.optJSONArray(key);

        // If there is no array, return null.
        if (jsonArray == null) {
            return null;
        }

        // Create a string array of the same length.
        ArrayList<String> strings = new ArrayList<>(jsonArray.length());

        // Copy the strings from the JSON array to the string array.
        for (int i = 0, size = jsonArray.length(); i < size; i++) {
            strings.add(jsonArray.optString(i));
        }

        return strings;
    }
}
