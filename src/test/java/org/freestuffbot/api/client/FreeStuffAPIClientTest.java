package org.freestuffbot.api.client;

import org.freestuffbot.api.client.exceptions.FreeStuffException;
import org.freestuffbot.api.client.fake_http_client.FaultyIOHttpClient;
import org.freestuffbot.api.client.structures.GamesCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FreeStuffAPIClientTest {

    @Test
    void ioException() {
        FreeStuffAPIClient client = new FreeStuffAPIClient("FAKE_TOKEN");
        client.client = new FaultyIOHttpClient();
        assertThrows(FreeStuffException.class, client::ping, "IOException: Faulty HttpClient.");
    }

    @Test
    void unauthorized() {
        FreeStuffAPI basicAPI = createFakeClient("DONT_AUTHORIZE");
        assertThrows(FreeStuffException.class, basicAPI::ping, "Forbidden: Authorization invalid.");

        FreeStuffAPI partnerAPI = createFakeClient("DONT_AUHTORIZE", "DONT_AUTHORIZE");
        assertThrows(FreeStuffException.class, partnerAPI::ping, "Forbidden: Authorization invalid.");
    }

    @Test
    void ping() throws FreeStuffException, InterruptedException {
        FreeStuffAPI api = createFakeBasicClient();
        api.ping();
    }

    @Test
    void getGamesList() throws FreeStuffException, InterruptedException {
        FreeStuffAPI api = createFakeBasicClient();

        int[] allGames = api.getGamesList(GamesCategory.ALL);
        int[] allGamesExpected = {276756, 696742, 578572, 30979, 320412};
        assertArrayEquals(allGames, allGamesExpected);

        int[] approvedGames = api.getGamesList(GamesCategory.APPROVED);
        int[] approvedGamesExpected = {276756, 30979};
        assertArrayEquals(approvedGames, approvedGamesExpected);

        int[] freeGames = api.getGamesList(GamesCategory.FREE);
        int[] freeGamesExpected = {276756};
        assertArrayEquals(freeGames, freeGamesExpected);
    }

    private FreeStuffAPIClient createFakeBasicClient() {
        return createFakeClient("FAKE_TOKEN");
    }

    private FreeStuffAPIClient createFakePartnerClient() {
        return createFakeClient("FAKE_TOKEN", "FAKE_SERVICE_ID");
    }

    private FreeStuffAPIClient createFakeClient(String apiToken) {
        FreeStuffAPIClient fakeClient = new FreeStuffAPIClient(apiToken);
        fakeClient.client = new FakeAPIHttpClient();
        return fakeClient;
    }

    private FreeStuffAPIClient createFakeClient(String apiToken, String serviceId) {
        FreeStuffAPIClient fakeClient = new FreeStuffAPIClient(apiToken, serviceId);
        fakeClient.client = new FakeAPIHttpClient();
        return fakeClient;
    }


}
