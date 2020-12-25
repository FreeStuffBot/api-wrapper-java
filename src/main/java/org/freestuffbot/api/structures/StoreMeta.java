package org.freestuffbot.api.structures;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the meta information of a game.
 */
public class StoreMeta {
    //Empty if store != "steam". Contains subids divided by a space otherwise.
    @SerializedName("steam_subids")
    public String steamSubIDs;
}
