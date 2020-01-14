package com.mercury.platform.core.utils.interceptor.webhook;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;

import java.util.List;
import java.io.IOException;

import com.mercury.platform.core.utils.interceptor.webhook.WebhookMessage;

import com.google.gson.Gson;

public abstract class WebhookDeliverer {

    // one instance, reuse
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Gson gson = new Gson();

    public static void sendPost(List<String> webhookAddresses, WebhookMessage content) {

        webhookAddresses.stream().forEach(webhook -> {
            try {
                HttpPost post = new HttpPost(webhook);
                post.setHeader("Content-type", "application/json");

                StringEntity postingString = new StringEntity(gson.toJson(content));
                post.setEntity(postingString);

                try (CloseableHttpClient httpClient = HttpClients.createDefault();
                        CloseableHttpResponse response = httpClient.execute(post)) {
                    System.out.println(response);
                    httpClient.close();
                }
            } catch (IOException ex) {
                System.out.println("Failed to send webhook message");
            }
        });

    }
}
