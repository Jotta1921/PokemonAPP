package pe.josueyovera.pokdexexplorer.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pe.josueyovera.pokdexexplorer.R;
import pe.josueyovera.pokdexexplorer.model.PokemonItem;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<PokemonItem> pokemonList;
    private List<PokemonItem> fullList;
    private OnPokemonClickListener clickListener;

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonItem pokemon, int position);
        void onFavoriteClick(PokemonItem pokemon, int position);
    }

    public PokemonAdapter(List<PokemonItem> pokemonList, OnPokemonClickListener clickListener) {
        this.pokemonList = pokemonList != null ? pokemonList : new ArrayList<>();
        this.fullList = new ArrayList<>(this.pokemonList);
        this.clickListener = clickListener;
    }

    public void setPokemonList(List<PokemonItem> newPokemonList) {
        this.pokemonList = newPokemonList != null ? newPokemonList : new ArrayList<>();
        this.fullList = new ArrayList<>(this.pokemonList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        if (query == null || query.trim().isEmpty()) {
            pokemonList = new ArrayList<>(fullList);
        } else {
            List<PokemonItem> filtered = new ArrayList<>();
            String filterPattern = query.toLowerCase().trim();
            for (PokemonItem item : fullList) {
                if (item.getName() != null && item.getName().toLowerCase().contains(filterPattern)) {
                    filtered.add(item);
                }
            }
            pokemonList = filtered;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonItem pokemon = pokemonList.get(position);
        int pokemonId = pokemon.getId();

        // ID formateado (ej. #001)
        holder.tvPokemonId.setText(String.format(Locale.getDefault(), "#%03d", pokemonId));

        // Nombre con primera letra en mayúscula
        String name = pokemon.getName();
        if (name != null && !name.isEmpty()) {
            String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            holder.tvPokemonName.setText(capitalizedName);
        } else {
            holder.tvPokemonName.setText("");
        }

        // Configurar tipos según el Pokémon
        configurarTipos(holder, name, pokemonId);

        // Cargar imagen con Glide
        Glide.with(holder.itemView.getContext())
                .load(pokemon.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonId + ".png")
                .into(holder.ivPokemon);

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onPokemonClick(pokemon, holder.getAdapterPosition());
            }
        });

        holder.btnFavorite.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onFavoriteClick(pokemon, holder.getAdapterPosition());
            }
        });
    }

    private void configurarTipos(PokemonViewHolder holder, String name, int id) {
        if (holder.tvPokemonType1 == null) return;

        String n = name != null ? name.toLowerCase() : "";
        if (n.contains("bulbasaur") || n.contains("ivysaur") || n.contains("venusaur") || id == 1) {
            holder.tvPokemonType1.setText("PLANTA");
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#475569")));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);

            holder.tvPokemonType2.setText("VENENO");
            holder.tvPokemonType2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2E8F0")));
            holder.tvPokemonType2.setTextColor(Color.parseColor("#475569"));
            holder.tvPokemonType2.setVisibility(View.VISIBLE);
        } else if (n.contains("charmander") || n.contains("charmeleon") || n.contains("charizard") || id == 4) {
            holder.tvPokemonType1.setText("FUEGO");
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DC2626")));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);
            holder.tvPokemonType2.setVisibility(View.GONE);
        } else if (n.contains("squirtle") || n.contains("wartortle") || n.contains("blastoise") || id == 7) {
            holder.tvPokemonType1.setText("AGUA");
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#475569")));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);
            holder.tvPokemonType2.setVisibility(View.GONE);
        } else if (n.contains("pikachu") || n.contains("raichu") || id == 25) {
            holder.tvPokemonType1.setText("ELÉCTRICO");
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3B82F6")));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);
            holder.tvPokemonType2.setVisibility(View.GONE);
        } else if (n.contains("gengar") || id == 94) {
            holder.tvPokemonType1.setText("FANTASMA");
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#475569")));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);

            holder.tvPokemonType2.setText("VENENO");
            holder.tvPokemonType2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2E8F0")));
            holder.tvPokemonType2.setTextColor(Color.parseColor("#475569"));
            holder.tvPokemonType2.setVisibility(View.VISIBLE);
        } else {
            holder.tvPokemonType1.setText("NORMAL");
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#475569")));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);
            holder.tvPokemonType2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return pokemonList != null ? pokemonList.size() : 0;
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPokemon;
        TextView tvPokemonId;
        TextView tvPokemonName;
        TextView tvPokemonType1;
        TextView tvPokemonType2;
        ImageButton btnFavorite;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPokemon = itemView.findViewById(R.id.ivPokemon);
            tvPokemonId = itemView.findViewById(R.id.tvPokemonId);
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            tvPokemonType1 = itemView.findViewById(R.id.tvPokemonType1);
            tvPokemonType2 = itemView.findViewById(R.id.tvPokemonType2);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
