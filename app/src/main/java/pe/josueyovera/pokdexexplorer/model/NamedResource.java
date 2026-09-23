package pe.josueyovera.pokdexexplorer.model;

import com.google.gson.annotations.SerializedName;

public class NamedResource {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public NamedResource() {
    }

    public NamedResource(String name, String url) {
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
}
