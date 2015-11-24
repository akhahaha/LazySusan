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

    public String listingId;
    public String name;

    public String address, city, state, zip;
    public Double latitude, longitude;
    public Double distance;

    public String website;
    public String phone;

    public Double ratingAverage;
    public int ratingCount;

    public String category;
    public int priceRange;

    public String imagePath;

    public ArrayList<String> cuisines;
    public ArrayList<Entree> entrees;

    public Business() {
    }

    public static Business parse(JSONObject jo) {
        Business business = new Business();

        business.listingId = jo.optString("listing_id");
        business.name = jo.optString("name");

        business.address = jo.optString("address");
        business.city = jo.optString("city");
        business.state = jo.optString("state");
        business.zip = jo.optString("zip");
        business.latitude = jo.optDouble("latitude");
        business.longitude = jo.optDouble("longitude");
        business.distance = jo.optDouble("distance");

        business.website = jo.optString("website");
        business.phone = jo.optString("phone");

        business.ratingAverage = jo.optDouble("average_rating");
        business.ratingCount = jo.optInt("rating_count");

        business.category = jo.optString("nearby_category");

        // Parse features
        JSONObject features = jo.optJSONObject("features");
        if (features != null) {
            // Get priceRange
            JSONArray priceRange = features
                    .optJSONArray("restaurant_price_range");
            business.priceRange = 0;
            if (priceRange != null) {
                // Look for the highest priceRange value in the array.
                for (int i = 0, size = priceRange.length(); i < size; i++) {
                    int price = 0;

                    try {
                        price = Integer.parseInt(priceRange.getString(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (business.priceRange < price) {
                        business.priceRange = price;
                    }
                }
            }

            // Get cuisines
            business.cuisines = getJSONStringList(features, "cuisine");
        }

        // Parse image paths
        JSONArray imageUrls = jo.optJSONArray("img_paths");
        if (imageUrls != null) {
            // Use last image (more likely to be of food)
            JSONObject image = (JSONObject) imageUrls
                    .opt(imageUrls.length() - 1);
            if (image != null) {
                business.imagePath = image.optString("full_image_path");
            } else {
                return null;
            }
        }

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
        listingId = in.readString();
        name = in.readString();

        address = in.readString();
        city = in.readString();
        state = in.readString();
        zip = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        distance = in.readDouble();

        website = in.readString();
        phone = in.readString();

        ratingAverage = in.readDouble();
        ratingCount = in.readInt();

        category = in.readString();
        priceRange = in.readInt();

        imagePath = in.readString();

        in.readList(cuisines, getClass().getClassLoader());
        in.readList(entrees, getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(listingId);
        out.writeString(name);

        out.writeString(address);
        out.writeString(city);
        out.writeString(state);
        out.writeString(zip);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeDouble(distance);

        out.writeString(website);
        out.writeString(phone);

        out.writeDouble(ratingAverage);
        out.writeInt(ratingCount);

        out.writeString(category);
        out.writeInt(priceRange);

        out.writeString(imagePath);

        out.writeList(cuisines);
        out.writeList(entrees);
    }
}
