package org.freestuffbot.api.client.structures.events;

import com.google.gson.annotations.JsonAdapter;
import org.freestuffbot.api.client.adapters.OperationEventAdapter;

@JsonAdapter(OperationEventAdapter.class)
public class OperationEvent extends Event {

    /**
     * The command to execute.
     */
    public String command;

    /**
     * The command's arguments.
     */
    public String[] arguments;
}
