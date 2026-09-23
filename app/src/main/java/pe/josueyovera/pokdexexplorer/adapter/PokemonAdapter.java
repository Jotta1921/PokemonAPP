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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import pe.josueyovera.pokdexexplorer.R;
import pe.josueyovera.pokdexexplorer.helper.PokemonTypeHelper;
import pe.josueyovera.pokdexexplorer.model.PokemonItem;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<PokemonItem> pokemonList;
    private List<PokemonItem> fullList;
    private Set<Integer> favoriteIds;
    private OnPokemonClickListener clickListener;

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonItem pokemon, int position);
        void onFavoriteClick(PokemonItem pokemon, int position);
    }

    public PokemonAdapter(List<PokemonItem> pokemonList, OnPokemonClickListener clickListener) {
        this.pokemonList = pokemonList != null ? pokemonList : new ArrayList<>();
        this.fullList = new ArrayList<>(this.pokemonList);
        this.favoriteIds = new HashSet<>();
        this.clickListener = clickListener;
    }

    public void setFavoriteIds(Set<Integer> favoriteIds) {
        this.favoriteIds = favoriteIds != null ? favoriteIds : new HashSet<>();
        notifyDataSetChanged();
    }

    public void toggleFavoriteId(int id) {
        if (favoriteIds.contains(id)) {
            favoriteIds.remove(id);
        } else {
            favoriteIds.add(id);
        }
        notifyDataSetChanged();
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

    public void filterByType(String typeName) {
        if (typeName == null || typeName.trim().isEmpty() || "TODOS".equalsIgnoreCase(typeName.trim())) {
            pokemonList = new ArrayList<>(fullList);
        } else {
            List<PokemonItem> filtered = new ArrayList<>();
            for (PokemonItem item : fullList) {
                if (PokemonTypeHelper.hasType(item.getId(), typeName)) {
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

        // Configurar tipos reales desde PokemonTypeHelper
        configurarTipos(holder, pokemonId);

        // Estado e icono de Favorito
        boolean isFav = favoriteIds != null && favoriteIds.contains(pokemonId);
        if (isFav) {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
            holder.btnFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#DC2626")));
        } else {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border);
            holder.btnFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#64748B")));
        }

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
            toggleFavoriteId(pokemonId);
            if (clickListener != null) {
                clickListener.onFavoriteClick(pokemon, holder.getAdapterPosition());
            }
        });
    }

    private void configurarTipos(PokemonViewHolder holder, int id) {
        if (holder.tvPokemonType1 == null) return;

        PokemonTypeHelper.TypeInfo info = PokemonTypeHelper.getTypeInfo(id);

        if (info.type1 != null) {
            holder.tvPokemonType1.setText(info.type1);
            String colorHex1 = PokemonTypeHelper.getTypeColor(info.type1);
            holder.tvPokemonType1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorHex1)));
            holder.tvPokemonType1.setTextColor(Color.WHITE);
            holder.tvPokemonType1.setVisibility(View.VISIBLE);
        } else {
            holder.tvPokemonType1.setVisibility(View.GONE);
        }

        if (info.type2 != null) {
            holder.tvPokemonType2.setText(info.type2);
            holder.tvPokemonType2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2E8F0")));
            holder.tvPokemonType2.setTextColor(Color.parseColor("#475569"));
            holder.tvPokemonType2.setVisibility(View.VISIBLE);
        } else {
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
