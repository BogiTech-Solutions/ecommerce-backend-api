package com.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record EnvConfig(Security security, Payment payment, File file) {
  public record Security(Jwt jwt, Admin admin) {
    public record Jwt(String secret, long expiration) {}

    public record Admin(String email, String password) {}
  }

  public record Payment(Stripe stripe, Chapa chapa) {
    public record Stripe(String secretKey, String webhookSecret) {}

    public record Chapa(String secretKey, String webhookSecret, String baseUrl) {}
  }

  public record File(String uploadDir) {}

  // Convenience Helper Methods
  public String getSigningKey() {
    return security().jwt().secret();
  }

  public String getSupperAdminEmail() {
    return security().admin().email();
  }

  public String getSupperAdminPAssword() {
    return security().admin().password();
  }

  public long getJwtExpiration() {
    return security().jwt().expiration();
  }

  public String getStripeSecretKey() {
    return payment().stripe().secretKey();
  }

  public String getStripeWebhookSecret() {
    return payment().stripe().webhookSecret();
  }

  public String getChapaSecretKey() {
    return payment().chapa().secretKey();
  }

  public String getChapaWebhookSecret() {
    return payment().chapa().webhookSecret();
  }

  public String getBaseUrl() {
    return payment().chapa().baseUrl();
  }

  public String getUploadDir() {
    return file().uploadDir();
  }
}
