package pe.josueyovera.pokdexexplorer.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import pe.josueyovera.pokdexexplorer.MainActivity;
import pe.josueyovera.pokdexexplorer.R;
import pe.josueyovera.pokdexexplorer.adapter.FavoritesAdapter;
import pe.josueyovera.pokdexexplorer.database.AppDatabase;
import pe.josueyovera.pokdexexplorer.model.PokemonFavorito;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnFavoriteClickListener {

    private RecyclerView recyclerFavorites;
    private ProgressBar progressBarFavorites;
    private View layoutFavListContainer;
    private View emptyContainerFavorites;
    private TextView tvFavCountSubtitle;
    private TextView tvFavNavBadge;
    private TextView btnToggleLista;
    private TextView btnToggleVacio;

    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);

        View mainView = findViewById(R.id.favoritesMainLayout);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarFavoritos();
    }

    private void initViews() {
        recyclerFavorites = findViewById(R.id.recyclerFavorites);
        progressBarFavorites = findViewById(R.id.progressBarFavorites);
        layoutFavListContainer = findViewById(R.id.layoutFavListContainer);
        emptyContainerFavorites = findViewById(R.id.emptyContainerFavorites);
        tvFavCountSubtitle = findViewById(R.id.tvFavCountSubtitle);
        tvFavNavBadge = findViewById(R.id.tvFavNavBadge);
        btnToggleLista = findViewById(R.id.btnToggleLista);
        btnToggleVacio = findViewById(R.id.btnToggleVacio);

        View navFavItemExplorar = findViewById(R.id.navFavItemExplorar);
        MaterialButton btnFavExplore = findViewById(R.id.btnFavExplore);
        MaterialButton btnExportShare = findViewById(R.id.btnExportShare);

        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));

        if (navFavItemExplorar != null) {
            navFavItemExplorar.setOnClickListener(v -> abrirExplorar());
        }

        if (btnFavExplore != null) {
            btnFavExplore.setOnClickListener(v -> abrirExplorar());
        }

        if (btnExportShare != null) {
            btnExportShare.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "¡Mira mi equipo Pokémon en PokéDex Explorer! Desarrollado con Android.");
                startActivity(Intent.createChooser(shareIntent, "Compartir tarjeta de entrenador"));
            });
        }

        // Toggles de la barra superior ("Lista" vs "Vacío")
        if (btnToggleLista != null && btnToggleVacio != null) {
            btnToggleLista.setOnClickListener(v -> mostrarEstadoListaView());
            btnToggleVacio.setOnClickListener(v -> mostrarEstadoVacioView());
        }
    }

    private void abrirExplorar() {
        Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void cargarFavoritos() {
        progressBarFavorites.setVisibility(View.VISIBLE);

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<PokemonFavorito> favoritos = db.pokemonFavoritoDao().obtenerTodos();

            runOnUiThread(() -> {
                progressBarFavorites.setVisibility(View.GONE);
                int size = favoritos.size();

                if (tvFavNavBadge != null) {
                    tvFavNavBadge.setText(String.valueOf(size));
                }

                if (size == 0) {
                    if (tvFavCountSubtitle != null) {
                        tvFavCountSubtitle.setText("0 guardados");
                    }
                    if (btnToggleLista != null) {
                        btnToggleLista.setText("Lista (0)");
                    }
                    mostrarEstadoVacioView();
                } else {
                    if (tvFavCountSubtitle != null) {
                        tvFavCountSubtitle.setText(String.format(Locale.getDefault(), "%d especímenes en memoria local", size));
                    }
                    if (btnToggleLista != null) {
                        btnToggleLista.setText(String.format(Locale.getDefault(), "❤️ Lista (%d)", size));
                    }
                    mostrarEstadoListaView();

                    adapter = new FavoritesAdapter(favoritos, FavoritesActivity.this);
                    recyclerFavorites.setAdapter(adapter);
                }
            });
        });
    }

    private void mostrarEstadoListaView() {
        if (layoutFavListContainer != null) layoutFavListContainer.setVisibility(View.VISIBLE);
        if (emptyContainerFavorites != null) emptyContainerFavorites.setVisibility(View.GONE);

        if (btnToggleLista != null && btnToggleVacio != null) {
            btnToggleLista.setBackgroundResource(R.drawable.bg_chip);
            btnToggleLista.setTextColor(Color.parseColor("#DC2626"));

            btnToggleVacio.setBackground(null);
            btnToggleVacio.setTextColor(Color.parseColor("#64748B"));
        }
    }

    private void mostrarEstadoVacioView() {
        if (layoutFavListContainer != null) layoutFavListContainer.setVisibility(View.GONE);
        if (emptyContainerFavorites != null) emptyContainerFavorites.setVisibility(View.VISIBLE);

        if (btnToggleLista != null && btnToggleVacio != null) {
            btnToggleLista.setBackground(null);
            btnToggleLista.setTextColor(Color.parseColor("#64748B"));

            btnToggleVacio.setBackgroundResource(R.drawable.bg_chip);
            btnToggleVacio.setTextColor(Color.parseColor("#DC2626"));
        }
    }

    @Override
    public void onItemClick(PokemonFavorito pokemon) {
        Intent intent = new Intent(FavoritesActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POKEMON_NAME, pokemon.getName());
        intent.putExtra(DetailActivity.EXTRA_POKEMON_ID, pokemon.getId());
        startActivity(intent);
    }

    @Override
    public void onRemoveFavorite(PokemonFavorito pokemon, int position) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(getApplicationContext())
                    .pokemonFavoritoDao()
                    .eliminarPorId(pokemon.getId());

            runOnUiThread(() -> {
                Toast.makeText(this, "Eliminado de favoritos: " + pokemon.getName(), Toast.LENGTH_SHORT).show();
                cargarFavoritos();
            });
        });
    }
}
