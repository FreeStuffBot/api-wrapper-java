package org.freestuffbot.api.client.structures.events;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.freestuffbot.api.client.adapters.EventAdapter;

@JsonAdapter(EventAdapter.class)
public class Event {
    /**
     * The type of the event.
     */
    @SerializedName("event")
    public EventType type;

    /**
     * The webhook secret.
     */
    public String secret;
}
