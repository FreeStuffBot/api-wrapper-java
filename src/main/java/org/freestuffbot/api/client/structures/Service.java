package org.freestuffbot.api.client.structures;

import com.google.gson.annotations.SerializedName;

public enum Service {
    @SerializedName("discord")
    DISCORD("discord"),
    @SerializedName("telegram")
    TELEGRAM("telegram");

    private final String serializedName;

    Service(String serializedName) {
        this.serializedName = serializedName;
    }

    public String getSerializedName() {
        return serializedName;
    }

    @Override
    public String toString() {
        return serializedName;
    }
}
