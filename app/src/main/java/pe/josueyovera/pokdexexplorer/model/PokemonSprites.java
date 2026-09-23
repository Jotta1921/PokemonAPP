package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;

public class PokemonSprites {

    @SerializedName("front_default")
    private String frontDefault;

    public PokemonSprites() {
    }

    public PokemonSprites(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFront_default() {
        return frontDefault;
    }
}
