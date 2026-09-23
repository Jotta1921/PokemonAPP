package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;

public class PokemonItem {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public PokemonItem() {
    }

    public PokemonItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        if (url == null || url.isEmpty()) return 0;
        String clean = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        String[] parts = clean.split("/");
        try {
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getImageUrl() {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + getId() + ".png";
    }
}
