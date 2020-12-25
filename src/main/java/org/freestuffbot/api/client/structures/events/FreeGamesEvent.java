package org.freestuffbot.api.client.structures.events;

import com.google.gson.annotations.SerializedName;

public class FreeGamesEvent extends Event {
    /**
     * A list of newly published games ids.
     */
    @SerializedName("data")
    public int[] gameIds;
}
