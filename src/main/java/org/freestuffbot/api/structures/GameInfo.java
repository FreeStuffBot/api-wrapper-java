package org.freestuffbot.api.structures;

import com.google.gson.annotations.SerializedName;

import java.net.URI;

/**
 * An object with all the data needed to make an announcement of a game offer.
 */
public class GameInfo {
    /**
     * Proxy url.
     */
    public URI url;

    /**
     * Direct link to the store page, please use the {@code url} field for href.
     */
    @SerializedName("org_url")
    public URI originalUrl;

    /**
     * Game's title.
     */
    public String title;

    /**
     * Original price before the discount.
     */
    @SerializedName("org_price")
    public Price originalPrice;

    /**
     * Price after the discount.
     */
    public Price price;

    /**
     * Url to the thumbnail image.
     */
    public URI thumbnail;

    /**
     * UNIX Timestamp in seconds - marks the time when the offer expires.
     */
    public int until;

    /**
     * Game's store.
     */
    public Store store;

    /**
     * Flags, like trash game, or third-party game.
     */
    public GameFlag[] flags;

    /**
     * Type of announcement.
     */
    public AnnouncementType type;

    /**
     * Meta information.
     */
    @SerializedName("store_meta")
    public StoreMeta storeMeta;

    /**
     * Represents the meta information of a game.
     */
    public static class StoreMeta {
        //Empty if store != "steam". Contains subids divided by a space otherwise.
        @SerializedName("steam_subids")
        public String steamSubIDs;
    }
}
