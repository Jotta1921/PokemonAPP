package pe.josueyovera.pokdexexplorer.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pe.josueyovera.pokdexexplorer.model.PokemonFavorito;

@Dao
public interface PokemonFavoritoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertar(PokemonFavorito pokemon);

    @Delete
    void eliminar(PokemonFavorito pokemon);

    @Query("DELETE FROM pokemon_favoritos WHERE id = :id")
    void eliminarPorId(int id);

    @Query("SELECT * FROM pokemon_favoritos ORDER BY id ASC")
    List<PokemonFavorito> obtenerTodos();

    @Query("SELECT EXISTS(SELECT 1 FROM pokemon_favoritos WHERE id = :id LIMIT 1)")
    boolean esFavorito(int id);

    @Query("SELECT * FROM pokemon_favoritos WHERE id = :id LIMIT 1")
    PokemonFavorito obtenerPorId(int id);
}
