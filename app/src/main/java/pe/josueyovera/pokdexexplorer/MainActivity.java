package pe.josueyovera.pokdexexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import pe.josueyovera.pokdexexplorer.adapter.PokemonAdapter;
import pe.josueyovera.pokdexexplorer.api.RetrofitClient;
import pe.josueyovera.pokdexexplorer.database.AppDatabase;
import pe.josueyovera.pokdexexplorer.model.PokemonDetail;
import pe.josueyovera.pokdexexplorer.model.PokemonItem;
import pe.josueyovera.pokdexexplorer.model.PokemonResponse;
import pe.josueyovera.pokdexexplorer.ui.DetailActivity;
import pe.josueyovera.pokdexexplorer.ui.FavoritesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonClickListener {

    private static final String TAG = "POKEAPI";

    private RecyclerView recyclerPokemon;
    private ProgressBar progressBar;
    private EditText etSearch;
    private View errorContainer;
    private TextView tvErrorMessage;
    private Button btnRetry;

    private TextView chipFilterTodos;
    private TextView chipFilterFuego;
    private TextView chipFilterAgua;
    private TextView chipFilterPlanta;
    private TextView chipFilterElectrico;

    private View navItemFavoritos;
    private TextView tvNavFavoritesBadge;

    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        cargarPokemon();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarContadorFavoritos();
    }

    private void initViews() {
        recyclerPokemon = findViewById(R.id.recyclerPokemon);
        progressBar = findViewById(R.id.progressBar);
        etSearch = findViewById(R.id.etSearch);
        errorContainer = findViewById(R.id.errorContainer);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        btnRetry = findViewById(R.id.btnRetry);

        chipFilterTodos = findViewById(R.id.chipFilterTodos);
        chipFilterFuego = findViewById(R.id.chipFilterFuego);
        chipFilterAgua = findViewById(R.id.chipFilterAgua);
        chipFilterPlanta = findViewById(R.id.chipFilterPlanta);
        chipFilterElectrico = findViewById(R.id.chipFilterElectrico);

        navItemFavoritos = findViewById(R.id.navItemFavoritos);
        tvNavFavoritesBadge = findViewById(R.id.tvNavFavoritesBadge);

        recyclerPokemon.setLayoutManager(new LinearLayoutManager(this));

        btnRetry.setOnClickListener(v -> cargarPokemon());

        if (navItemFavoritos != null) {
            navItemFavoritos.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            });
        }

        // Filtros de tipo
        setupTypeFilters();

        // Búsqueda
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (adapter != null) {
                        adapter.filter(s.toString());
                        if (adapter.getItemCount() == 0 && s.length() > 2) {
                            tvErrorMessage.setText("No se encontraron coincidencias locales.");
                        } else {
                            errorContainer.setVisibility(View.GONE);
                            recyclerPokemon.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            etSearch.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = etSearch.getText().toString().trim();
                    if (!query.isEmpty() && (adapter == null || adapter.getItemCount() == 0)) {
                        buscarPokemonRemoto(query);
                    }
                    return true;
                }
                return false;
            });
        }
    }

    private void setupTypeFilters() {
        if (chipFilterTodos == null) return;

        chipFilterTodos.setOnClickListener(v -> {
            if (adapter != null) adapter.filter("");
        });
        chipFilterFuego.setOnClickListener(v -> {
            if (adapter != null) adapter.filter("char");
        });
        chipFilterAgua.setOnClickListener(v -> {
            if (adapter != null) adapter.filter("squirt");
        });
        chipFilterPlanta.setOnClickListener(v -> {
            if (adapter != null) adapter.filter("bulb");
        });
        chipFilterElectrico.setOnClickListener(v -> {
            if (adapter != null) adapter.filter("pika");
        });
    }

    private void actualizarContadorFavoritos() {
        Executors.newSingleThreadExecutor().execute(() -> {
            int count = AppDatabase.getInstance(getApplicationContext())
                    .pokemonFavoritoDao()
                    .obtenerTodos().size();

            runOnUiThread(() -> {
                if (tvNavFavoritesBadge != null) {
                    tvNavFavoritesBadge.setText(String.valueOf(count));
                }
            });
        });
    }

    private void cargarPokemon() {
        mostrarEstadoCarga();

        RetrofitClient.getApiService().obtenerPokemon(151, 0).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonItem> pokemonList = response.body().getResults();
                    if (pokemonList != null && !pokemonList.isEmpty()) {
                        mostrarEstadoExito();
                        adapter = new PokemonAdapter(pokemonList, MainActivity.this);
                        recyclerPokemon.setAdapter(adapter);
                    } else {
                        mostrarEstadoError("No se encontraron Pokémon disponibles.");
                    }
                } else {
                    Log.e(TAG, "Error al cargar Pokémon: " + response.code());
                    mostrarEstadoError("Ocurrió un error al cargar la lista de Pokémon.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Fallo de red al conectar con PokéAPI", t);
                mostrarEstadoError("No se pudo conectar al servidor.\nPor favor, verifica tu conexión a internet.");
            }
        });
    }

    private void buscarPokemonRemoto(String query) {
        mostrarEstadoCarga();

        RetrofitClient.getApiService().buscarPorNombre(query.toLowerCase()).enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDetail> call, @NonNull Response<PokemonDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarEstadoExito();
                    PokemonDetail detail = response.body();
                    PokemonItem item = new PokemonItem(detail.getName(), "https://pokeapi.co/api/v2/pokemon/" + detail.getId() + "/");
                    adapter = new PokemonAdapter(Collections.singletonList(item), MainActivity.this);
                    recyclerPokemon.setAdapter(adapter);
                } else {
                    mostrarEstadoError("No encontramos ningún Pokémon llamado '" + query + "'.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonDetail> call, @NonNull Throwable t) {
                mostrarEstadoError("Verifica tu conexión a internet para realizar la búsqueda.");
            }
        });
    }

    private void mostrarEstadoCarga() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerPokemon.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }

    private void mostrarEstadoExito() {
        progressBar.setVisibility(View.GONE);
        recyclerPokemon.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }

    private void mostrarEstadoError(String mensaje) {
        progressBar.setVisibility(View.GONE);
        recyclerPokemon.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(mensaje);
    }

    @Override
    public void onPokemonClick(PokemonItem pokemon, int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POKEMON_NAME, pokemon.getName());

        int id = pokemon.getId();
        if (id > 0) {
            intent.putExtra(DetailActivity.EXTRA_POKEMON_ID, id);
        }

        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(PokemonItem pokemon, int position) {
        int id = pokemon.getId();
        String name = pokemon.getName();
        if (id > 0) {
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                boolean esFav = db.pokemonFavoritoDao().esFavorito(id);
                if (esFav) {
                    db.pokemonFavoritoDao().eliminarPorId(id);
                } else {
                    String img = pokemon.getImageUrl();
                    pe.josueyovera.pokdexexplorer.model.PokemonFavorito fav =
                            new pe.josueyovera.pokdexexplorer.model.PokemonFavorito(id, name, img, "POKÉMON");
                    db.pokemonFavoritoDao().insertar(fav);
                }

                runOnUiThread(() -> {
                    actualizarContadorFavoritos();
                    Toast.makeText(this, (esFav ? "Eliminado de " : "Guardado en ") + "favoritos: " + name, Toast.LENGTH_SHORT).show();
                });
            });
        }
    }
}
