package org.freestuffbot.api.client.adapters;

import com.google.gson.*;
import org.freestuffbot.api.client.structures.events.Event;
import org.freestuffbot.api.client.structures.events.EventType;
import org.freestuffbot.api.client.structures.events.FreeGamesEvent;
import org.freestuffbot.api.client.structures.events.OperationEvent;

import java.lang.reflect.Type;

import static org.freestuffbot.api.client.structures.events.EventType.FREE_GAMES;
import static org.freestuffbot.api.client.structures.events.EventType.OPERATION;

public class EventAdapter implements JsonDeserializer<Event> {
    private static final Gson gson = new Gson();

    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject jsonEvent = json.getAsJsonObject();
        EventType eventType = gson.fromJson(jsonEvent.get("event"), EventType.class);

        if (eventType == FREE_GAMES) {
            return gson.fromJson(jsonEvent, FreeGamesEvent.class);
        } else if (eventType == OPERATION) {
            return gson.fromJson(jsonEvent, OperationEvent.class);
        } else {
            Event event = new Event();

            event.type = eventType;
            event.secret = jsonEvent.get("secret").getAsString();

            return event;
        }
    }
}
