package com.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class KeepAliveService {

  private final RestClient restClient;

  // Set your production Render URL in application.properties or environment
  // variable
  @Value("${app.render-url:http://localhost:8080}")
  private String renderUrl;

  public KeepAliveService() {
    this.restClient = RestClient.create();
  }

  // 14 minutes in milliseconds = 14 * 60 * 1000 = 840,000 ms
  @Scheduled(fixedRate = 840000)
  public void pingSelf() {
    try {
      // Ping a lightweight endpoint like /health or your root URL
      String response = restClient.get().uri(renderUrl).retrieve().body(String.class);
      log.info("Keep-alive ping successful: {}", response);
    } catch (Exception e) {
      log.warn("Keep-alive ping failed: {}", e.getMessage());
    }
  }
}
