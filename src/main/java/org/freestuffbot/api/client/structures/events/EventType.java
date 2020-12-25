package org.freestuffbot.api.client.structures.events;

import com.google.gson.annotations.SerializedName;

public enum EventType {
    @SerializedName("free_games")
    FREE_GAMES,

    @SerializedName("operation")
    OPERATION
}
