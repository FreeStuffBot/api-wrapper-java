package org.freestuffbot.api.client;

import org.freestuffbot.api.client.fake_http_client.FakeHttpClient;
import org.freestuffbot.api.client.fake_http_client.FakeHttpResponse;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeAPIHttpClient extends FakeHttpClient {

    protected static final HttpHeaders emptyHeaders = HttpHeaders.of(new HashMap<>(), (header, value) -> true);
    protected static final Map<String, String> fakeGETResponses = new HashMap<>();
    protected static final Map<String, String> fakePOSTResponses = new HashMap<>();
    protected static final String baseUrl = "https://management.freestuffbot.xyz/api/v1/";

    static {
        fakeGETResponses.put("ping", "ping");
        fakeGETResponses.put("games/all", "game_list_all");
        fakeGETResponses.put("games/approved", "game_list_approved");
        fakeGETResponses.put("games/free", "game_list_free");
        fakeGETResponses.put("game/276756/info", "game_276756_info");
        fakeGETResponses.put("game/276756/analytics", "game_276756_analytics");
        fakeGETResponses.put("game/276756/all", "game_276756_all");
        fakeGETResponses.put("game/276756+696742+578572+30979+320412/info", "games_info");
        fakeGETResponses.put("game/276756+696742+578572+30979+320412/analytics", "games_analytics");
        fakeGETResponses.put("game/276756+696742+578572+30979+320412/all", "games_all");

        fakePOSTResponses.put("game/276756/analytics", "submit_analytics_data");
    }

    public FakeAPIHttpClient() {
        super(request -> {
            HttpHeaders requestHeaders = request.headers();
            Optional<String> optionalAuthorization = requestHeaders.firstValue("Authorization");

            if (optionalAuthorization.isEmpty()) return respondUnauthorized(request);
            String authorization = optionalAuthorization.get();

            boolean basicToken = authorization.equals("Basic FAKE_TOKEN");
            boolean partnerToken = authorization.equals("Partner FAKE_TOKEN FAKE_SERVICE_ID");

            if (!(basicToken || partnerToken)) return respondUnauthorized(request);

            String endpoint = request.uri().toString().substring(baseUrl.length());
            String responseName = null;

            if (request.method().equals("GET")) {
                responseName = fakeGETResponses.get(endpoint);
            } else if (request.method().equals("POST")) {
                responseName = fakePOSTResponses.get(endpoint);
            }

            if (responseName != null) return respondSuccess(request, responseName);

            return respondNotFound(request);
        });
    }

    protected static HttpResponse<String> respondSuccess(HttpRequest request, String name) {
        return new FakeHttpResponse<>(200, emptyHeaders, readResourceFile("responses/" + name + ".json"), request);
    }

    protected static HttpResponse<String> respondUnauthorized(HttpRequest request) {
        return new FakeHttpResponse<>(403, emptyHeaders, readResourceFile("responses/unauthorized.json"), request);
    }

    protected static HttpResponse<String> respondNotFound(HttpRequest request) {
        return new FakeHttpResponse<>(404, emptyHeaders, readResourceFile("responses/not_found.json"), request);
    }

    protected static HttpResponse<String> respondRateLimited(HttpRequest request) {
        return new FakeHttpResponse<>(429, emptyHeaders, readResourceFile("responses/rate_limited.json"), request);
    }

    @SuppressWarnings("ConstantConditions")
    protected static String readResourceFile(String name) {
        try {
            return new String(FakeAPIHttpClient.class.getClassLoader().getResourceAsStream(name).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e); //We're not supposed to handle the resource errors.
        }
    }
}
