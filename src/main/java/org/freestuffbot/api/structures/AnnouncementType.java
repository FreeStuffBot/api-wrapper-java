package org.freestuffbot.api.structures;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the type of the announcement.
 */
public enum AnnouncementType {
    @SerializedName("free")
    FREE,
    @SerializedName("weekend")
    WEEKEND,
    @SerializedName("discount")
    DISCOUNT,
    @SerializedName("ad")
    AD,
    @SerializedName("unknown")
    UNKNOWN
}
