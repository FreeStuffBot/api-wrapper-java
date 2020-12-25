package org.freestuffbot.api.client.structures;

import com.google.gson.annotations.SerializedName;
import org.freestuffbot.api.structures.ServiceStatusType;

public class ServiceStatus<T> {
    /**
     * The current version of the service.
     */
    public String version;

    /**
     * Any additional data about the service.
     */
    public T data;

    /**
     * Has to be unique for each service of the same kind.
     * A discord and a telegram service may share the same suid.
     * This can be as simple as numerically counting each process spawned for the service.
     */
    @SerializedName("suid")
    public String serviceId;

    /**
     * The current status of the service.
     */
    public ServiceStatusType status;

    /**
     * Host server name.
     */
    public String server;

    /**
     * The service type: Discord or Telegram.
     */
    public Service service;
}
