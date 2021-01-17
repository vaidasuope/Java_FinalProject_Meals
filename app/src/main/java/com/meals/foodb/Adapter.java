package com.meals.foodb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Meals> data;

    public static final String ENTRY = "com.meals.foodB.ENTRY";//raktas susideda is package ir entry musu
    //private boolean meals;

    // create constructor to initialize context and data sent from SearchActivity
    public Adapter(Context context, List<Meals> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_meals, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data - cia pildys kortele kiekvienu atveju, kai iskviesim, irasu
    //cia sitoj vietoj issitraukiam is saraso konkretu irasa ir uzpildom kiekviena elementa
    //cia persirasyti reikia kokius duomenis norim issitraukti
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Meals current = data.get(position);
        myHolder.textName.setText(current.getName());
        myHolder.textTags.setText("Tags: " + current.getTags());
        myHolder.textCategory.setText("Category: " + current.getCategory());
        myHolder.textArea.setText("Area: " + current.getArea());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textName;
        TextView textTags;
        TextView textCategory;
        TextView textArea;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textName);
            textTags = (TextView) itemView.findViewById(R.id.textTags);
            textCategory = (TextView) itemView.findViewById(R.id.textCategory);
            textArea = (TextView) itemView.findViewById(R.id.textArea);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {
            Intent goToNewEntryActivity = new Intent(context, NewEntryActivity.class);

            int itemPosition = getAdapterPosition();//grazina konkrecios korteles indeksa
            Meals meals = data.get(itemPosition);

            goToNewEntryActivity.putExtra(ENTRY, meals);

            context.startActivity(goToNewEntryActivity);
        }
    }
}
