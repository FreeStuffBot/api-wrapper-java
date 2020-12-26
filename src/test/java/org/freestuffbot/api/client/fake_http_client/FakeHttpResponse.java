package org.freestuffbot.api.client.fake_http_client;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * A fake HttpResponse.
 * @param <T> The response body type.
 */
public class FakeHttpResponse<T> implements HttpResponse<T> {

    private final int statusCode;
    private final HttpHeaders responseHeaders;
    private final T responseBody;
    private final HttpRequest sourceRequest;

    /**
     * Creates a fake HttpResponse.
     *
     * @param statusCode      The response status code.
     * @param responseHeaders The response headers.
     * @param responseBody    The response body.
     * @param sourceRequest   The HttpRequest corresponding to this response.
     */
    public FakeHttpResponse(int statusCode, HttpHeaders responseHeaders, T responseBody, HttpRequest sourceRequest) {
        this.statusCode = statusCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.sourceRequest = sourceRequest;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public HttpRequest request() {
        return sourceRequest;
    }

    @Override
    public Optional<HttpResponse<T>> previousResponse() {
        return Optional.empty();
    }

    @Override
    public HttpHeaders headers() {
        return this.responseHeaders;
    }

    @Override
    public T body() {
        return responseBody;
    }

    @Override
    public Optional<SSLSession> sslSession() {
        return Optional.empty();
    }

    @Override
    public URI uri() {
        return sourceRequest.uri();
    }

    @Override
    public HttpClient.Version version() {
        return HttpClient.Version.HTTP_1_1;
    }
}
