package tk.zytekaron.nerdurl4j;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tk.zytekaron.nerdurl4j.api.Body;
import tk.zytekaron.nerdurl4j.api.NerdUrlException;
import tk.zytekaron.nerdurl4j.api.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * NerdUrl generator for the NerdUrl API (https://www.nerdurl.ml/api)
 */
public class NerdUrl {
    private static final String BASE_URL = "https://www.nerdurl.ml/api";
    private final HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String token;
    
    /**
     * Create a NerdUrl generator
     */
    public NerdUrl() {
        this(null);
    }
    
    /**
     * Create a NerdUrl generator with an API token
     * @param token The API token to authenticate with
     */
    public NerdUrl(String token) {
        this.token = token;
    }
    
    /**
     * Shorten a URL
     * @param url The URL to shorten
     * @return A {@link CompletableFuture} containing the shortened URL returned from the API
     */
    public CompletableFuture<String> shorten(String url) {
        url = urlify(url);
        HttpRequest request = createRequest("/shortenURL", new Body(url));
        return execute(request);
    }
    
    /**
     * Prepend a URL with "https://" if it does not begin with "http"
     * @param url The URL to urlify
     * @return The URL-ified URL
     */
    public String urlify(String url) {
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        return url;
    }
    
    private HttpRequest createRequest(String path, Body body) {
        byte[] bodyBytes;
        try {
            bodyBytes = mapper.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            throw new NerdUrlException(e);
        }
        return createRequest(path, bodyBytes);
    }
    
    private HttpRequest createRequest(String path, byte[] body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json");
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        return builder.build();
    }
    
    private CompletableFuture<String> execute(HttpRequest request) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(httpResponse -> {
                    checkStatus(httpResponse);
                    return httpResponse;
                })
                .thenApply(httpResponse -> deserialize(httpResponse.body()))
                .thenApply(Response::getData);
    }
    
    private Response deserialize(byte[] data) {
        try {
            return mapper.readValue(data, Response.class);
        } catch (IOException e) {
            throw new NerdUrlException(e);
        }
    }
    
    private void checkStatus(HttpResponse<?> httpResponse) {
        switch (httpResponse.statusCode()) {
            case 400:
                throw new NerdUrlException("Bad request");
            case 429:
                throw new NerdUrlException("Too Many Requests");
        }
    }
}