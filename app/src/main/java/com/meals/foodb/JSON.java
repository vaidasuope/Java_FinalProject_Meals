package com.meals.foodb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class JSON {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //apsirasom sarasus, perduodam visa valstybiu sarasa ir ta valstybe pagal kuria nori filtruoti sarasa klientas
    //graziname tos valstybes sarasa su atitinkamais duomenimis, kurio pageidavo klientas

    public static JSONArray getJSONArray(JSONObject json) throws JSONException {

        //JSONObject to JSONArray
        JSONArray jsonArray = json.getJSONArray("meals");
        return jsonArray;
    }

    public static ArrayList<Meals> getList(JSONArray jsonArray) throws JSONException {
        ArrayList<Meals> mealsList = new ArrayList<Meals>();
        // Extract data from json and store into ArrayList as class objects
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json_data = jsonArray.getJSONObject(i);
            Meals meals = new Meals(
                    json_data.getString("strIngredient1"),
                    json_data.getString("strMeal"),
                    json_data.getString("strTags"),
                    json_data.getString("strCategory"),
                    json_data.getString("strArea")
            );
            mealsList.add(meals);
        }
        return mealsList;
    }

    public static ArrayList<Meals> getMealsListByQuery(ArrayList<Meals> mealsList, String mealName) {
        ArrayList<Meals> mealsListByQuery = new ArrayList<Meals>();
        for (Meals meals : mealsList) {
            if (meals.getName().contains(mealName)) {
                mealsListByQuery.add(meals);
            }
        }
        return mealsListByQuery;
    }

}
