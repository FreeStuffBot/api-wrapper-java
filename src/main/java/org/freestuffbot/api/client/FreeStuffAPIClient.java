package org.freestuffbot.api.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.freestuffbot.api.client.exceptions.FreeStuffException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.http.HttpClient.Version.HTTP_1_1;

public class FreeStuffAPIClient extends FreeStuffAPI {

    protected HttpClient client = HttpClient.newBuilder()
            .version(HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    /**
     * Creates a new API wrapper instance for a public API key.
     *
     * @param apiKey The public API key.
     */
    public FreeStuffAPIClient(String apiKey) {
        super(apiKey);
    }

    /**
     * Creates a new API wrapper instance for a partner API key.
     *
     * @param apiKey    The partner API key.
     * @param serviceId The service id of this instance.
     */
    public FreeStuffAPIClient(String apiKey, String serviceId) {
        super(apiKey, serviceId);
    }

    @Override
    protected JsonElement request(String endpoint, String suffix, String requestBody) throws FreeStuffException, InterruptedException {
        String url = baseUrl + endpoint + (suffix != null ? suffix : "");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", authorizationToken)
                .header("Content-Type","application/json");

        HttpRequest request = (requestBody == null) ? requestBuilder.GET().build() :
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            if (jsonResponse.get("success").getAsBoolean()) {
                return jsonResponse.get("data");
            } else {
                throw new FreeStuffException(jsonResponse.get("error").getAsString(), jsonResponse.get("message").getAsString());
            }
        } catch (IOException e) {
            throw new FreeStuffException("IOException", e.getMessage());
        }
    }
}
