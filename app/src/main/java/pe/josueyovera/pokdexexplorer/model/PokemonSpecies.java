package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonSpecies {

    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;

    @SerializedName("genera")
    private List<GenusEntry> genera;

    public static class FlavorTextEntry {
        @SerializedName("flavor_text")
        private String flavorText;

        @SerializedName("language")
        private NamedResource language;

        public String getFlavorText() {
            return flavorText;
        }

        public NamedResource getLanguage() {
            return language;
        }
    }

    public static class GenusEntry {
        @SerializedName("genus")
        private String genus;

        @SerializedName("language")
        private NamedResource language;

        public String getGenus() {
            return genus;
        }

        public NamedResource getLanguage() {
            return language;
        }
    }

    public String getSpanishFlavorText() {
        if (flavorTextEntries != null) {
            for (FlavorTextEntry entry : flavorTextEntries) {
                if (entry.getLanguage() != null && "es".equalsIgnoreCase(entry.getLanguage().getName())) {
                    return entry.getFlavorText().replace("\n", " ").replace("\f", " ");
                }
            }
            for (FlavorTextEntry entry : flavorTextEntries) {
                if (entry.getLanguage() != null && "en".equalsIgnoreCase(entry.getLanguage().getName())) {
                    return entry.getFlavorText().replace("\n", " ").replace("\f", " ");
                }
            }
        }
        return null;
    }

    public String getSpanishGenus() {
        if (genera != null) {
            for (GenusEntry entry : genera) {
                if (entry.getLanguage() != null && "es".equalsIgnoreCase(entry.getLanguage().getName())) {
                    String g = entry.getGenus();
                    if (g != null) {
                        return g.replace("Pokémon ", "").toUpperCase();
                    }
                }
            }
        }
        return null;
    }
}
