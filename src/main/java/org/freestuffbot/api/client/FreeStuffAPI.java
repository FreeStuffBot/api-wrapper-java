package org.freestuffbot.api.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.freestuffbot.api.adapters.GameFlagAdapter;
import org.freestuffbot.api.client.exceptions.FreeStuffException;
import org.freestuffbot.api.client.structures.GamesCategory;
import org.freestuffbot.api.client.structures.Service;
import org.freestuffbot.api.client.structures.ServiceStatus;
import org.freestuffbot.api.client.structures.events.Event;
import org.freestuffbot.api.structures.GameFlag;
import org.freestuffbot.api.structures.GameInfo;

public abstract class FreeStuffAPI {

    /**
     * The baseURL of the API.
     */
    protected static final String baseUrl = "https://management.freestuffbot.xyz/api/v1/";

    /**
     * The GSON instance used for serializing and deserializing JSON.
     */
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(GameFlag[].class, new GameFlagAdapter())
            .create();

    /**
     * Whether this is a partner API instance ({@code true}) or a public API instance ({@code false}).
     */
    protected final boolean partnerAPI;

    /**
     * The value of the {@code Authorization} http header used when sending API requests.
     */
    protected final String authorizationToken;

    /**
     * Creates a new API wrapper instance for a public API key.
     *
     * @param apiKey The public API key.
     */
    public FreeStuffAPI(String apiKey) {
        partnerAPI = false;
        authorizationToken = "Basic " + apiKey;
    }

    /**
     * Creates a new API wrapper instance for a partner API key.
     *
     * @param apiKey    The partner API key.
     * @param serviceId The service id of this instance.
     */
    public FreeStuffAPI(String apiKey, String serviceId) {
        partnerAPI = true;
        authorizationToken = "Partner " + apiKey + " " + serviceId;
    }

    /**
     * Pings the server, and returns the milliseconds taken until the response was received.
     *
     * <b>Rate limit:</b> none.
     *
     * @return The milliseconds taken for the response to be received.
     */
    public long ping() throws FreeStuffException, InterruptedException {
        long startType = System.currentTimeMillis();
        request("ping", null, null);
        return System.currentTimeMillis() - startType;
    }

    /**
     * Returns an integer array with the ids of all games in the requested category.
     *
     * <b>Rate limit:</b> 5x in 15min.
     *
     * @param category The category to request.
     * @return The games ids.
     */
    public int[] getGamesList(GamesCategory category) throws FreeStuffException, InterruptedException {
        if (category == null) throw new NullPointerException("category is null.");
        return request("games/", category.getSerializedName(), null, int[].class);
    }

    /**
     * Gets information about a game by looking up it's id.
     * The games are batched 5 by 5.
     *
     * <b>Rate limit:</b> 10x in 5min.
     *
     * @param gameIds The games ids to request.
     * @return An array of game's info matching the gameIds.
     */
    public GameInfo[] getGamesInfo(int[] gameIds) throws FreeStuffException, InterruptedException {
        if (gameIds == null) throw new NullPointerException("gameIds is null.");
        GameInfo[] gameInfo = new GameInfo[gameIds.length];

        for (int i = 0; i < gameIds.length; i++) {
            StringBuilder gameIdsMerged = new StringBuilder();
            for (int j = i; j < Math.min(i + 5, gameIds.length); j++)
                gameIdsMerged.append(gameIds[j]);
            JsonObject response = request("game/", gameIdsMerged.toString() + "/info", null).getAsJsonObject();
            for (int j = i; j < Math.min(i + 5, gameIds.length); j++)
                gameInfo[j] = gson.fromJson(response.get(String.valueOf(gameIds[j])), GameInfo.class);
        }

        return gameInfo;
    }

    //TODO: add getGamesAnalytics (partner endpoint).
    //TODO: add getGamesAllDetails (partner endpoint).

    /**
     * Heartbeat update endpoint. Query every 20-30 seconds.
     *
     * <b>Rate limit:</b> 4x in 1min.
     *
     * @param serviceStatus The current status of the service.
     * @param <T>           The type of the additional data about the service.
     * @return The events from received from the server.
     */
    public <T> Event[] updateServiceStatus(ServiceStatus<T> serviceStatus) throws FreeStuffException, InterruptedException {
        return request("status", null, gson.toJson(serviceStatus), Event[].class);
    }

    /**
     * Reports the announcement analytics to the server after finishing the announcement.
     *
     * @param service       The service (Discord/Telegram).
     * @param serviceId     The service unique id.
     * @param analyticsData The analytics data.
     * @param <T>           The type of the analytics data.
     */
    public <T> void submitGameAnnouncementAnalytics(Service service, String serviceId, int gameId, T analyticsData) throws FreeStuffException, InterruptedException {
        ServiceAnalytics<T> analytics = new ServiceAnalytics<>();

        analytics.service = service;
        analytics.serviceId = serviceId;
        analytics.data = analyticsData;

        request("game/", gameId + "/analytics", gson.toJson(analytics));
    }

    /**
     * Sends an API request and returns the data element.
     *
     * @param endpoint    The endpoint to use, appended to the baseURL.
     * @param suffix      A suffix for the endpoint, for passing path parameters without switching the rate limit bucket.
     * @param requestBody Serialized JSON data for the request body, can be null for no body.
     * @return The response data element.
     * @throws FreeStuffException on failure.
     */
    protected abstract JsonElement request(String endpoint, String suffix, String requestBody) throws FreeStuffException, InterruptedException;

    /**
     * Sends an API request and decodes the response data.
     *
     * @param endpoint    The endpoint to use, appended to the baseURL.
     * @param suffix      A suffix for the endpoint, for passing path parameters without switching the rate limit bucket.
     * @param requestBody Serialized JSON data for the request body, can be null for no body.
     * @param type        The type of the response body.
     * @param <T>         The type of the response body.
     * @return The response body.
     * @throws FreeStuffException on failure.
     */
    protected <T> T request(String endpoint, String suffix, String requestBody, Class<T> type) throws FreeStuffException, InterruptedException {
        JsonElement responseElement = request(endpoint, suffix, requestBody);
        return gson.fromJson(responseElement, TypeToken.get(type).getType());
    }

    private static class ServiceAnalytics<T> {
        /**
         * Your data in the same model as stored in the database. Varies by service kind.
         */
        public T data;
        /**
         * Service unique id.
         */
        @SerializedName("suid")
        public String serviceId;
        /**
         * The service: Discord or Telegram.
         */
        public Service service;
    }
}
