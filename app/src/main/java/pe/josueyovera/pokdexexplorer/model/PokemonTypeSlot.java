package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;

public class PokemonTypeSlot {

    @SerializedName("slot")
    private int slot;

    @SerializedName("type")
    private NamedResource type;

    public PokemonTypeSlot() {
    }

    public PokemonTypeSlot(int slot, NamedResource type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public NamedResource getType() {
        return type;
    }

    public void setType(NamedResource type) {
        this.type = type;
    }
}
