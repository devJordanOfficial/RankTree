package org.infamousmc.ranktree.Data;

public enum Texture {
    ROME("39571"),
    GREECE("48920"),
    JAPAN("604"),
    INFO("4317"),
    ERROR("23224");

    private final String texture;

    private Texture(String texture) {
        this.texture = texture;
    }

    public String getID() {
        return texture;
    }
}
