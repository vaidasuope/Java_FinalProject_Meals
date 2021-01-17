package com.meals.foodb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NewEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        setTitle("Selected meal information");

        Intent intent = getIntent();//naudojames tuo, ka paveldi. Cia kai is vieno lango pereinam i kita.
        Meals meals = (Meals) intent.getSerializableExtra(Adapter.ENTRY);//reuikia nurodyti kurios klases objekta grazinam
        Toast.makeText(this, "Meal: " + meals.getName(), Toast.LENGTH_SHORT).show();

        //pasiimsin visus elementus is xml failo su kuriais dirbsim
        final CheckBox checkBoxBeef = findViewById(R.id.meal_beef);
        final CheckBox checkBoxDessert = findViewById(R.id.meal_dessert);
        final CheckBox checkBoxVegetarian = findViewById(R.id.meal_vegetarian);
        final CheckBox checkBoxStarter = findViewById(R.id.meal_starter);

        final RadioGroup radioGroup = findViewById(R.id.area);
        RadioButton radioButton = findViewById(R.id.radioButton);

        final Spinner spinner = findViewById(R.id.ingredient);
        ArrayList<String> updateList = new ArrayList<String>();//programiskai sudesim datas i sita susikurta saarasa
        updateList.add(meals.getIngridients());
        updateList.add(getResources().getString(R.string.new_entry_1));
        updateList.add(getResources().getString(R.string.new_entry_2));
        updateList.add(getResources().getString(R.string.new_entry_3));
        updateList.add(getResources().getString(R.string.new_entry_4));
        updateList.add(getResources().getString(R.string.new_entry_5));

        //adapteris reikalingas sujungti isdestyma (vaizda) su sarasu
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                updateList
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);//adapteri idedame i spineri, susiejame su spineriu savo date adapteri susikurta

        final EditText tagsText = findViewById(R.id.tagsInput);

        Button btnNewEntry = findViewById(R.id.display_selected_btn);

        //uzpildysime visus elementus coronos informacija (konkretaus iraso)

        checkBoxBeef.setText(meals.getCategory());
        //cia konvertuojame int i string, nes setText tikisi, kad mes paduosim string jam, tai jeigu number yra, tai reikia konvertuoti
        radioButton.setText(String.valueOf(meals.getArea()));
        tagsText.setText(String.valueOf(meals.getTags()));

        //ant mygtuko paspaudimo parodysime vartotojo ivesta - koreguota informacija

        btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "";
                if (checkBoxBeef.isChecked()) {
                    category += checkBoxBeef.getText().toString() + ", ";
                }
                if (checkBoxDessert.isChecked()) {
                    category += checkBoxDessert.getText().toString() + ", ";
                }
                if (checkBoxVegetarian.isChecked()) {
                    category += checkBoxVegetarian.getText().toString() + ", ";
                }
                if (checkBoxStarter.isChecked()) {
                    category += checkBoxStarter.getText().toString() + ", ";
                }

                //gauname pasirinkta radio button is radio group
                int selectedRadionGroupId = radioGroup.getCheckedRadioButtonId();
                //cia surandam radio button pagal grazinta id is grupes
                RadioButton selectedButton = (RadioButton) findViewById(selectedRadionGroupId);
                String area = (selectedButton.getText().toString());

                String ingredients = String.valueOf(spinner.getSelectedItem());

                String tags = tagsText.getText().toString();

                if (Validation.isValidLetters(tags)){
                    Meals meals = new Meals(ingredients, area, tags, category);
                    //atvaizuojam vartotojui objekto informacija
                    Toast.makeText(
                            NewEntryActivity.this,
                            "Category: " + meals.getCategory() + "\n " +
                                    "Area: " + meals.getArea() + "\n" +
                                    "Tags: " + meals.getTags() + "\n " +
                                    "Ingredients: " + meals.getIngridients(),
                            Toast.LENGTH_SHORT
                    ).show();
                } else {//blogai ivesti tags duomenys
                    tagsText.setError(getResources().getString(R.string.new_entry_invalid_tags));
                    tagsText.requestFocus();
                }
            }
        });
    }
}