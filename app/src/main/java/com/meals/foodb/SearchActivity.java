package com.meals.foodb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import android.app.SearchManager;
import android.support.v7.widget.SearchView;

public class SearchActivity extends AppCompatActivity {
    public static final String MEALS_API = "https://www.themealdb.com/api/json/v1/1/search.php?f=B";

    private RecyclerView recyclerView;
    private Adapter adapter;

    private ArrayList<Meals> mealsList = new ArrayList<Meals>();

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AsyncFetch asyncFetch = new AsyncFetch();
        asyncFetch.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu);

        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }

            // From all meals list creates specific list by searched country
            ArrayList<Meals> mealsByQuery = JSON.getMealsListByQuery(mealsList, query);

            if (mealsByQuery.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.search_no_results) + query, Toast.LENGTH_SHORT).show();
            }

            // Setup and Handover data to recyclerview
            recyclerView = (RecyclerView) findViewById(R.id.meals_list);
            adapter = new Adapter(SearchActivity.this, mealsByQuery);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        }
    }

    private class AsyncFetch extends AsyncTask<String, String, ArrayList<Meals>> {
        ProgressDialog pdLoading = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage(getResources().getString(R.string.search_loading_data));
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected ArrayList<Meals> doInBackground(String... params) {
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(MEALS_API);

                    JSONArray jsonArray = null;
                    mealsList = new ArrayList<Meals>();
                    try {
                        jsonArray = JSON.getJSONArray(jsonObject);
                        mealsList = JSON.getList(jsonArray);
                    } catch (JSONException e) {
                        Toast.makeText(
                                SearchActivity.this,
                                getResources().getText(R.string.search_error_reading_data) + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                    return mealsList;
            } catch (JSONException | IOException e1) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getText(R.string.search_error_reading_data) + e1.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return null;
            }
        }// doInBackground

        @Override
        protected void onPostExecute(ArrayList<Meals> mealsList) {
            //this method will be running on UI thread
            pdLoading.dismiss();

           if(mealsList != null) {
               Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_found_entries_from_api) + mealsList.size(), Toast.LENGTH_SHORT).show();
           }
        }//onPostExecute
    }//AsyncFetch class
}
