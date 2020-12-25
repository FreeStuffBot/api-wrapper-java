package org.freestuffbot.api.client.adapters;

import com.google.gson.*;
import org.freestuffbot.api.client.structures.events.EventType;
import org.freestuffbot.api.client.structures.events.OperationEvent;

import java.lang.reflect.Type;

public class OperationEventAdapter implements JsonDeserializer<OperationEvent> {

    @Override
    public OperationEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;

        JsonObject jsonEvent = json.getAsJsonObject();

        OperationEvent operationEvent = new OperationEvent();
        operationEvent.type = EventType.OPERATION;
        operationEvent.secret = jsonEvent.get("secret").getAsString();
        operationEvent.command = jsonEvent.getAsJsonObject("data").get("command").getAsString();

        JsonArray jsonArguments = jsonEvent.getAsJsonObject("data").getAsJsonArray("arguments");
        operationEvent.arguments = new String[jsonArguments.size()];
        for (int i = 0; i < operationEvent.arguments.length; i++) operationEvent.arguments[i] = jsonArguments.get(i).getAsString();

        return operationEvent;
    }
}
