package org.freestuffbot.api.structures;

import com.google.gson.annotations.SerializedName;

public enum ServiceStatusType {
    @SerializedName("ok")
    @SuppressWarnings("unused")
    OK,
    @SerializedName("partial")
    @SuppressWarnings("unused")
    PARTIAL,
    @SerializedName("timeout")
    @SuppressWarnings("unused")
    TIMEOUT,
    @SerializedName("offline")
    @SuppressWarnings("unused")
    OFFLINE,
    @SerializedName("rebooting")
    @SuppressWarnings("unused")
    REBOOTING,
    @SerializedName("fatal")
    @SuppressWarnings("unused")
    FATAL
}
