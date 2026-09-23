package pe.josueyovera.pokdexexplorer.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon_favoritos")
public class PokemonFavorito {

    @PrimaryKey
    private int id;
    private String name;
    private String imageUrl;
    private String types;

    public PokemonFavorito() {
    }

    public PokemonFavorito(int id, String name, String imageUrl, String types) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.types = types;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
