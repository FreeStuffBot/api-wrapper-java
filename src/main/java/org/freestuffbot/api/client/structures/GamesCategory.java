package org.freestuffbot.api.client.structures;

public enum GamesCategory {
    /**
     * All games in the database.
     */
    ALL("all"),
    /**
     * All games in the database that have been manually approved by our content moderators.
     */
    APPROVED("approved"),
    /**
     * All games currently free.
     */
    FREE("free");

    /* Enum code */

    private final String serializedName;

    GamesCategory(String serializedName) {
        this.serializedName = serializedName;
    }

    /**
     * Returns the serialized name of the category.
     * @return The serialized name of the category.
     */
    public String getSerializedName() {
        return serializedName;
    }

    @Override
    public String toString() {
        return serializedName;
    }
}
