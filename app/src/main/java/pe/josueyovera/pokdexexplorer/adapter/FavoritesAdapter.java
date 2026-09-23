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
import pe.josueyovera.pokdexexplorer.model.PokemonFavorito;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<PokemonFavorito> favoriteList;
    private OnFavoriteClickListener listener;

    public interface OnFavoriteClickListener {
        void onItemClick(PokemonFavorito pokemon);
        void onRemoveFavorite(PokemonFavorito pokemon, int position);
    }

    public FavoritesAdapter(List<PokemonFavorito> favoriteList, OnFavoriteClickListener listener) {
        this.favoriteList = favoriteList != null ? favoriteList : new ArrayList<>();
        this.listener = listener;
    }

    public void setFavoriteList(List<PokemonFavorito> favoriteList) {
        this.favoriteList = favoriteList != null ? favoriteList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_pokemon, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        PokemonFavorito pokemon = favoriteList.get(position);
        int id = pokemon.getId();
        String name = pokemon.getName();

        holder.tvFavPokemonId.setText(String.format(Locale.getDefault(), "#%03d", id));

        if (name != null && !name.isEmpty()) {
            String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
            holder.tvFavPokemonName.setText(capitalized);
        } else {
            holder.tvFavPokemonName.setText("Pokémon");
        }

        // Estilos e info por tipo
        configurarEstiloTipo(holder, name, id);

        // Imagen
        String imageUrl = pokemon.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";
        }

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.ivFavPokemonImage);

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(pokemon);
            }
        });

        holder.btnFavItemHeart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveFavorite(pokemon, holder.getAdapterPosition());
            }
        });
    }

    private void configurarEstiloTipo(FavoriteViewHolder holder, String name, int id) {
        String n = name != null ? name.toLowerCase() : "";

        if (n.contains("pikachu") || n.contains("raichu") || id == 25) {
            holder.tvFavPokemonType.setText("ELÉCTRICO");
            holder.tvFavPokemonType.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EAB308")));
            holder.tvFavPokemonSubInfo.setText("⚡ 60 HP • Estática");
            holder.viewFavoriteGlow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEF08A")));
        } else if (n.contains("charmander") || n.contains("charmeleon") || n.contains("charizard") || id == 4) {
            holder.tvFavPokemonType.setText("FUEGO");
            holder.tvFavPokemonType.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EA580C")));
            holder.tvFavPokemonSubInfo.setText("🔥 39 HP • Mar Llamas");
            holder.viewFavoriteGlow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFEDD5")));
        } else if (n.contains("squirtle") || n.contains("blastoise") || id == 7) {
            holder.tvFavPokemonType.setText("AGUA");
            holder.tvFavPokemonType.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0284C7")));
            holder.tvFavPokemonSubInfo.setText("💧 44 HP • Torrente");
            holder.viewFavoriteGlow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E0F2FE")));
        } else if (n.contains("bulbasaur") || id == 1) {
            holder.tvFavPokemonType.setText("PLANTA");
            holder.tvFavPokemonType.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
            holder.tvFavPokemonSubInfo.setText("🌿 45 HP • Espesura");
            holder.viewFavoriteGlow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DCFCE7")));
        } else {
            holder.tvFavPokemonType.setText("POKÉMON");
            holder.tvFavPokemonType.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#475569")));
            holder.tvFavPokemonSubInfo.setText("⭐ Guardado en memoria local");
            holder.viewFavoriteGlow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F1F5F9")));
        }
    }

    @Override
    public int getItemCount() {
        return favoriteList != null ? favoriteList.size() : 0;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        View viewFavoriteGlow;
        TextView tvFavPokemonId;
        TextView tvFavPokemonName;
        TextView tvFavPokemonType;
        TextView tvFavPokemonSubInfo;
        ImageView ivFavPokemonImage;
        ImageButton btnFavItemHeart;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            viewFavoriteGlow = itemView.findViewById(R.id.viewFavoriteGlow);
            tvFavPokemonId = itemView.findViewById(R.id.tvFavPokemonId);
            tvFavPokemonName = itemView.findViewById(R.id.tvFavPokemonName);
            tvFavPokemonType = itemView.findViewById(R.id.tvFavPokemonType);
            tvFavPokemonSubInfo = itemView.findViewById(R.id.tvFavPokemonSubInfo);
            ivFavPokemonImage = itemView.findViewById(R.id.ivFavPokemonImage);
            btnFavItemHeart = itemView.findViewById(R.id.btnFavItemHeart);
        }
    }
}
