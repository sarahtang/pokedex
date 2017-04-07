package com.juliazluo.www.pokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

/**
 * Created by julia on 2017-02-14.
 * MAIN ADAPTER
 */

public class FilteredListAdapter extends RecyclerView.Adapter<FilteredListAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<Pokedex.Pokemon> pokemonList;
    private boolean isLinear = true;

    public FilteredListAdapter(Context context, ArrayList<Pokedex.Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }


    @Override
    public FilteredListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    public void setIsLinear(boolean b) {
        isLinear = b;
    }

    @Override
    public void onBindViewHolder(final FilteredListAdapter.CustomViewHolder holder, int position) {
        final Pokedex.Pokemon pokemon = pokemonList.get(position);

        holder.nameText.setText(pokemon.name);
        holder.numText.setText(pokemon.number); //Switched from hp to number


        Glide.with(context)
                .load("http://assets.pokemon.com/assets/cms2/img/pokedex/full/" + pokemon.number + ".png")
                .into(holder.imageView);


        holder.cardViewListHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("POKEMON", pokemon);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public void setFilter(ArrayList<Pokedex.Pokemon> newList) {
        pokemonList.clear();
        pokemonList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, numText;
        ImageView imageView;
        CardView cardViewListHolder;

        public CustomViewHolder(View view) {
            super(view);


            nameText = (TextView) view.findViewById(R.id.filter_list_name); //need view. because not in an activity
            imageView = (ImageView) view.findViewById(R.id.filter_list_image);
            numText = (TextView) view.findViewById(R.id.filter_list_hp);
            cardViewListHolder = (CardView) view.findViewById(R.id.filterListCardView);
            
        }
    }
}
