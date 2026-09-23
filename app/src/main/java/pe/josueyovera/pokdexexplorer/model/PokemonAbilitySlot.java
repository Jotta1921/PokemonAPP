package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;

public class PokemonAbilitySlot {

    @SerializedName("is_hidden")
    private boolean isHidden;

    @SerializedName("slot")
    private int slot;

    @SerializedName("ability")
    private NamedResource ability;

    public PokemonAbilitySlot() {
    }

    public PokemonAbilitySlot(boolean isHidden, int slot, NamedResource ability) {
        this.isHidden = isHidden;
        this.slot = slot;
        this.ability = ability;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public boolean isIs_hidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public NamedResource getAbility() {
        return ability;
    }

    public void setAbility(NamedResource ability) {
        this.ability = ability;
    }
}
