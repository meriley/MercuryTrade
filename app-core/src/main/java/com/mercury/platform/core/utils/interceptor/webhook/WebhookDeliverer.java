package com.mercury.platform.core.utils.interceptor.webhook;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;

import com.mercury.platform.core.utils.interceptor.webhook.WebhookMessage;

import com.google.gson.Gson;

public abstract class WebhookDeliverer {

    // one instance, reuse
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Gson gson = new Gson();

    public static void sendPost(WebhookMessage content) throws Exception {

        HttpPost post = new HttpPost("https://maker.ifttt.com/trigger/trades/with/key/cfabarFG82Ah0JmgwVsSdB");
        post.setHeader("Content-type","application/json");
        
        StringEntity  postingString =new StringEntity(gson.toJson(content));
        post.setEntity(postingString);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
            httpClient.close();
        }

    }
}

