package org.freestuffbot.api.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.freestuffbot.api.structures.GameFlag;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A GSON adapter for serializing GameFlag[] arrays into bitfields (int), and vice versa.
 */
public class GameFlagAdapter extends TypeAdapter<GameFlag[]> {

    @Override
    public GameFlag[] read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        ArrayList<GameFlag> flags = new ArrayList<>(GameFlag.values().length);

        int bitsField = reader.nextInt();
        for (GameFlag flag : GameFlag.values()) if (flag.isSet(bitsField)) flags.add(flag);

        return flags.toArray(new GameFlag[0]);
    }

    @Override
    public void write(JsonWriter writer, GameFlag[] gameFlags) throws IOException {
        if (gameFlags == null) {
            writer.nullValue();
            return;
        }

        int bitsField = 0;
        for (GameFlag flag : gameFlags) bitsField |= flag.bitmask();
        writer.value(bitsField);
    }

}