package pe.josueyovera.pokdexexplorer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PokemonDao {

    @Insert
    long insert(Pokemon pokemon);

    @Update
    void update(Pokemon pokemon);

    @Delete
    void delete(Pokemon pokemon);

    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    List<Pokemon> getAllPokemon();

    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    Pokemon getPokemonById(int id);

    @Query("SELECT * FROM pokemon WHERE isFavorite = 1")
    List<Pokemon> getFavoritePokemon();

    @Query("DELETE FROM pokemon")
    void deleteAll();
}
