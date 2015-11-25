package com.alankhazam.lazysusan.data;

import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Business data class.
 */
public class Business extends Data {

    public String id;
    public String name;

    public String address;

    public String website;
    public String phone;

    public Business() {
    }

    public static Business parse(JSONObject jo) {
        Business business = new Business();

        business.id = jo.optString("businessId");
        business.name = jo.optString("businessName");
        business.address = jo.optString("address");
        business.website = jo.optString("website");
        business.phone = jo.optString("phone");

        return business;
    }

    public static ArrayList<Business> parseArray(JSONArray jsonArray) {
        ArrayList<Business> businesses = new ArrayList<Business>();

        // Parse each element into a Business and add to the list
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Business business = parse(jsonArray.getJSONObject(i));
                if (business != null) {
                    businesses.add(business);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return businesses;
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    private Business(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        website = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(address);
        out.writeString(website);
        out.writeString(phone);
    }
}
