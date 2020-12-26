package org.freestuffbot.api.client.fake_http_client;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@FunctionalInterface
public interface FakeHttpHandler {
    HttpResponse<?> apply(HttpRequest request) throws IOException, InterruptedException;
}
