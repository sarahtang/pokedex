package com.juliazluo.www.pokedex;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener{

    protected PokemonHandler pokemonHandler;
    protected boolean[] chosenTypes;
    protected EditText attackInput, hpInput, dpInput;

    AutoCompleteTextView mAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonHandler = new PokemonHandler();
        ArrayList<String> pokemonTypes = pokemonHandler.getPokemonTypes();
        chosenTypes = new boolean[pokemonTypes.size()];

        ArrayList<Pokedex.Pokemon> pokemonList = pokemonHandler.getPokemonList();

        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewForSearch);
        SearchSuggestionsAdapter searchSuggestionsAdapter = new SearchSuggestionsAdapter(getApplicationContext(),
                R.layout.search_list_item, pokemonList);
        mAutoCompleteTextView.setAdapter(searchSuggestionsAdapter);
        mAutoCompleteTextView.setThreshold(1);

        // all types start out as selected
        for (int i = 0; i < chosenTypes.length; i++) {
            chosenTypes[i] = true;
        }

        MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.type_spinner);
        multiSpinner.setItems(pokemonTypes, "All types", this);
        attackInput = (EditText) findViewById(R.id.attack_input);
        hpInput = (EditText) findViewById(R.id.hp_input);
        dpInput = (EditText) findViewById(R.id.dp_input);


        (findViewById(R.id.filter_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minAttack = 0;
                int minDP = 0;
                int minHP = 0;

                if (attackInput.getText().length() > 0)
                    minAttack = Integer.parseInt(attackInput.getText().toString());
                if (hpInput.getText().length() > 0)
                    minHP = Integer.parseInt(hpInput.getText().toString());
                if (dpInput.getText().length() > 0)
                    minDP = Integer.parseInt(dpInput.getText().toString());

                Log.i("Input", minDP + " " + minHP + " " + minAttack);
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("RANDOMIZE", false);
                intent.putExtra(getString(R.string.chosen_types), chosenTypes);
                intent.putExtra(getString(R.string.min_attack), minAttack);
                intent.putExtra("MIN_DP", minDP);
                intent.putExtra("MIN_HP", minHP);
                startActivity(intent);
            }
        });

        (findViewById(R.id.random_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("RANDOMIZE", true);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search for a pokemon...");


        return true;
    }

    public void onItemsSelected(boolean[] selected) {
        chosenTypes = selected;
    }
}
