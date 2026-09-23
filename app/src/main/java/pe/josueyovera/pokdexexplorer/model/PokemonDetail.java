package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetail {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("height")
    private int height;

    @SerializedName("weight")
    private int weight;

    @SerializedName("sprites")
    private PokemonSprites sprites;

    @SerializedName("types")
    private List<PokemonTypeSlot> types;

    @SerializedName("abilities")
    private List<PokemonAbilitySlot> abilities;

    @SerializedName("stats")
    private List<PokemonStatSlot> stats;

    public PokemonDetail() {
    }

    public PokemonDetail(int id, String name, int height, int weight, PokemonSprites sprites, List<PokemonTypeSlot> types, List<PokemonAbilitySlot> abilities, List<PokemonStatSlot> stats) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.sprites = sprites;
        this.types = types;
        this.abilities = abilities;
        this.stats = stats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public PokemonSprites getSprites() {
        return sprites;
    }

    public void setSprites(PokemonSprites sprites) {
        this.sprites = sprites;
    }

    public List<PokemonTypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonTypeSlot> types) {
        this.types = types;
    }

    public List<PokemonAbilitySlot> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<PokemonAbilitySlot> abilities) {
        this.abilities = abilities;
    }

    public List<PokemonStatSlot> getStats() {
        return stats;
    }

    public void setStats(List<PokemonStatSlot> stats) {
        this.stats = stats;
    }
}
