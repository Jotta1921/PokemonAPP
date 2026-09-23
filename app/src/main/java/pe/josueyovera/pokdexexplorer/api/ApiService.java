package pe.josueyovera.pokdexexplorer.api;

import pe.josueyovera.pokdexexplorer.model.PokemonDetail;
import pe.josueyovera.pokdexexplorer.model.PokemonResponse;
import pe.josueyovera.pokdexexplorer.model.PokemonSpecies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("pokemon")
    Call<PokemonResponse> obtenerPokemon(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("pokemon/{id}")
    Call<PokemonDetail> obtenerDetalle(
            @Path("id") int id
    );

    @GET("pokemon/{name}")
    Call<PokemonDetail> buscarPorNombre(
            @Path("name") String name
    );

    @GET("pokemon-species/{id}")
    Call<PokemonSpecies> obtenerEspecie(
            @Path("id") int id
    );
}
