package com.ecommerce.controller;

import com.ecommerce.entity.Ad;
import com.ecommerce.service.AdService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ads")
public class AdController {

  private final AdService adService;

  public AdController(AdService adService) {
    this.adService = adService;
  }

  // GET /api/ads - Public endpoint to fetch active ads
  @GetMapping
  public ResponseEntity<List<Ad>> getActiveAds() {
    List<Ad> ads = adService.getAllActiveAds();
    return ResponseEntity.ok(ads);
  }

  // GET /api/ads/{id} - Fetch a specific ad by ID
  @GetMapping("/{id}")
  public ResponseEntity<Ad> getAdById(@PathVariable Long id) {
    Ad ad = adService.getAdById(id);
    return ResponseEntity.ok(ad);
  }

  // POST /api/ads - Create a new ad (Admin feature)
  @PostMapping
  public ResponseEntity<Ad> createAd(@Valid @RequestBody Ad ad) {
    Ad createdAd = adService.createAd(ad);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
  }

  // DELETE /api/ads/{id} - Delete an ad by ID (Admin feature)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
    adService.deleteAd(id);
    return ResponseEntity.noContent().build();
  }
}
