package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;

public class PokemonStatSlot {

    @SerializedName("base_stat")
    private int baseStat;

    @SerializedName("effort")
    private int effort;

    @SerializedName("stat")
    private NamedResource stat;

    public PokemonStatSlot() {
    }

    public PokemonStatSlot(int baseStat, int effort, NamedResource stat) {
        this.baseStat = baseStat;
        this.effort = effort;
        this.stat = stat;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public NamedResource getStat() {
        return stat;
    }

    public void setStat(NamedResource stat) {
        this.stat = stat;
    }
}
