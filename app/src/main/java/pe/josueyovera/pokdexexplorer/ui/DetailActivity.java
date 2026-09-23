package pe.josueyovera.pokdexexplorer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import pe.josueyovera.pokdexexplorer.R;
import pe.josueyovera.pokdexexplorer.api.RetrofitClient;
import pe.josueyovera.pokdexexplorer.database.AppDatabase;
import pe.josueyovera.pokdexexplorer.model.PokemonAbilitySlot;
import pe.josueyovera.pokdexexplorer.model.PokemonDetail;
import pe.josueyovera.pokdexexplorer.model.PokemonFavorito;
import pe.josueyovera.pokdexexplorer.model.PokemonSpecies;
import pe.josueyovera.pokdexexplorer.model.PokemonStatSlot;
import pe.josueyovera.pokdexexplorer.model.PokemonTypeSlot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "POKEMON_DETAIL";

    public static final String EXTRA_POKEMON_ID = "POKEMON_ID";
    public static final String EXTRA_POKEMON_NAME = "POKEMON_NAME";

    private ScrollView scrollDetail;
    private ProgressBar detailProgressBar;
    private View detailErrorContainer;
    private TextView tvDetailErrorMessage;

    private ImageView ivDetailPokemon;
    private TextView tvDetailBadgeRegion;
    private TextView tvDetailId;
    private TextView tvDetailName;
    private TextView tvDetailType1;
    private TextView tvDetailType2;
    private TextView tvDetailHeight;
    private TextView tvDetailHeightInches;
    private TextView tvDetailWeight;
    private TextView tvDetailWeightLbs;
    private TextView tvDetailAbilitiesCount;
    private TextView tvDetailAbility1Title;
    private TextView tvDetailAbility1Desc;
    private TextView tvDetailAbility2Title;
    private TextView tvDetailAbility2Desc;
    private View layoutAbility2;
    private ImageButton btnDetailFavorite;

    // Stats
    private TextView tvDetailTotalStats;
    private TextView tvStatHp, tvStatAttack, tvStatDefense, tvStatSpAtk, tvStatSpDef, tvStatSpeed;
    private ProgressBar progressStatHp, progressStatAttack, progressStatDefense, progressStatSpAtk, progressStatSpDef, progressStatSpeed;

    // Flavor Text & Evolutions
    private TextView tvDetailFlavorText;
    private ImageView ivEvo1, ivEvo2, ivEvo3;
    private TextView tvEvo1NameId, tvEvo2NameId, tvEvo3NameId;
    private TextView tvEvo1Condition, tvEvo2Condition;

    private int pokemonId = -1;
    private String pokemonName = null;
    private String pokemonImageUrl = null;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        View mainView = findViewById(R.id.detailMainLayout);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initViews();
        obtenerDatosIntent();
        cargarDetalle();
    }

    private void initViews() {
        scrollDetail = findViewById(R.id.scrollDetail);
        detailProgressBar = findViewById(R.id.detailProgressBar);
        detailErrorContainer = findViewById(R.id.detailErrorContainer);
        tvDetailErrorMessage = findViewById(R.id.tvDetailErrorMessage);
        Button btnDetailRetry = findViewById(R.id.btnDetailRetry);

        ImageButton btnDetailBack = findViewById(R.id.btnDetailBack);
        ivDetailPokemon = findViewById(R.id.ivDetailPokemon);
        tvDetailBadgeRegion = findViewById(R.id.tvDetailBadgeRegion);
        tvDetailId = findViewById(R.id.tvDetailId);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailType1 = findViewById(R.id.tvDetailType1);
        tvDetailType2 = findViewById(R.id.tvDetailType2);
        tvDetailHeight = findViewById(R.id.tvDetailHeight);
        tvDetailHeightInches = findViewById(R.id.tvDetailHeightInches);
        tvDetailWeight = findViewById(R.id.tvDetailWeight);
        tvDetailWeightLbs = findViewById(R.id.tvDetailWeightLbs);
        tvDetailAbilitiesCount = findViewById(R.id.tvDetailAbilitiesCount);
        tvDetailAbility1Title = findViewById(R.id.tvDetailAbility1Title);
        tvDetailAbility1Desc = findViewById(R.id.tvDetailAbility1Desc);
        tvDetailAbility2Title = findViewById(R.id.tvDetailAbility2Title);
        tvDetailAbility2Desc = findViewById(R.id.tvDetailAbility2Desc);
        layoutAbility2 = findViewById(R.id.layoutAbility2);
        btnDetailFavorite = findViewById(R.id.btnDetailFavorite);

        // Stats
        tvDetailTotalStats = findViewById(R.id.tvDetailTotalStats);
        tvStatHp = findViewById(R.id.tvStatHp);
        tvStatAttack = findViewById(R.id.tvStatAttack);
        tvStatDefense = findViewById(R.id.tvStatDefense);
        tvStatSpAtk = findViewById(R.id.tvStatSpAtk);
        tvStatSpDef = findViewById(R.id.tvStatSpDef);
        tvStatSpeed = findViewById(R.id.tvStatSpeed);

        progressStatHp = findViewById(R.id.progressStatHp);
        progressStatAttack = findViewById(R.id.progressStatAttack);
        progressStatDefense = findViewById(R.id.progressStatDefense);
        progressStatSpAtk = findViewById(R.id.progressStatSpAtk);
        progressStatSpDef = findViewById(R.id.progressStatSpDef);
        progressStatSpeed = findViewById(R.id.progressStatSpeed);

        // Flavor Text & Evo
        tvDetailFlavorText = findViewById(R.id.tvDetailFlavorText);
        ivEvo1 = findViewById(R.id.ivEvo1);
        ivEvo2 = findViewById(R.id.ivEvo2);
        ivEvo3 = findViewById(R.id.ivEvo3);
        tvEvo1NameId = findViewById(R.id.tvEvo1NameId);
        tvEvo2NameId = findViewById(R.id.tvEvo2NameId);
        tvEvo3NameId = findViewById(R.id.tvEvo3NameId);
        tvEvo1Condition = findViewById(R.id.tvEvo1Condition);
        tvEvo2Condition = findViewById(R.id.tvEvo2Condition);

        if (btnDetailBack != null) {
            btnDetailBack.setOnClickListener(v -> finish());
        }

        if (btnDetailRetry != null) {
            btnDetailRetry.setOnClickListener(v -> cargarDetalle());
        }

        btnDetailFavorite.setOnClickListener(v -> alternarFavorito());
    }

    private void obtenerDatosIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            pokemonId = intent.getIntExtra(EXTRA_POKEMON_ID, -1);
            pokemonName = intent.getStringExtra(EXTRA_POKEMON_NAME);
        }
    }

    private void cargarDetalle() {
        mostrarEstadoCarga();

        Call<PokemonDetail> call;
        if (pokemonId > 0) {
            call = RetrofitClient.getApiService().obtenerDetalle(pokemonId);
        } else if (pokemonName != null && !pokemonName.trim().isEmpty()) {
            call = RetrofitClient.getApiService().buscarPorNombre(pokemonName.toLowerCase().trim());
        } else {
            mostrarEstadoError("No se especificó ningún Pokémon válido.");
            return;
        }

        call.enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDetail> call, @NonNull Response<PokemonDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarEstadoExito();
                    mostrarDetalle(response.body());
                } else {
                    Log.e(TAG, "Error HTTP al obtener detalle: " + response.code());
                    mostrarEstadoError("No encontramos información sobre este Pokémon.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonDetail> call, @NonNull Throwable t) {
                Log.e(TAG, "Fallo de red al obtener detalle", t);
                mostrarEstadoError("No se pudo conectar al servidor.\nPor favor, verifica tu conexión a internet.");
            }
        });
    }

    private void mostrarDetalle(PokemonDetail detail) {
        int id = detail.getId();
        this.pokemonId = id;

        // Kanto Region Badge
        if (tvDetailBadgeRegion != null) {
            tvDetailBadgeRegion.setText(String.format(Locale.getDefault(), "🎯 KANTO #%03d", id));
        }

        String rawName = detail.getName();
        if (rawName != null && !rawName.isEmpty()) {
            this.pokemonName = rawName.substring(0, 1).toUpperCase() + rawName.substring(1);
            tvDetailName.setText(this.pokemonName);
        } else {
            this.pokemonName = "Pokémon";
            tvDetailName.setText("");
        }

        // Subtítulo ID
        tvDetailId.setText(String.format(Locale.getDefault(), "#%03d • POKÉMON", id));

        // Imagen oficial del Pokémon
        pokemonImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";
        if (detail.getSprites() != null && detail.getSprites().getFrontDefault() != null) {
            pokemonImageUrl = detail.getSprites().getFrontDefault();
        }

        Glide.with(this)
                .load(pokemonImageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivDetailPokemon);

        // Tipos Reales
        List<PokemonTypeSlot> types = detail.getTypes();
        if (types != null && !types.isEmpty()) {
            tvDetailType1.setText("⚡ " + types.get(0).getType().getName().toUpperCase());
            tvDetailType1.setVisibility(View.VISIBLE);

            if (types.size() > 1 && types.get(1).getType() != null) {
                tvDetailType2.setText(types.get(1).getType().getName().toUpperCase());
                tvDetailType2.setVisibility(View.VISIBLE);
            } else {
                tvDetailType2.setText("GEN I");
                tvDetailType2.setVisibility(View.VISIBLE);
            }
        } else {
            tvDetailType1.setText("⚡ NORMAL");
            tvDetailType2.setText("GEN I");
        }

        // Altura y Peso Reales de la API
        double heightInMeters = detail.getHeight() / 10.0;
        int inchesTotal = (int) Math.round(heightInMeters * 39.3701);
        int feet = inchesTotal / 12;
        int inches = inchesTotal % 12;

        tvDetailHeight.setText(String.format(Locale.getDefault(), "%.1f m", heightInMeters));
        tvDetailHeightInches.setText(String.format(Locale.getDefault(), "%d' %02d\"", feet, inches));

        double weightInKg = detail.getWeight() / 10.0;
        double weightInLbs = weightInKg * 2.20462;
        tvDetailWeight.setText(String.format(Locale.getDefault(), "%.1f kg", weightInKg));
        tvDetailWeightLbs.setText(String.format(Locale.getDefault(), "%.1f lbs", weightInLbs));

        // Habilidades Reales
        List<PokemonAbilitySlot> abilities = detail.getAbilities();
        if (abilities != null && !abilities.isEmpty()) {
            tvDetailAbilitiesCount.setText(abilities.size() + " descubiertas");

            PokemonAbilitySlot slot1 = abilities.get(0);
            if (slot1.getAbility() != null) {
                String name1 = slot1.getAbility().getName();
                tvDetailAbility1Title.setText(name1.substring(0, 1).toUpperCase() + name1.substring(1));
                tvDetailAbility1Desc.setText("Habilidad característica de " + pokemonName + " que mejora su desempeño en combate.");
            }

            if (abilities.size() > 1) {
                PokemonAbilitySlot slot2 = abilities.get(1);
                if (slot2.getAbility() != null) {
                    String name2 = slot2.getAbility().getName();
                    tvDetailAbility2Title.setText(name2.substring(0, 1).toUpperCase() + name2.substring(1));
                    tvDetailAbility2Desc.setText("Habilidad secundaria u oculta con efectos especiales en batalla.");
                    layoutAbility2.setVisibility(View.VISIBLE);
                }
            } else {
                layoutAbility2.setVisibility(View.GONE);
            }
        } else {
            tvDetailAbilitiesCount.setText("0 descubiertas");
        }

        // Estadísticas Base Reales de PokéAPI
        List<PokemonStatSlot> stats = detail.getStats();
        int hp = 45, atk = 49, def = 49, spAtk = 65, spDef = 65, speed = 45;

        if (stats != null && !stats.isEmpty()) {
            for (PokemonStatSlot slot : stats) {
                if (slot.getStat() != null) {
                    String statName = slot.getStat().getName();
                    if ("hp".equalsIgnoreCase(statName)) hp = slot.getBaseStat();
                    else if ("attack".equalsIgnoreCase(statName)) atk = slot.getBaseStat();
                    else if ("defense".equalsIgnoreCase(statName)) def = slot.getBaseStat();
                    else if ("special-attack".equalsIgnoreCase(statName)) spAtk = slot.getBaseStat();
                    else if ("special-defense".equalsIgnoreCase(statName)) spDef = slot.getBaseStat();
                    else if ("speed".equalsIgnoreCase(statName)) speed = slot.getBaseStat();
                }
            }
        }

        int total = hp + atk + def + spAtk + spDef + speed;
        tvDetailTotalStats.setText("Total: " + total);

        tvStatHp.setText(String.valueOf(hp));
        progressStatHp.setProgress(hp);

        tvStatAttack.setText(String.valueOf(atk));
        progressStatAttack.setProgress(atk);

        tvStatDefense.setText(String.valueOf(def));
        progressStatDefense.setProgress(def);

        tvStatSpAtk.setText(String.valueOf(spAtk));
        progressStatSpAtk.setProgress(spAtk);

        tvStatSpDef.setText(String.valueOf(spDef));
        progressStatSpDef.setProgress(spDef);

        tvStatSpeed.setText(String.valueOf(speed));
        progressStatSpeed.setProgress(speed);

        // Cargar Especie y Registro de Campo Real en Español desde PokéAPI
        cargarEspecieYRegistro(id);

        // Cargar Línea Evolutiva Dinámica
        configurarLineaEvolutiva(id);

        comprobarSiEsFavorito();
    }

    private void cargarEspecieYRegistro(int id) {
        RetrofitClient.getApiService().obtenerEspecie(id).enqueue(new Callback<PokemonSpecies>() {
            @Override
            public void onResponse(@NonNull Call<PokemonSpecies> call, @NonNull Response<PokemonSpecies> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonSpecies species = response.body();

                    String genus = species.getSpanishGenus();
                    if (genus != null && !genus.isEmpty()) {
                        tvDetailId.setText(String.format(Locale.getDefault(), "#%03d • ESPECIE %s", id, genus));
                    }

                    String flavor = species.getSpanishFlavorText();
                    if (flavor != null && !flavor.isEmpty()) {
                        tvDetailFlavorText.setText(flavor);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonSpecies> call, @NonNull Throwable t) {
                Log.e(TAG, "No se pudo cargar la especie en español", t);
            }
        });
    }

    private void configurarLineaEvolutiva(int currentId) {
        int evo1Id = 1, evo2Id = 2, evo3Id = 3;
        String evo1Name = "Bulbasaur", evo2Name = "Ivysaur", evo3Name = "Venusaur";
        String cond1 = "Nivel 16", cond2 = "Nivel 32";

        if (currentId == 1 || currentId == 2 || currentId == 3) {
            evo1Id = 1; evo1Name = "Bulbasaur";
            evo2Id = 2; evo2Name = "Ivysaur";
            evo3Id = 3; evo3Name = "Venusaur";
            cond1 = "Nivel 16"; cond2 = "Nivel 32";
        } else if (currentId == 4 || currentId == 5 || currentId == 6) {
            evo1Id = 4; evo1Name = "Charmander";
            evo2Id = 5; evo2Name = "Charmeleon";
            evo3Id = 6; evo3Name = "Charizard";
            cond1 = "Nivel 16"; cond2 = "Nivel 36";
        } else if (currentId == 7 || currentId == 8 || currentId == 9) {
            evo1Id = 7; evo1Name = "Squirtle";
            evo2Id = 8; evo2Name = "Wartortle";
            evo3Id = 9; evo3Name = "Blastoise";
            cond1 = "Nivel 16"; cond2 = "Nivel 36";
        } else if (currentId == 25 || currentId == 26 || currentId == 172) {
            evo1Id = 172; evo1Name = "Pichu";
            evo2Id = 25; evo2Name = "Pikachu";
            evo3Id = 26; evo3Name = "Raichu";
            cond1 = "Amistad"; cond2 = "Piedra Trueno";
        } else if (currentId == 92 || currentId == 93 || currentId == 94) {
            evo1Id = 92; evo1Name = "Gastly";
            evo2Id = 93; evo2Name = "Haunter";
            evo3Id = 94; evo3Name = "Gengar";
            cond1 = "Nivel 25"; cond2 = "Intercambio";
        } else {
            // Generico para cualquier otro id
            evo1Id = Math.max(1, currentId - 1);
            evo1Name = "Etapa 1";
            evo2Id = currentId;
            evo2Name = pokemonName != null ? pokemonName : "Actual";
            evo3Id = currentId + 1;
            evo3Name = "Etapa 3";
            cond1 = "Evolución";
            cond2 = "Evolución";
        }

        String url1 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + evo1Id + ".png";
        String url2 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + evo2Id + ".png";
        String url3 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + evo3Id + ".png";

        if (ivEvo1 != null) Glide.with(this).load(url1).into(ivEvo1);
        if (ivEvo2 != null) Glide.with(this).load(url2).into(ivEvo2);
        if (ivEvo3 != null) Glide.with(this).load(url3).into(ivEvo3);

        if (tvEvo1NameId != null) tvEvo1NameId.setText(String.format(Locale.getDefault(), "%s\n#%03d", evo1Name, evo1Id));
        if (tvEvo2NameId != null) tvEvo2NameId.setText(String.format(Locale.getDefault(), "%s\n#%03d", evo2Name, evo2Id));
        if (tvEvo3NameId != null) tvEvo3NameId.setText(String.format(Locale.getDefault(), "%s\n#%03d", evo3Name, evo3Id));

        if (tvEvo1Condition != null) tvEvo1Condition.setText(cond1);
        if (tvEvo2Condition != null) tvEvo2Condition.setText(cond2);
    }

    private void comprobarSiEsFavorito() {
        Executors.newSingleThreadExecutor().execute(() -> {
            boolean esFav = AppDatabase.getInstance(getApplicationContext())
                    .pokemonFavoritoDao()
                    .esFavorito(pokemonId);

            runOnUiThread(() -> {
                this.isFavorite = esFav;
                actualizarIconoFavorito();
            });
        });
    }

    private void alternarFavorito() {
        isFavorite = !isFavorite;
        actualizarIconoFavorito();

        final boolean nuevoEstado = isFavorite;
        final int id = pokemonId;
        final String nombre = pokemonName;
        final String imagen = pokemonImageUrl;

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            if (nuevoEstado) {
                PokemonFavorito fav = new PokemonFavorito(id, nombre, imagen, "POKÉMON");
                db.pokemonFavoritoDao().insertar(fav);
            } else {
                db.pokemonFavoritoDao().eliminarPorId(id);
            }

            runOnUiThread(() -> {
                String msj = nuevoEstado ? "Añadido a favoritos" : "Eliminado de favoritos";
                Toast.makeText(DetailActivity.this, msj, Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void actualizarIconoFavorito() {
        if (isFavorite) {
            btnDetailFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            btnDetailFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void mostrarEstadoCarga() {
        detailProgressBar.setVisibility(View.VISIBLE);
        scrollDetail.setVisibility(View.GONE);
        detailErrorContainer.setVisibility(View.GONE);
    }

    private void mostrarEstadoExito() {
        detailProgressBar.setVisibility(View.GONE);
        scrollDetail.setVisibility(View.VISIBLE);
        detailErrorContainer.setVisibility(View.GONE);
    }

    private void mostrarEstadoError(String mensaje) {
        detailProgressBar.setVisibility(View.GONE);
        scrollDetail.setVisibility(View.GONE);
        detailErrorContainer.setVisibility(View.VISIBLE);
        tvDetailErrorMessage.setText(mensaje);
    }
}
