package com.alankhazam.lazysusan.data;

import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Entree data class.
 */
public class Entree extends Data {

    public static int CONTENT_BASE = 0;

    public String name;
    public String id;

    public String businessName;
    public String businessId;
    public Business business;

    public Double price;
    public Double ratingAverage;
    public Integer ratingCount;
    public String description;

    public String displayImage;
    public ArrayList<String> images;

    // Relational information
    public ArrayList<String> cuisines;
    public ArrayList<String> foodTypes;
    public ArrayList<String> mealTypes;

    // Secondary information
    public ArrayList<String> ingredients;
    public ArrayList<String> restrictions;

    public Entree() {
    }

    public static Entree parse(JSONObject json) {
        Entree entree = new Entree();

        entree.name = json.optString("name", null);
        entree.id = json.optString("id", null);

        entree.businessName = json.optString("businessName", null);
        entree.businessId = json.optString("businessId", null);

        entree.price = json.optDouble("price", -1);
        entree.ratingAverage = json.optDouble("ratingAverage", -1);
        entree.ratingCount = json.optInt("ratingCount", -1);
        entree.description = json.optString("description", null);

        entree.displayImage = json.optString("displayImage", null);
        entree.images = getJSONStringList(json, "images");

        entree.cuisines = getJSONStringList(json, "cuisines");
        entree.foodTypes = getJSONStringList(json, "foodTypes");
        entree.mealTypes = getJSONStringList(json, "mealTypes");

        entree.ingredients = getJSONStringList(json, "ingredients");
        entree.restrictions = getJSONStringList(json, "restrictions");

        return entree;
    }

    public static ArrayList<Entree> parseArray(JSONArray jsonArray) {
        ArrayList<Entree> entrees = new ArrayList<Entree>();

        // Parse each element into a Entree and add to the list
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Entree entree = parse(jsonArray.getJSONObject(i));
                if (entree != null) {
                    entrees.add(entree);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return entrees;
    }

    public static final Creator<Entree> CREATOR = new Creator<Entree>() {
        public Entree createFromParcel(Parcel in) {
            return new Entree(in);
        }

        public Entree[] newArray(int size) {
            return new Entree[size];
        }
    };

    private Entree(Parcel in) {
        name = in.readString();
        id = in.readString();

        businessName = in.readString();
        businessId = in.readString();
        business = in.readParcelable(getClass().getClassLoader());

        price = in.readDouble();
        ratingAverage = in.readDouble();
        ratingCount = in.readInt();
        description = in.readString();

        displayImage = in.readString();
        in.readList(images, getClass().getClassLoader());

        in.readList(cuisines, getClass().getClassLoader());
        in.readList(foodTypes, getClass().getClassLoader());
        in.readList(mealTypes, getClass().getClassLoader());

        in.readList(ingredients, getClass().getClassLoader());
        in.readList(restrictions, getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        // No child classes
        return CONTENT_BASE;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(id);

        out.writeString(businessName);
        out.writeString(businessId);
        out.writeParcelable(business, business.describeContents());

        out.writeDouble(price);
        out.writeDouble(ratingAverage);
        out.writeInt(ratingCount);
        out.writeString(description);

        out.writeString(displayImage);
        out.writeList(images);

        out.writeList(cuisines);
        out.writeList(foodTypes);
        out.writeList(mealTypes);

        out.writeList(ingredients);
        out.writeList(restrictions);
    }
}
